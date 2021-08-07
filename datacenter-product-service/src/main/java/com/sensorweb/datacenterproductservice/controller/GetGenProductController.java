package com.sensorweb.datacenterproductservice.controller;

import com.sensorweb.datacenterproductservice.entity.DataPath;
import com.sensorweb.datacenterproductservice.entity.GenProduct;
import com.sensorweb.datacenterproductservice.entity.Observation;
import com.sensorweb.datacenterproductservice.feign.AirFeignClient;
import com.sensorweb.datacenterproductservice.feign.HimawariFeignClient;
import com.sensorweb.datacenterproductservice.feign.ObsFeignClient;
import com.sensorweb.datacenterproductservice.feign.WeatherFeignClient;
import com.sensorweb.datacenterproductservice.service.GetGenProductService;
import com.sensorweb.datacenterproductservice.service.InsertGenProductService;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;


@RestController
@Slf4j
@CrossOrigin
public class GetGenProductController {

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


    @GetMapping(value = "getGenProductById")
    public GenProduct getById(@Param("id") int id) {
        return getGenProductService.getGenProductById(id);
    }

    @GetMapping(value = "getGenProductByType")
    public GenProduct getById(@Param("type") String type) {
        return getGenProductService.getGenProductByType(type);
    }

    @GetMapping(value = "getGenProducts")
    public List<GenProduct> getById() {
        return getGenProductService.getGenProducts();
    }



    public static LocalDateTime string2LocalDateTime(String time){
        //处理类似20210727000000格式的时间字符串为2021-07-27 00:00:00

        String year = time.substring(0,4);
        String month = time.substring(4,6);
        String day = time.substring(6,8);
        String hour = time.substring(8,10);
        String minute = time.substring(10,12);
        String second = time.substring(12);
        String time123 = year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second;
//        System.out.println("time123 = " + time123);
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = LocalDateTime.parse(time123, dateTimeFormatter);
        return localDateTime;
    }


    @GetMapping(value = "getFilePathByTypeAndTime")
    public String getFilePathByTypeAndTime(@RequestParam("type") String type, @RequestParam("spa") String spa, @RequestParam("time") String time) {
//        SimpleDateFormat sd = new SimpleDateFormat();// 格式化时间
//        sd.applyPattern("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date();// 获取当前时间
////        System.out.println("现在时间：" + sd.format(date));
//        String time = sd.format(date);
//        String time1 = time.substring(0, time.indexOf(":"));
//        String time2 = time1 + ":00:00";
        Instant timeNow = string2LocalDateTime(time).atZone(ZoneId.of("GMT+8")).toInstant();
//        System.out.println("timeNow = " + timeNow);
        String filePath = getFilePath123(type, spa,timeNow);
        return filePath;
    }


