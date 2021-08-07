package com.sensorweb.datacenterweatherservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.datacenterweatherservice.dao.WeatherMapper;
import com.sensorweb.datacenterweatherservice.dao.WeatherStationMapper;
import com.sensorweb.datacenterweatherservice.entity.ChinaWeather;
import com.sensorweb.datacenterweatherservice.entity.Observation;
import com.sensorweb.datacenterweatherservice.entity.WeatherStationModel;
import com.sensorweb.datacenterweatherservice.feign.ObsFeignClient;
import com.sensorweb.datacenterweatherservice.feign.SensorFeignClient;
import com.sensorweb.datacenterweatherservice.util.WeatherConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@EnableScheduling
public class InsertWeatherInfo {

    @Autowired
    private WeatherMapper weatherMapper;

    @Autowired
    private WeatherStationMapper weatherStationMapper;

    @Autowired
    private SensorFeignClient sensorFeignClient;

    @Autowired
    private ObsFeignClient obsFeignClient;

    /**
     * 每小时接入一次数据
     */
    @Scheduled(cron = "0 20 0/1 * * ?") //每个小时的35分开始接入
    public void insertDataByHour() {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = false;
                try {
                    flag = insertWeatherInfoBatch(getWeatherInfo123());
                    if (flag) {
                        log.info("中国气象局接入时间: " + dateTime.toString() + "Status: Success");
                        System.out.println("中国气象局接入时间: " + dateTime.toString() + "Status: Success");
                    }
                    Thread.sleep(2 * 60 * 1000);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertWeatherInfoBatch(List<ChinaWeather> chinaWeathers) {
        for (ChinaWeather chinaWeather:chinaWeathers) {
            WeatherStationModel weatherStationModel = weatherStationMapper.selectByStationId(chinaWeather.getStationId());
            chinaWeather.setLat(String.valueOf(weatherStationModel.getLat()));
            chinaWeather.setLng(String.valueOf(weatherStationModel.getLon()));
            chinaWeather.setStationName(weatherStationModel.getStationName());
            try {
                int status = weatherMapper.insertData(chinaWeather);
            }
            catch (Exception e){
//                System.out.println("重复数据，不插入");
                e.printStackTrace();
            }
//            if (status>0) {
//                Observation observation = new Observation();
//                observation.setProcedureId(chinaWeather.getStationId());
//                observation.setObsTime(chinaWeather.getQueryTime());
//                observation.setMapping("ch_weather");
//                observation.setObsProperty("Weather");
//                observation.setType("CH_WEATHER");
//                WeatherStationModel weatherStationModel = weatherStationMapper.selectByStationId(chinaWeather.getStationId());
//                if (weatherStationModel!=null) {
//                    String wkt = "POINT(" + weatherStationModel.getLon() + " " + weatherStationModel.getLat() + ")";
//                    observation.setWkt(wkt);
//                    String bbox = weatherStationModel.getLon() + " " + weatherStationModel.getLat() + "," + weatherStationModel.getLon() + " " + weatherStationModel.getLat();
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
//
//                }
//                observation.setEndTime(observation.getObsTime());
//                observation.setBeginTime(observation.getObsTime());
//                observation.setOutId(chinaWeather.getId());
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

    /**
     * 通过调用中国气象局接口获取对应的气象数据，最后以json对象的形式返回
     */
    public JSONObject getWeatherResultByArea(String areaId) throws IOException {
        String param = "area=" + areaId + "&type=observe" + "&key=" + WeatherConstant.CHINA_WEATHER_KEY;
        String document = DataCenterUtils.doGet(WeatherConstant.CHINA_WEATHER_URL, param);
        if (document!=null) {
            return JSON.parseObject(document);
        }
        return null;
    }

    /**
     * 首先获取气象站点，然后调用API获取气象数据，因每次请求的站点数量不能超过20个，为了提高效率，每次请求20个站点
     */
    public List<ChinaWeather> getWeatherInfo() throws IOException, ParseException {
        List<ChinaWeather> res = new ArrayList<>();
        List<WeatherStationModel> weatherStationModels = weatherStationMapper.selectByStationType("wh_1+8_weather");
        for (int i=0; i< weatherStationModels.size();) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j<20 && i< weatherStationModels.size(); j++) {
                sb.append(weatherStationModels.get(i).getStationId()).append("|");
                i++;
            }
            String areaIds = sb.substring(0, sb.length()-1);
            JSONObject jsonObject = getWeatherResultByArea(URLEncoder.encode(areaIds, "utf-8"));
            if (jsonObject!=null) {
                JSONObject observes = jsonObject.getJSONObject("observe");
                int size = observes.size();
                Set<String> keys = observes.keySet();
                for (String key:keys) {
                    JSONObject result = observes.getJSONObject(key).getJSONObject("1001002");
                    if (key.equals("101200406003")) {
                        System.out.println(getNowTime(result.getString("000")));
                    }
                    ChinaWeather chinaWeather = new ChinaWeather();
                    chinaWeather.setStationId(key);
                    chinaWeather.setQueryTime(getNowTime(result.getString("000")));
                    chinaWeather.setUpdateTime(chinaWeather.getQueryTime());
                    chinaWeather.setPrecipitation(result.getString("006"));
                    chinaWeather.setPressure(result.getString("007"));
                    chinaWeather.setWindD(result.getString("004"));
                    chinaWeather.setWindP(result.getString("003"));
                    chinaWeather.setWeatherP(result.getString("001"));
                    chinaWeather.setHumidity(result.getString("005"));
                    chinaWeather.setTemperature(result.getString("002"));
                    chinaWeather.setWp(getWPInfo(chinaWeather.getWindP()));
                    chinaWeather.setQs(getQSInfo(chinaWeather.getStationId(), result.getString("000")));
                    res.add(chinaWeather);
                }
            }

        }
        return res;
    }


    public List<ChinaWeather> getWeatherInfo123() throws IOException, ParseException {
        List<ChinaWeather> res = new ArrayList<>();
        List<WeatherStationModel> weatherStationModels = weatherStationMapper.selectByStationType("wh_1+8_weather");
        for (int i=0; i< weatherStationModels.size();i++) {
//            StringBuilder sb = new StringBuilder();
//            for (int j = 0; j<20 && i< weatherStationModels.size(); j++) {
//                sb.append(weatherStationModels.get(i).getStationId()).append("|");
//                i++;
//            }
            String areaIds = weatherStationModels.get(i).getStationId();
            JSONObject jsonObject = getWeatherResultByArea(URLEncoder.encode(areaIds, "utf-8"));
            if (jsonObject!=null) {
                JSONObject observes = jsonObject.getJSONObject("observe");
                int size = observes.size();
                Set<String> keys = observes.keySet();
                for (String key:keys) {
                    JSONObject result = observes.getJSONObject(key).getJSONObject("1001002");
//                    if (key.equals("101200406003")) {
//                        System.out.println(getNowTime(result.getString("000")));
//                    }
                    ChinaWeather chinaWeather = new ChinaWeather();
                    chinaWeather.setStationId(key);
                    chinaWeather.setQueryTime(getNowTime(result.getString("000")));
                    chinaWeather.setUpdateTime(chinaWeather.getQueryTime());
                    chinaWeather.setPrecipitation(result.getString("006"));
                    chinaWeather.setPressure(result.getString("007"));
                    chinaWeather.setWindD(result.getString("004"));
                    chinaWeather.setWindP(result.getString("003"));
                    chinaWeather.setWeatherP(result.getString("001"));
                    chinaWeather.setHumidity(result.getString("005"));
                    chinaWeather.setTemperature(result.getString("002"));
                    chinaWeather.setWp(getWPInfo(chinaWeather.getWindP()));
                    chinaWeather.setQs(getQSInfo(chinaWeather.getStationId(), result.getString("000")));
                    res.add(chinaWeather);
                }
            }

        }
        return res;
    }

    /**
     * 根据时间生成日期时间
     * @param time 14:27
     * @return
     */
    public Instant getNowTime(String time) throws ParseException {
//        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String hour = time.substring(0,time.indexOf(":"));
        String dateTime = timeStamp + " " + hour+ ":00"+ ":00";
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, dateTimeFormatter);
        return localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant();
    }

    public Instant str2Instant(String time) {
        String pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant();
    }

    /**
     * 根据风力编号获取转化后的风速WP
     * @param wp
     * @return
     */
    public String getWPInfo(String wp) {
        String res = "";
        switch (wp) {
            case "0": res = "5.4"; break;
            case "1": res = "6.7"; break;
            case "2": res = "9.3"; break;
            case "3": res = "12.3"; break;
            case "4": res = "15.5"; break;
            case "5": res = "18.9"; break;
            case "6": res = "22.6"; break;
            case "7": res = "24.4"; break;
            case "8": res = "30.5"; break;
            case "9": res = "34.8"; break;
        }
        return res;
    }

    /**
     * 获取QS信息
     * @param stationId
     * @param queryTime
     * @return
     */
    public String getQSInfo(String stationId, String queryTime) {
        SimpleDateFormat sd = new SimpleDateFormat();// 格式化时间
        sd.applyPattern("yyyyMMddHH:mm:ss");
        Date date = new Date();// 获取当前时间
//        System.out.println("现在时间：" + sd.format(date));
        String time = sd.format(date);
        String time1 = time.substring(0,time.indexOf(":"));
        String time2 = time1+"0000";
        return stationId + time2;
    }
}
