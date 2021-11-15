package com.sensorweb.datacentermobileservice.service;


import com.sensorweb.datacentermobileservice.dao.MeasuringVehicleMapper;
import com.sensorweb.datacentermobileservice.entity.MeasuringVehicle;
import com.sensorweb.datacentermobileservice.util.MeasuringVehicleUtil;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.datacenterutil.utils.FTPUtils;
import io.swagger.models.auth.In;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Service
@EnableScheduling
public class MeasuringVehicleService {
    @Value("${datacenter.path.HLDradio}")
    private String HLDRadioDownLoadPath;

    @Value("${datacenter.path.VLDradio}")
    private String VLDRadioDownLoadPath;

    @Autowired
    MeasuringVehicleMapper  measuringVehicleMapper;

    public List<MeasuringVehicle> getVocsByPage(int pageNum, int pageSize) {
        return measuringVehicleMapper.getVocsByPage(pageNum, pageSize);
    }
    public List<MeasuringVehicle> getHTByPage(int pageNum, int pageSize) {
        return measuringVehicleMapper.getHTByPage(pageNum, pageSize);
    }
    public List<MeasuringVehicle> getPMByPage(int pageNum, int pageSize) {
        return measuringVehicleMapper.getPMByPage(pageNum, pageSize);
    }
    public List<MeasuringVehicle> getAirByPage(int pageNum, int pageSize) {
        return measuringVehicleMapper.getAirByPage(pageNum, pageSize);
    }
    public List<MeasuringVehicle> getSPMSByPage(int pageNum, int pageSize) {
        return measuringVehicleMapper.getSPMSByPage(pageNum, pageSize);
    }



