package com.sensorweb.datacenterproductservice.service;

import com.sensorweb.datacenterproductservice.dao.DataPathMapper;
import com.sensorweb.datacenterproductservice.dao.GenProductMapper;
import com.sensorweb.datacenterproductservice.entity.DataPath;
import com.sensorweb.datacenterproductservice.entity.GenProduct;
import com.sensorweb.datacenterproductservice.entity.Observation;
import com.sensorweb.datacenterproductservice.feign.AirFeignClient;
import com.sensorweb.datacenterproductservice.feign.HimawariFeignClient;
import com.sensorweb.datacenterproductservice.feign.ObsFeignClient;
import com.sensorweb.datacenterproductservice.feign.WeatherFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@EnableScheduling
public class InsertGenProductService {

    @Autowired
    private GenProductMapper genProductMapper;
    @Autowired
    private DataPathMapper dataPathMapper;

    @Autowired
    private GetGenProductService getGenProductService;

    @Autowired
    private ObsFeignClient obsFeignClient;

    @Autowired
    private AirFeignClient airFeignClient;
    @Autowired
    private WeatherFeignClient weatherFeignClient;
    @Autowired
    private HimawariFeignClient himawariFeignClient;
    @Autowired
    private InsertGenProductService insertGenProductService;

    public boolean insertGenProduct(GenProduct genProduct) {
        int status = genProductMapper.insertData(genProduct);
        return status > 0;
    }


    public boolean insertDataPathByHour(DataPath datapath){

//        DataPath datapath = new DataPath();
        boolean status = dataPathMapper.insertNeededData(datapath);
        System.out.println("success");
        return status;

    }
    /**
     * 每小时接入一次数据
     */
//    @Scheduled(cron = "0 50 0/1 * * ?") //每个小时的55分开始接入
//    public void insertDataByHour() {
//        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00").withZone(ZoneId.of("Asia/Shanghai"));
//        String time = formatter.format(dateTime);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                boolean flag = true;
//                while (flag) {
//                    try {
//                        String timeBegin = time;
//                        String timeEnd = time;
//                        flag = getFilePath(timeBegin,timeEnd);
//                        if (flag) {
//                            log.info("所有文件路径的接入时间: " + time + "Status: Success");
//                            System.out.println("所有文件路径的接入时间:  " + time + "Status: Success");
//                        } else {
//                            System.out.println("所有文件路径的接入时间:  " + time + "Status: Fail");
//                        }
////                        Thread.sleep(5 * 60 * 1000);
//                        } catch(Exception e){
//                            log.error(e.getMessage());
//                            log.info("所有文件路径的接入时间:  " + time + "Status: Fail");
//                            System.out.println(e.getMessage());
//                            break;
//                        }
//                    }
//                }
//            }).start();
//    }

    public boolean getFilePath(String timeBegin, String timeEnd) {
        boolean res = false;
        String Type = "AIR,Himawari,WEATHER";
        String ranSpa = "wuhanCC,yangtzeRiverEB,china";
        String[] dataTypes = Type.split(",");
        String[] ranSpas = ranSpa.split(",");
        System.out.println("数据类别数：" + dataTypes.length + "-->" + Arrays.toString(dataTypes));
        try {
            LocalDateTime start = string2LocalDateTime3(timeBegin);
            Instant begin = start.atZone(ZoneId.of("GMT+8")).toInstant();
            System.out.println("time: " + begin.toString());
            LocalDateTime end = string2LocalDateTime3(timeEnd);
            Instant stop = end.atZone(ZoneId.of("GMT+8")).toInstant();
            System.out.println("time: " + stop.toString());
//            Instant time_tmp1 = begin.minusSeconds(8 * 60 * 60);
            for (String dataType:dataTypes) {
                for (String spa:ranSpas) {
                    Instant time = begin;
                    while (time.isBefore(stop)) {
                        DataPath datapath = new DataPath();
                        //查询后插入dataneed表，方便后续查询
                        if (dataType.equals("Himawari")) {
                            String filepath = himawariFeignClient.getHimawariDataById(time);
//                            if(filepath  == null){
//                                filepath = himawariFeignClient.getHimawariMaxTimeData();
//                            }
                            Instant out_time = time.plusSeconds(8 * 60 * 60);
                            datapath.setDataId(dataType + "_" + spa + "_" + replace(out_time.toString()));
                            datapath.setGeoType(spa);
                            datapath.setDataType(dataType);
                            datapath.setFilePath(filepath);
                            datapath.setBeginTime(time);
                            datapath.setEndTime(time);
                            res = insertGenProductService.insertDataPathByHour(datapath);
                            System.out.println(ranSpa + dataType);
                        } else {
                            String filepath = getFilePath(dataType,spa,time);
                            Instant out_time = time.plusSeconds(8 * 60 * 60);
                            datapath.setDataId(dataType + "_" + spa + "_" + replace(out_time.toString()));
                            datapath.setGeoType(spa);
                            datapath.setDataType(dataType);
                            datapath.setFilePath(filepath);
                            datapath.setBeginTime(time);
                            datapath.setEndTime(time);
                            res = insertGenProductService.insertDataPathByHour(datapath);
                        }
                        time  = time.plusSeconds(60*60);
                    } }
            }
            System.out.println("--------------end--------------");
        }
        catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return res;
    }

