package com.sensorweb.datacenterairservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sensorweb.datacenterairservice.dao.AirStationMapper;
import com.sensorweb.datacenterairservice.dao.TWEPAMapper;
import com.sensorweb.datacenterairservice.entity.AirStationModel;
import com.sensorweb.datacenterairservice.entity.Observation;
import com.sensorweb.datacenterairservice.entity.TWEPA;
import com.sensorweb.datacenterairservice.feign.ObsFeignClient;
import com.sensorweb.datacenterairservice.feign.SensorFeignClient;
import com.sensorweb.datacenterairservice.util.AirConstant;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.datacenterutil.utils.DownloadFile;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.GZIPInputStream;

@Slf4j
@Service
@EnableScheduling
public class InsertTWEPA implements AirConstant {

    @Autowired
    private AirStationMapper airStationMapper;

    @Value("${datacenter.tmpDir}")
    private String tmpDir;

    @Autowired
    private TWEPAMapper twepaMapper;

    @Autowired
    private SensorFeignClient sensorFeignClient;

    @Autowired
    private ObsFeignClient obsFeignClient;

    /**
     * 每小时接入一次数据
     */
    @Scheduled(cron = "0 30 0/1 * * ?") //每个小时的30分开始接入
    public void insertDataByHour() {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = false;
                try {
                    String path = downloadFile();
                    String document = getDocumentByGZip(path);
                    flag = insertTwEPAInfoBatch(getEPAInfo(document));
                    if (flag) {
                        log.info("台湾EPA接入时间: " + dateTime.toString() + "Status: Success");
                        DataCenterUtils.sendMessage("TW_AIR"+ dateTime.toString(), "站网-台湾空气质量","这是一条获取的台湾省空气质量数据");
                        System.out.println("台湾EPA接入时间: " + dateTime.toString() + "Status: Success");
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.info("台湾EPA接入时间: " + dateTime.toString() + "Status: Fail");
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }


    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertTwEPAInfoBatch(List<TWEPA> twepas) {

        for (TWEPA twepa:twepas) {
            int status = twepaMapper.insertData(twepa);
//            if (status>0) {
//                Observation observation = new Observation();
//                observation.setProcedureId(twepa.getSiteId());
//                observation.setObsTime(twepa.getPublishTime());
//                observation.setMapping("tw_epa");
//                observation.setObsProperty("AirQuality");
//                observation.setType("TW_EPA_AIR");
//                AirStationModel airStationModel = airStationMapper.selectByStationId(twepa.getSiteId()).get(0);
//                if (airStationModel!=null) {
//                    String wkt = "POINT(" + airStationModel.getLon() + " " + airStationModel.getLat() + ")";
//                    observation.setWkt(wkt);
//                    String bbox = airStationModel.getLon() + " " + airStationModel.getLat() + "," + airStationModel.getLon() + " " + airStationModel.getLat();
//                    observation.setBbox(bbox);
//
//                    boolean isWH = obsFeignClient.insertBeforeSelectWHSpa(wkt);
//                    boolean isCJ = obsFeignClient.insertBeforeSelectCJSpa(wkt);
//                    if(isWH==true&&isCJ==true){
//                        observation.setGeoType(1);
//                    }
//                    else if(isWH==false&&isCJ==true){
//                        observation.setGeoType(2);
//                    }else{
//                        observation.setGeoType(3);
//                    }
//                }
//                observation.setEndTime(observation.getObsTime().plusSeconds(60*60));
//                observation.setBeginTime(observation.getObsTime());
//                observation.setOutId(twepa.getId());
//                boolean flag = true;
////                try {
////                    flag = sensorFeignClient.isExist(observation.getProcedureId());
////                    System.out.println(flag);
////                } catch (Exception e) {
////                    e.printStackTrace();
////                }
//                if (flag) {
//                    obsFeignClient.insertData(observation);
//                } else {
//                    log.info("procedure:" + observation.getProcedureId() + "不存在");
//                }
//            }
        }

        return true;
    }

    public Instant str2Instant(String time) {
        String pattern = "yyyy/MM/dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant();
    }

    /**
     * 解析JSON字符串信息
     */
    public List<TWEPA> getEPAInfo(String document) {
        List<TWEPA> res = new ArrayList<>();
        if (!StringUtils.isBlank(document)) {
            JSONObject jsonObject = JSON.parseObject(document);
            JSONArray feeds = jsonObject.getJSONArray("feeds");
            if (feeds!=null) {
                for (int i=0; i<feeds.size(); i++) {
                    TWEPA twepa = new TWEPA();
                    JSONObject feed = feeds.getJSONObject(i);
                    twepa.setTime(str2Instant(feed.getString("PublishTime")));
                    twepa.setAqi(feed.getString("AQI"));
                    twepa.setCo(feed.getString("CO"));
                    twepa.setCo8hr(feed.getString("CO_8hr"));
                    twepa.setCountry(feed.getString("Country"));
                    twepa.setImportDate(str2Instant(feed.getString("PublishTime")));
                    twepa.setLon(Double.parseDouble(feed.getString("Longitude")));
                    twepa.setLat(Double.parseDouble(feed.getString("Latitude")));
                    twepa.setNo(feed.getString("NO"));
                    twepa.setNo2(feed.getString("NO2"));
                    twepa.setNox(feed.getString("NOx"));
                    twepa.setO3(feed.getString("O3"));
                    twepa.setO38hr(feed.getString("O3_8hr"));
                    twepa.setPm10(feed.getString("PM10"));
                    twepa.setPm10Avg(feed.getString("PM10_AVG"));
                    twepa.setPm25(feed.getString("PM2_5"));
                    twepa.setPm25Avg(feed.getString("PM2_5_AVG"));
                    twepa.setPollutant(feed.getString("Pollutant"));
                    twepa.setPublishTime(str2Instant(feed.getString("PublishTime")));
                    twepa.setSo2(feed.getString("SO2"));
                    twepa.setSo2Avg(feed.getString("SO2_AVG"));
                    twepa.setSiteEngName(feed.getString("SiteEngName"));
                    twepa.setSiteId(feed.getString("SiteId"));
                    twepa.setSiteName(feed.getString("SiteName"));
                    twepa.setSiteType(feed.getString("SiteType"));
                    twepa.setStatus(feed.getString("Status"));
                    twepa.setWindDirec(feed.getString("WindDirec"));
                    twepa.setWindSpeed(feed.getString("WindSpeed"));
                    twepa.setApp(feed.getString("app"));
                    twepa.setDate(feed.getString("date"));
                    twepa.setFmtOpt(feed.getString("fmt_opt"));
                    twepa.setVerFormat(feed.getString("ver_format"));
                    twepa.setDeviceId(feed.getString("device_id"));
                    res.add(twepa);
                }
            }
        }
        return res;
    }

    /**
     * 不进行解压，直接获取压缩包里的文件内容
     */
    public String getDocumentByGZip(String filePath) {
        String res = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(filePath)), StandardCharsets.ISO_8859_1));
            String line = null;
            while( (line = reader.readLine()) != null){
                res = line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 通过网络下载链接下载数据文件到本地目录,并返回下载成功的文件路径
     */
    public String downloadFile() {
        String filePath  = tmpDir + getNowTime() + ".json.gz";
        try {
            DownloadFile.downloadUsingStream(AirConstant.TW_EPA_URL, filePath);
        } catch (IOException e) {
            log.debug(e.getMessage());
            return null;
        }
        return filePath;
    }

    /**
     * 根据时间生成日期时间字符串
     * @return
     */
    public String getNowTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
    }

