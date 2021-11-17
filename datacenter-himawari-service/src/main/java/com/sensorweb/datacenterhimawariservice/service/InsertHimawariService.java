package com.sensorweb.datacenterhimawariservice.service;

import com.sensorweb.datacenterhimawariservice.dao.HimawariMapper;
import com.sensorweb.datacenterhimawariservice.entity.Himawari;
import com.sensorweb.datacenterhimawariservice.feign.ObsFeignClient;
import com.sensorweb.datacenterhimawariservice.feign.SensorFeignClient;
import com.sensorweb.datacenterhimawariservice.util.HimawariConstant;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.datacenterutil.utils.FTPUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Configuration
@EnableScheduling
public class InsertHimawariService implements HimawariConstant {

    @Value("${datacenter.path.himawari}")
    private String upload;

    @Autowired
    private HimawariMapper himawariMapper;

    @Autowired
    private SensorFeignClient sensorFeignClient;

    @Autowired
    private ObsFeignClient obsFeignClient;

    //存储因更新时间错开导致的未能及时接入的数据时间
    private static final List<LocalDateTime> temp = new ArrayList<>();

    /**
     * 每隔一个小时执行一次，为了以小时为单位接入数据
     */
//    @Scheduled(cron = "00 35 * * * ?")//每小时的35分00秒执行一次(本来是每小时的30分数据更新一次，但是由于数据量的关系，可能造成在半点的时候数据并没有完成上传而导致的获取数据失败，所以这里提前半个小时，)
    @Scheduled(cron = "0 35 0/1 * * ?")
    public void insertDataByHour() {
        LocalDateTime dateTime = LocalDateTime.now();
//        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("UTC"));  //由于接入的文件不稳定，这里准备是晚8小时进行接入
//        接入因更新时间错开导致的未能及时接入的数据时间
        if (temp.size()>0) {
            for (int i=temp.size(); i>0; i--) {
                try {
                    insertData(temp.get(i-1));
                    temp.remove(i-1);
                } catch (Exception e) {
                    log.info(e.getMessage());
                }
            }
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                boolean flag = true;
                LocalDateTime time = dateTime;
                while (flag && count<10) {
                    int year = time.getYear();
                    Month month = time.getMonth();
                    String monthValue = month.getValue()<10?"0"+month.getValue():month.getValue()+"";
                    String day = time.getDayOfMonth()<10?"0"+time.getDayOfMonth():time.getDayOfMonth()+"";
                    String hour = time.getHour()<10?"0"+time.getHour():time.getHour()+"";
                    String minute = time.getMinute()<10?"0"+time.getMinute():time.getMinute()+"";
                    String fileName = getName(year+"", monthValue, day, hour, minute);
                    if (himawariMapper.selectByName(fileName)!=null) {
                        log.info("数据已存在");
                        return;
                    }
                    try {
                        flag = !insertData(time);
                        if (!flag) {
                            log.info("Himawari接入时间: " + time + "Status: Success");
                            DataCenterUtils.sendMessage("Himawari-8"+dateTime.toString(), "卫星-葵花8号","这是一条获取的葵花8号卫星的数据");
                        }
                        time = time.minusHours(1);
                        count++;
                        Thread.sleep(2 * 60 * 1000);
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }
                }
                if (count==10) {
                    temp.add(time);
                }
            }
        }).start();
    }

    /**
     * Registry Himawari Data, from dateTime to now
     * @param dateTime YYYY-DD-MMThh:mm:ss  is start time
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertData(LocalDateTime dateTime) throws Exception {
        int year = dateTime.getYear();
        dateTime = dateTime.minusHours(8);//本地时间需要减去8才能化为UTC
        Month month = dateTime.getMonth();
        String monthValue = month.getValue()<10?"0"+month.getValue():month.getValue()+"";
        String day = dateTime.getDayOfMonth()<10?"0"+dateTime.getDayOfMonth():dateTime.getDayOfMonth()+"";
        String hour = dateTime.getHour()<10?"0"+dateTime.getHour():dateTime.getHour()+"";
        String minute = dateTime.getMinute()<10?"0"+dateTime.getMinute():dateTime.getMinute()+"";
        Himawari himawari = getData(year+"", monthValue, day, hour,minute);
        if (himawari==null) {
            log.info("无法获取Himawari数据或数据已存在");
            return false;
        }
        int status = himawariMapper.insertData(himawari);
//        if (status>0) {
//            Observation observation = new Observation();
//            String procedureId = "urn:JMA:def:identifier:OGC:2.0:Himawari-8-components";
//            observation.setProcedureId(procedureId);
//            observation.setObsTime(himawari.getTime());
//            observation.setEndTime(himawari.getTime().plusSeconds(60*60));
//            observation.setBeginTime(himawari.getTime());
//            observation.setMapping("himawari");
//            observation.setObsProperty("Himawari_ARP");
//            observation.setType("Himawari");
//            observation.setName(himawari.getName());
//            observation.setBbox("-180 -90,180 90");
//            String wkt = "POLYGON((-180 -90,-180 90,180 90,180 -90,-180 -90))";
//            observation.setWkt(wkt);
//            observation.setOutId(himawari.getId());
//            boolean isWH = obsFeignClient.insertBeforeSelectWHSpa(wkt);
//            boolean isCJ = obsFeignClient.insertBeforeSelectCJSpa(wkt);
//            System.out.println("返回的isWH是："+isWH);
//            System.out.println("返回的isWH是："+isCJ);
//            if(isWH==true&&isCJ==true){
//                observation.setGeoType(1);
//            }
//            else if(isWH==false&&isCJ==true){
//                observation.setGeoType(2);
//            }else{
//                observation.setGeoType(3);
//            }
//
//            //传感器是否存在
//            boolean flag = sensorFeignClient.isExist(observation.getProcedureId());
//            if (flag) {
//                obsFeignClient.insertData(observation);
//            } else {
//                log.info("procedure: " + observation.getProcedureId() + "不存在");
//                throw new Exception("procedure: " + observation.getProcedureId() + "不存在");
//            }
//        }
        return status>0;
    }
    
  /**
     * get the data of APR
     * @param year
     * @param month
     * @param day
     * @param hour
     * @param minute
     */
    public Himawari getData(String year, String month, String day, String hour, String minute) throws ParseException {
        FTPClient ftpClient = FTPUtils.getFTPClient(HimawariConstant.HAMAWARI_HOST, HimawariConstant.HAMAWARI_USERNAME, HimawariConstant.HAMAWARI_PASSWORD);
        String fileName = getName(year, month, day, hour, minute);
        String filePath = HimawariConstant.AREOSOL_PROPERTY_LEVEL3 + year + month + "/" + day + "/";
        String uploadFilePath = upload + fileName;
        boolean flag = FTPUtils.downloadFTP(ftpClient, filePath, fileName, uploadFilePath);
        Himawari himawari = new Himawari();
        if (flag) {
            himawari.setName(fileName);
            String date = year + "-" + month + "-" + day + "T" + hour + ":00:00";
            Instant temp = DataCenterUtils.string2LocalDateTime(date).atZone(ZoneId.of("UTC")).toInstant();
            himawari.setTime(temp);
            himawari.setUrl(filePath + fileName);
            himawari.setLocalPath(uploadFilePath);
            return himawari;
        }
        return null;
    }

    /**
     * get the name of APR by naming convention
     * @param year YYYY
     * @param month MM
     * @param day DD
     * @param hour hh
     * @param minute mm
     * example: H08_20150727_0800_1HARP001_FLDK.02401_02401.nc
     */
    public String getName(String year, String month, String day, String hour, String minute) {
        if (StringUtils.isBlank(year) || StringUtils.isBlank(month) || StringUtils.isBlank(day) || StringUtils.isBlank(hour)) {
            log.error("parameter is not right");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("H08_").append(year).append(month).append(day).append("_").append(hour).append("00_1HARP031_FLDK.02401_02401.nc");
        return sb.toString();
    }

}