    public String getFilePath( String dataType ,String spa,Instant begin) {
        String res = null;
        try {
            if (dataType.equals("Himawari")) {  //由于葵花卫星接入差8小时，这里实时查询时间要减8小时
                res = himawariFeignClient.getHimawariDataById(begin);
//                if(res == null){
//                    res = himawariFeignClient.getHimawariMaxTimeData();
//                }
            } else {
                Map<String,List<String>> stationIDs = airFeignClient.getStationByTypeAndSpa(dataType, spa);
                System.out.println("stationIDs = " + stationIDs);
                res = getDataPath(stationIDs, begin, spa);
            }
            System.out.println("--------------end--------------");
        }
        catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return res;

    }

    public String getDataPath(Map<String,List<String>> stationIDs, Instant time, String geotype) {
        String path = null;
        if (stationIDs != null && stationIDs.size() > 0) {
            Set<String> keys = stationIDs.keySet();   //此行可省略，直接将map.keySet()写在for-each循环的条件中
            for (String key : keys) {
                if(geotype.equals("wuhanCC")){
                    if(key.equals("HB_AIR")) {
//                            System.out.println("HB_AIR-->id: " + stationIDs.size());
                        Map<String, String> HB_AIR =airFeignClient.getExportHBAirDataByIds(stationIDs.get(key), time, geotype);
                        if(HB_AIR !=null){
                            path = HB_AIR.get("filePath");
                        }
                    }else if (key.equals("wh_1+8_weather")) {
//                            System.out.println("WEATHER-->id: " + stationIDs.size());
                        Map<String, String> WEATHER = weatherFeignClient.getExportWeatherDataByIds(stationIDs.get(key), time, geotype);
                        if(WEATHER !=null){
                            path = WEATHER.get("filePath");
                        }
                    }
                }
                else if(geotype.equals("yangtzeRiverEB")){
//                        System.out.println("CH_AIR-->id: " + stationIDs.size());
                    if(key.equals("CH_AIR")) {
                        Map<String, String> CH_AIR = airFeignClient.getExportCHAirDataByIds(stationIDs.get(key), Long.valueOf(replace(time.plusSeconds(8*60*60).toString()).trim()), geotype);
//                        System.out.println(Long.valueOf(replace(time.plusSeconds(8*60*60).toString()).trim()));
                        if(CH_AIR !=null){
                            path = CH_AIR.get("filePath");
                        }
                    }
                }
                else if (geotype.equals("china")){
                    if(key.equals("CH_AIR")) {
//                        System.out.println("CH_AIR-->id: " + stationIDs.size());
                        Map<String, String> CH_AIR = airFeignClient.getExportCHAirDataByIds(stationIDs.get(key), Long.valueOf(replace(time.plusSeconds(8*60*60).toString()).trim()), geotype);
                        if(CH_AIR !=null){
                            path = CH_AIR.get("filePath");
                        }
                    }
                    else if (key.equals("TW_EPA_AIR")) {
//                        System.out.println("TW_EPA_AIR-->id: " + stationIDs.size());
                        airFeignClient.getExportTWAirDataByIds(stationIDs.get(key), time, geotype);
//                            if(TW_EPA_AIR !=null){
//                                path = TW_EPA_AIR.get("filePath");
//                            }
                    }
                }
            }
        }
        return path;
    }

    public static LocalDateTime string2LocalDateTime3(String time) throws ParseException {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTimeFormatter.withZone(ZoneId.of("GMT+8"));
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return localDateTime;
    }

    public String replace (String out){
        String out1 =out.replace("-","");
        String out2 = out1.replace("T","");
        String out3 = out2.replace("Z","");
        String out4 = out3.replace(":","");
        return  out4;
    }


}