    public boolean insertVocsData(String data) throws IOException {
        int statue = 0;
        String lon = null;
        String lat = null;
        MeasuringVehicle measuringVehicle = new MeasuringVehicle();
        System.out.println("获取到的数据为： " + data);
        if(data.substring(0,2).equals("##")){
        try {
            Matcher m1 = Pattern.compile("(?<=DataTime=).+?(?=;)").matcher(data);
            while (m1.find()) {
                String time = m1.group().trim();
                String pattern = "yyyyMMddHHmmss";
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
                LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
                Instant time1 = localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant();
                measuringVehicle.setDataTime(time1);
            }
            Matcher m2 = Pattern.compile("(?<=a81002-Rtd=).+?(?=&)").matcher(data);
            while (m2.find()) {
                lon = m2.group().trim();
                measuringVehicle.setLon(Float.valueOf(lon));
            }
            Matcher m3 = Pattern.compile("(?<=a81001-Rtd=).+?(?=;)").matcher(data);
            while (m3.find()) {
                lat = m3.group().trim();
                measuringVehicle.setLat(Float.valueOf(lat));
            }

            Matcher m4 = Pattern.compile("(?<=a01002-Rtd=).+?(?=;)").matcher(data);
            while (m4.find()) {
                String AirHumidity = m4.group().trim();
                measuringVehicle.setAirHumidity(Float.valueOf(AirHumidity));
            }

            Matcher m5 = Pattern.compile("(?<=a01001-Rtd=).+?(?=;)").matcher(data);
            while (m5.find()) {
                String AirTemperature = m5.group().trim();
                measuringVehicle.setAirTemperature(Float.valueOf(AirTemperature));
            }

            Matcher m6 = Pattern.compile("(?<=a010061-Rtd=).+?(?=;)").matcher(data);
            while (m6.find()) {
                String AirPressure = m6.group().trim();
                measuringVehicle.setAirPressure(Float.valueOf(AirPressure));
            }

            Matcher m7 = Pattern.compile("(?<=a01007-Rtd=).+?(?=;)").matcher(data);
            while (m7.find()) {
                String WindSpeed = m7.group().trim();
                measuringVehicle.setWindSpeed(Float.valueOf(WindSpeed));
            }

            Matcher m8 = Pattern.compile("(?<=a01008-Rtd=).+?(?=;)").matcher(data);
            while (m8.find()) {
                String WindDirection = m8.group().trim();
                measuringVehicle.setWindDirection(Float.valueOf(WindDirection));
            }

            Matcher m9 = Pattern.compile("(?<=a80001-Rtd=).+?(?=;)").matcher(data);
            while (m9.find()) {
                String TVOCs = m9.group().trim();
                measuringVehicle.setTVOCs(Float.valueOf(TVOCs));
            }
            if(measuringVehicle != null) {
                statue = measuringVehicleMapper.insertVocsData(measuringVehicle);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
          return statue > 0;
        }else{
            System.out.println("获取数据内容/格式有问题，没有入库");
            return statue > 0;
        }

    }

    public boolean insertAirData(String data) throws IOException {
        int statue = 0;
        String lon = null;
        String lat = null;
        MeasuringVehicle measuringVehicle = new MeasuringVehicle();
        System.out.println("获取到的数据为： " + data);
        if(data.startsWith("##")){
            try {
                Matcher m1 = Pattern.compile("(?<=DataTime=).+?(?=;)").matcher(data);
                while (m1.find()) {
                    String time = m1.group().trim();
                    String pattern = "yyyyMMddHHmmss";
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                    dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
                    LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
                    Instant time1 = localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant();
                    measuringVehicle.setDataTime(time1);
                }
//                Matcher m2 = Pattern.compile("(?<=a81002-Rtd=).+?(?=&)").matcher(data);
//                while (m2.find()) {
//                    lon = m2.group().trim();
//                    measuringVehicle.setLon(Float.valueOf(lon));
//                }
//                Matcher m3 = Pattern.compile("(?<=a81001-Rtd=).+?(?=;)").matcher(data);
//                while (m3.find()) {
//                    lat = m3.group().trim();
//                    measuringVehicle.setLat(Float.valueOf(lat));
//                }

                Matcher m4 = Pattern.compile("(?<=a05024-Avg=).+?(?=,)").matcher(data);
                while (m4.find()) {
                    String o3 = m4.group().trim();
                    measuringVehicle.setO3(Float.valueOf(o3));
                }

                Matcher m5 = Pattern.compile("(?<=a21002-Avg=).+?(?=,)").matcher(data);
                while (m5.find()) {
                    String nox = m5.group().trim();
                    measuringVehicle.setNOX(Float.valueOf(nox));
                }

                Matcher m6 = Pattern.compile("(?<=a21026-Avg=).+?(?=,)").matcher(data);
                while (m6.find()) {
                    String so2 = m6.group().trim();
                    measuringVehicle.setSO2(Float.valueOf(so2));
                }
                Matcher m7 = Pattern.compile("(?<=a21003-Avg=).+?(?=,)").matcher(data);
                while (m7.find()) {
                    String no = m7.group().trim();
                    measuringVehicle.setNO(Float.valueOf(no));
                }
                Matcher m8 = Pattern.compile("(?<=a21003-Avg=).+?(?=,)").matcher(data);
                while (m8.find()) {
                    String no = m8.group().trim();
                    measuringVehicle.setNO(Float.valueOf(no));
                }

                Matcher m9 = Pattern.compile("(?<=a21004-Avg=).+?(?=,)").matcher(data);
                while (m9.find()) {
                    String no2 = m9.group().trim();
                    measuringVehicle.setNO2(Float.valueOf(no2));
                }
                Matcher m10 = Pattern.compile("(?<=a21004-Avg=).+?(?=,)").matcher(data);
                while (m10.find()) {
                    String co = m10.group().trim();
                    measuringVehicle.setCO(Float.valueOf(co));
                }

                if(measuringVehicle != null) {
                    measuringVehicle.setLon(0);
                    measuringVehicle.setLat(0);
                    statue = measuringVehicleMapper.insertAirData(measuringVehicle);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return statue > 0;
        }else{
            System.out.println("获取数据内容/格式有问题，没有入库");
            return statue > 0;
        }

    }

    public boolean insertPMData(String data) throws IOException {
        int statue = 0;
        String lon = null;
        String lat = null;
        MeasuringVehicle measuringVehicle = new MeasuringVehicle();
        System.out.println("获取到的数据为： " + data);
        if(data.startsWith("##")){
            try {
                Matcher m1 = Pattern.compile("(?<=DataTime=).+?(?=;)").matcher(data);
                while (m1.find()) {
                    String time = m1.group().trim();
                    String pattern = "yyyyMMddHHmmss";
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                    dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
                    LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
                    Instant time1 = localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant();
                    measuringVehicle.setDataTime(time1);
                }
//                Matcher m2 = Pattern.compile("(?<=a81002-Rtd=).+?(?=&)").matcher(data);
//                while (m2.find()) {
//                    lon = m2.group().trim();
//                    measuringVehicle.setLon(Float.valueOf(lon));
//                }
//                Matcher m3 = Pattern.compile("(?<=a81001-Rtd=).+?(?=;)").matcher(data);
//                while (m3.find()) {
//                    lat = m3.group().trim();
//                    measuringVehicle.setLat(Float.valueOf(lat));
//                }

                Matcher m4 = Pattern.compile("(?<=a34002-Avg=).+?(?=,)").matcher(data);
                while (m4.find()) {
                    String pm10 = m4.group().trim();
                    measuringVehicle.setPM10(Float.valueOf(pm10));
                }

                Matcher m5 = Pattern.compile("(?<=a34004-Avg=).+?(?=,)").matcher(data);
                while (m5.find()) {
                    String pm25 = m5.group().trim();
                    measuringVehicle.setPM25(Float.valueOf(pm25));
                }

                Matcher m6 = Pattern.compile("(?<=a34005-Avg=).+?(?=,)").matcher(data);
                while (m6.find()) {
                    String pm1 = m6.group().trim();
                    measuringVehicle.setPM1(Float.valueOf(pm1));
                }

                if(measuringVehicle != null) {
                    measuringVehicle.setLon(0);
                    measuringVehicle.setLat(0);
                    statue = measuringVehicleMapper.insertPMData(measuringVehicle);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return statue > 0;
        }else{
            System.out.println("获取数据内容/格式有问题，没有入库");
            return statue > 0;
        }

    }

    public boolean insertHTData(String data) throws IOException {
        int statue = 0;
        String lon = null;
        String lat = null;
        MeasuringVehicle measuringVehicle = new MeasuringVehicle();
        System.out.println("获取到的数据为： " + data);
        if(data.startsWith("##")){
            try {
                Instant time1 = null;
                Matcher m1 = Pattern.compile("(?<=DataTime=).+?(?=;)").matcher(data);
                while (m1.find()) {
                    String time = m1.group().trim();
                    String pattern = "yyyyMMddHHmmss";
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                    dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
                    LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
                    time1 = localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant();
                    measuringVehicle.setDataTime(time1);
                }
//                Matcher m2 = Pattern.compile("(?<=a81002-Rtd=).+?(?=&)").matcher(data);
//                while (m2.find()) {
//                    lon = m2.group().trim();
//                    measuringVehicle.setLon(Float.valueOf(lon));
//                }
//                Matcher m3 = Pattern.compile("(?<=a81001-Rtd=).+?(?=;)").matcher(data);
//                while (m3.find()) {
//                    lat = m3.group().trim();
//                    measuringVehicle.setLat(Float.valueOf(lat));
//                }

                Matcher m4 = Pattern.compile("(?<=a340081-Avg=).+?(?=,)").matcher(data);
                while (m4.find()) {
                    String c370nm = m4.group().trim();
                    measuringVehicle.setC370nm(Float.valueOf(c370nm));
                }

                Matcher m5 = Pattern.compile("(?<=a340082-Avg=).+?(?=,)").matcher(data);
                while (m5.find()) {
                    String c470nm = m5.group().trim();
                    measuringVehicle.setC470nm(Float.valueOf(c470nm));
                }

                Matcher m6 = Pattern.compile("(?<=a340083-Avg=).+?(?=,)").matcher(data);
                while (m6.find()) {
                    String c520nm = m6.group().trim();
                    measuringVehicle.setC520nm(Float.valueOf(c520nm));
                }
                Matcher m7 = Pattern.compile("(?<=a340084-Avg=).+?(?=,)").matcher(data);
                while (m7.find()) {
                    String c590nm = m7.group().trim();
                    measuringVehicle.setC590nm(Float.valueOf(c590nm));
                }
                Matcher m8 = Pattern.compile("(?<=a340085-Avg=).+?(?=,)").matcher(data);
                while (m8.find()) {
                    String c660nm = m8.group().trim();
                    measuringVehicle.setC660nm(Float.valueOf(c660nm));
                }
                Matcher m9 = Pattern.compile("(?<=a340086-Avg=).+?(?=,)").matcher(data);
                while (m9.find()) {
                    String c880nm = m9.group().trim();
                    measuringVehicle.setC880nm(Float.valueOf(c880nm));
                }
                Matcher m10 = Pattern.compile("(?<=a340087-Avg=).+?(?=,)").matcher(data);
                while (m10.find()) {
                    String c950nm = m10.group().trim();
                    measuringVehicle.setC950nm(Float.valueOf(c950nm));
                }

                if(measuringVehicle != null) {
                    Instant begin = time1.minusSeconds(5);
                    Instant end = time1.plusSeconds(5);
                    List<MeasuringVehicle> measuringVehicle1 =  measuringVehicleMapper.selectByTime(begin,end);
                    System.out.println("measuringVehicle1 = " + measuringVehicle1);
                    if(measuringVehicle1.size()>0) {
                        measuringVehicle.setLon(measuringVehicle1.get(0).getLon());
                        measuringVehicle.setLat(measuringVehicle1.get(0).getLat());
                    }
                    statue = measuringVehicleMapper.insertHTData(measuringVehicle);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return statue > 0;
        }else{
            System.out.println("获取数据内容/格式有问题，没有入库");
            return statue > 0;
        }


    }

    public boolean insertSPMSData(String data) throws IOException {
        int statue = 0;
        String lon = null;
        String lat = null;
        MeasuringVehicle measuringVehicle = new MeasuringVehicle();
        System.out.println("获取到的数据为： " + data);
        if(data.startsWith("##")){
            try {
                Matcher m1 = Pattern.compile("(?<=DataTime=).+?(?=;)").matcher(data);
                while (m1.find()) {
                    String time = m1.group().trim();
                    String pattern = "yyyyMMddHHmmss";
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                    dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
                    LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
                    Instant time1 = localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant();
                    measuringVehicle.setDataTime(time1);
                }
//                Matcher m2 = Pattern.compile("(?<=a81002-Rtd=).+?(?=&)").matcher(data);
//                while (m2.find()) {
//                    lon = m2.group().trim();
//                    measuringVehicle.setLon(Float.valueOf(lon));
//                }
//                Matcher m3 = Pattern.compile("(?<=a81001-Rtd=).+?(?=;)").matcher(data);
//                while (m3.find()) {
//                    lat = m3.group().trim();
//                    measuringVehicle.setLat(Float.valueOf(lat));
//                }

                Matcher m4 = Pattern.compile("(?<=b60004-Avg=).+?(?=,)").matcher(data);
                while (m4.find()) {
                    String dust = m4.group().trim();
                    measuringVehicle.setDust(Float.valueOf(dust));
                }

                Matcher m5 = Pattern.compile("(?<=b60001-Avg=).+?(?=,)").matcher(data);
                while (m5.find()) {
                    String biomassBurning = m5.group().trim();
                    measuringVehicle.setBiomassBurning(Float.valueOf(biomassBurning));
                }

                Matcher m6 = Pattern.compile("(?<=b60010-Avg=).+?(?=,)").matcher(data);
                while (m6.find()) {
                    String motorVehicleExhaust = m6.group().trim();
                    measuringVehicle.setMotorVehicleExhaust(Float.valueOf(motorVehicleExhaust));
                }
                Matcher m7 = Pattern.compile("(?<=b60003-Avg=).+?(?=,)").matcher(data);
                while (m7.find()) {
                    String burningCoal = m7.group().trim();
                    measuringVehicle.setBurningCoal(Float.valueOf(burningCoal));
                }
                Matcher m8 = Pattern.compile("(?<=b60009-Avg=).+?(?=,)").matcher(data);
                while (m8.find()) {
                    String industrialProcess= m8.group().trim();
                    measuringVehicle.setIndustrialProcess(Float.valueOf(industrialProcess));
                }

                Matcher m9 = Pattern.compile("(?<=b60006-Avg=).+?(?=,)").matcher(data);
                while (m9.find()) {
                    String secondaryInorganicSource = m9.group().trim();
                    measuringVehicle.setSecondaryInorganicSource(Float.valueOf(secondaryInorganicSource));
                }
                Matcher m10 = Pattern.compile("(?<=b60008-Avg=).+?(?=,)").matcher(data);
                while (m10.find()) {
                    String other = m10.group().trim();
                    measuringVehicle.setOther(Float.valueOf(other));
                }
                Matcher m11 = Pattern.compile("(?<=b60002-Avg=).+?(?=,)").matcher(data);
                while (m11.find()) {
                    String food = m11.group().trim();
                    measuringVehicle.setFood(Float.valueOf(food));
                }
                if(measuringVehicle != null) {
                    measuringVehicle.setLon(0);
                    measuringVehicle.setLat(0);
                    statue = measuringVehicleMapper.insertSPMSData(measuringVehicle);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return statue > 0;
        }else{
            System.out.println("获取数据内容/格式有问题，没有入库");
            return statue > 0;
        }

    }




//    @Scheduled(cron = "0 */1 * * * ?")
//    public void insertDataByHour() {
//        LocalDateTime dateTime = LocalDateTime.now();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                        String filePath_HLD = MeasuringVehicleUtil.HLD;
//                        String filePath_VLD = MeasuringVehicleUtil.VLD;
//                        String file_name_VLD = "";
//                        Boolean flag1 = false;
//
//                        SimpleDateFormat sd = new SimpleDateFormat();// 格式化时间
//                        sd.applyPattern("yyyyMMddHHmmss");
//                        Date date = new Date();// 获取当前时间
//                        String time = sd.format(date);
//
//                        String file_name= time.substring(0,8);
//                        filePath_HLD = filePath_HLD+"/"+file_name;
//
//                        for(int i =0; i<=59;i++){
//                            int j = Integer.valueOf(time);
//                            j=j+1;
//                            String file_name_HLD ="20211109000011"+".json";
//                            flag1 = downLoadRadioData(filePath_HLD,file_name_HLD,HLDRadioDownLoadPath,dateTime.toInstant(ZoneOffset.UTC),"HLD");
//                        }
////                        Boolean flag2 = downLoadRadioData(filePath_VLD,file_name_VLD,VLDRadioDownLoadPath,dateTime.toInstant(ZoneOffset.UTC),"VLD");
//                        if (flag1) {
//                            log.info("激光雷达HLD: " + time + "Status: Success");
//                        }
////                        if (flag2) {
////                            log.info("激光雷达VLD: " + time + "Status: Success");
////                        }
//                    } catch (Exception e) {
//                        System.out.println("激光雷达接入时间:  + time + Status: Failed");
//                        log.error(e.getMessage());
//                    }
//                }
//
//        }).start();
//    }

    public Boolean downLoadRadioData(String filePath,String file_name,String radio_downloadPath,Instant time,String type) throws ParseException {
        Boolean status = false;
        FTPClient ftpClient = FTPUtils.getFTPClient(MeasuringVehicleUtil.Radio_HOST, MeasuringVehicleUtil.Radio_USERNAME, MeasuringVehicleUtil.Radio_PASSWORD);
        boolean flag = FTPUtils.downloadFTP(ftpClient,filePath, file_name, radio_downloadPath);
        MeasuringVehicle measuringVehicle = new MeasuringVehicle();
        if (flag) {
            measuringVehicle.setFileName(file_name);
            measuringVehicle.setDataTime(time);
            measuringVehicle.setFilePath(radio_downloadPath + "/" +file_name);
            measuringVehicle.setType(type);
            int i = measuringVehicleMapper.insertRadioData(measuringVehicle);
            return i>0;
        }
        return status;
    }

}