    /**
     * 将台湾EPA站点注册到数据库
     * @param
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertTWAirStationInfo(List<TWEPA> twepas) throws IOException {
        List<AirStationModel> res = new ArrayList<>();
        if (twepas!=null && twepas.size()>0) {
            for (TWEPA twepa : twepas) {
                AirStationModel airStationModel = new AirStationModel();
                airStationModel.setStationId(twepa.getSiteId());
                airStationModel.setStationName(twepa.getSiteName());
                airStationModel.setTownship(twepa.getCountry());
                JSONObject jsonObject = null;
                if (airStationModel.getStationName()!=null) {
                    jsonObject = getGeoAddress(airStationModel.getStationName());
                }
                if (jsonObject!=null) {
                    Object result = jsonObject.get("result");
                    if (result!=null) {
                        Object location = ((JSONObject) result).get("location");
                        airStationModel.setLon(((JSONObject) location).getFloatValue("lng"));
                        airStationModel.setLat(((JSONObject) location).getFloatValue("lat"));
                    }
                }
                airStationModel.setStationType("TW_EPA_AIR");
                res.add(airStationModel);
            }
        }
        int status = airStationMapper.insertDataBatch(res);
        return status > 0;
    }

    /**
     * 根据地址获取经纬度信息,转换成JSON对象返回
     * @param address
     */
    public JSONObject getGeoAddress(String address) throws IOException {
        String param = "address=" + URLEncoder.encode(address, "utf-8") + "&output=json" + "&ak=" + AirConstant.BAIDU_AK;
        String document = DataCenterUtils.doGet(AirConstant.BAIDU_ADDRESS_API, param);
        if (document!=null) {
            return JSON.parseObject(document);
        }
        return null;
    }
}
