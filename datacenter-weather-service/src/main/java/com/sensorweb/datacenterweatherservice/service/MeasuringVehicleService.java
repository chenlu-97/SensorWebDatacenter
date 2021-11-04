package com.sensorweb.datacenterweatherservice.service;


import com.sensorweb.datacenterweatherservice.dao.MeasuringVehicleMapper;
import com.sensorweb.datacenterweatherservice.entity.MeasuringVehicle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Service
@EnableScheduling
public class MeasuringVehicleService {

    @Autowired
    MeasuringVehicleMapper  measuringVehicleMapper;

    public List<MeasuringVehicle> getDataByPage(int pageNum, int pageSize) {
        return measuringVehicleMapper.selectByPage(pageNum, pageSize);
    }


    public boolean insertData(String data) throws IOException {
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

            measuringVehicle.setGeom("POINT(" + Float.valueOf(lon) + " " + Float.valueOf(lat) + ")");

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
                statue = measuringVehicleMapper.insertData(measuringVehicle);
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

}