    @GetMapping(value = "getAirFiveHourFilePath")
    public List<String> getFiveAirHourFilePath() {
        SimpleDateFormat sd = new SimpleDateFormat();// 格式化时间
        String type = "HB_AIR";
        String spa = "wuhanCC";
        sd.applyPattern("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();// 获取当前时间
//        System.out.println("现在时间：" + sd.format(date));
        String time = sd.format(date);
        String time1 = time.substring(0, time.indexOf(":"));
        String time2 = time1 + ":00:00";
//        String out_time = time1+"0000";
        Instant timeNow = DataCenterUtils.string2LocalDateTime3(time2).toInstant(ZoneOffset.ofHours(+8)).plusSeconds(60*60);
        Instant timebefore = DataCenterUtils.string2LocalDateTime3(time2).toInstant(ZoneOffset.ofHours(+8)).minusSeconds(4*60*60);
        Instant timestamp = timebefore;
        String filePath = null;
        List<String> res = new ArrayList();
        while(timestamp.isBefore(timeNow)){
            List<String> stationIDs= new ArrayList();
            String ids = "421200012,429004090,429006096,421100011,420900009,420200002,420700007,420100001,429005095";
            String[] stationID = ids.split(",");
            for(String id:stationID){
                stationIDs.add(id);
            }
            filePath = airFeignClient.getExportAll_HBAirDataByIds(stationIDs,timestamp,spa).get("filePath");
            res.add(filePath);
            timestamp = timestamp.plusSeconds(60 * 60);
        }
        return res;
    }


    /**
     * 通过给出的参数，查询出相应产品类型所需要的所有数据的路径（本地路径）
     * @param productType 产品类型
     * @param spaRes 空间分辨率
     * @param ranType 范围类型
     * @param ranSpa 空间范围
     * @param timeRes 时间分辨率
     * @param timeBegin 开始时间
     * @param timeEnd 结束时间
     * @return
     */
//    修改后的查询方式，用新的查询表
//    @GetMapping(value = "getFilePath")
//    public List<Map<String, List<String>>> getFilePathTest(@RequestParam("productType") String productType, @RequestParam("spaRes") String spaRes,
//                                                           @RequestParam("ranType") String ranType, @RequestParam("ranSpa") String ranSpa,
//                                                           @RequestParam("timeRes") String timeRes, @RequestParam("timeBegin") String timeBegin,
//                                                           @RequestParam("timeEnd") String timeEnd) {
//        List<Map<String, List<String>>> finalres = new ArrayList<>() ;
//        GenProduct genProduct = getGenProductService.getGenProductByTypeAndRegion(productType, ranSpa);
//        String[] dataTypes = genProduct.getDataNeeded().split(",");
//        System.out.println("数据类别数：" + dataTypes.length + "-->" + Arrays.toString(dataTypes));
//        try {
//            LocalDateTime start = string2LocalDateTime3(timeBegin);
//            Instant begin = start.atZone(ZoneId.of("GMT+8")).toInstant();
//            System.out.println("time: " + begin.toString());
//            LocalDateTime end = string2LocalDateTime3(timeEnd);
//            Instant stop = end.atZone(ZoneId.of("GMT+8")).toInstant();
//            System.out.println("time: " + stop.toString());
//            if (timeRes.equals("1小时")) {
//                while (begin.isBefore(stop)) {
//                    Map<String, List<String>> res = new HashMap<>();
//                    List<String> filePaths = new ArrayList<>();
//                    for (String dataType:dataTypes) {
//                        String filePath = getGenProductService.selectFilePathByTimeAndType(begin,dataType,ranSpa);
//                        filePaths.add(filePath);
//                    }
//                    Instant out_time = begin.plusSeconds(8*60*60);
//                    res.put(replace(out_time.toString()),filePaths);
//                    finalres.add(res);
//                    begin = begin.plusSeconds(60 * 60);
//                }
//            }
//
//            System.out.println("--------------end--------------");
//        }
//        catch (Exception e) {
//            log.error(e.getMessage());
//            e.printStackTrace();
//        }
//        return finalres;
//    }

    //    修改后的查询方式，用新的查询表
    @GetMapping(value = "getFilePath")
    public List<Map<String, List<String>>> getFilePath(@RequestParam("productType") String productType, @RequestParam("spaRes") String spaRes,
                                                           @RequestParam("ranType") String ranType, @RequestParam("ranSpa") String ranSpa,
                                                           @RequestParam("timeRes") String timeRes, @RequestParam("timeBegin") String timeBegin,
                                                           @RequestParam("timeEnd") String timeEnd) {
        List<Map<String, List<String>>> finalres = new ArrayList<>() ;
        GenProduct genProduct = getGenProductService.getGenProductByTypeAndRegion(productType, ranSpa);
//        String[] dataTypes = genProduct.getDataNeeded().split(",");
        String id = "AIR,Himawari,WEATHER";
        String[] dataTypes = id.split(",");
        System.out.println("数据类别数：" + dataTypes.length + "-->" + Arrays.toString(dataTypes));
        try {
            LocalDateTime start = string2LocalDateTime3(timeBegin);
            Instant begin = start.atZone(ZoneId.of("GMT+8")).toInstant();
            System.out.println("time: " + begin.toString());
            LocalDateTime end = string2LocalDateTime3(timeEnd);
            Instant stop = end.atZone(ZoneId.of("GMT+8")).toInstant();
            System.out.println("time: " + stop.toString());
            if (timeRes.equals("1小时")) {
                while (begin.isBefore(stop)) {
                    Map<String, List<String>> res = new HashMap<>();
                    List<String> filePaths = new ArrayList<>();
                    for (String dataType:dataTypes) {
                        String filePath = getFilePath(dataType, ranSpa, begin);
                        filePaths.add(filePath);
                    }
                    Instant out_time = begin.plusSeconds(8*60*60);
                    res.put(replace(out_time.toString()),filePaths);
                    finalres.add(res);
                    begin = begin.plusSeconds(60 * 60);
                }
            }

            System.out.println("--------------end--------------");
        }
        catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return finalres;
    }



    public String getFilePath( String dataType ,String spa,Instant begin) {
        String res = null;
        try {
            if (dataType.equals("Himawari")) {  //由于葵花卫星接入差8小时，这里实时查询时间要减8小时
                res = himawariFeignClient.getHimawariDataById(begin);
                if(res == null){
                    String time = begin.plusSeconds(8*60*60).toString().replace("Z","");
                    System.out.println("time = " + time);
                    Map<String, String> tmp = himawariFeignClient.getHimawariData(time);
                    if(tmp.get(tmp.keySet()).equals("success")) {
                        res = himawariFeignClient.getHimawariDataById(begin);
                    }
                }
            } else {
                Map<String,List<String>> stationIDs = airFeignClient.getStationByTypeAndSpa(dataType, spa);
//                    System.out.println("stationIDs = " + stationIDs);
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


    public String getFilePath123( String dataType ,String spa,Instant begin) {
        String res = null;
        try {
                if (dataType.equals("Himawari")) {  //由于葵花卫星接入差8小时，这里实时查询时间要减8小时
                    res = himawariFeignClient.getHimawariDataById(begin);
                    if(res == null){
                      res = himawariFeignClient.getHimawariMaxTimeData();
                    }
                } else {
                    Map<String,List<String>> stationIDs = airFeignClient.getStationByTypeAndSpa(dataType, spa);
//                    System.out.println("stationIDs = " + stationIDs);
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



    @GetMapping(value = "insertFilePath")
    public boolean  insertFilePath( @RequestParam("timeBegin") String timeBegin, @RequestParam("timeEnd") String timeEnd) {
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
                        } else {
                            String filepath = getFilePath123(dataType,spa,time);
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
