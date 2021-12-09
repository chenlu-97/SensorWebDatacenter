package com.sensorweb.datacenterweatherservice.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.datacenterweatherservice.dao.HBWeatherStationMapper;
import com.sensorweb.datacenterweatherservice.entity.HBWeatherStation;
import com.sensorweb.datacenterweatherservice.util.WeatherConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

@Slf4j
@Service
@EnableScheduling
public class HBWeatherStationService {
    @Autowired
    HBWeatherStationMapper hbWeatherStationMapper;



    /**
     * 每小时接入一次数据
     */
    @Scheduled(cron = "0 20 0/1 * * ?") //每个小时的20分开始接入
    public void insertDataByHour() {
//        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHH0000");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -8);//由于接入时间是utc时间和正常的系统时间差8小时，这里需要减去8小时
        String dateTime = format.format(calendar.getTime());
        new Thread(new Runnable() {
            @Override
            public void run() {
                boolean flag = false;
                try {
                    String document = getApiDocument(dateTime);
                    flag= getIOTInfo(document);
                    if (flag) {
                        log.info("湖北气象接入时间: " + dateTime.toString() + "Status: Success");
                        System.out.println("湖北气象接入时间: " + dateTime.toString() + "Status: Success");
                        DataCenterUtils.sendMessage("HB_Weather_"+dateTime.toString(), "站网-湖北省气象","这是一条湖北省气象数据的");
                    }
                    Thread.sleep(2 * 60 * 1000);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                }
            }
        }).start();
    }




    public String getApiDocument(String time) throws IOException {
        String param = "userId=" + WeatherConstant.HBWEATHER_STATION_ID +"&pwd=" + WeatherConstant.HBWEATHER_STATION_PASSWORD + "&interfaceId=" + WeatherConstant.HBWEATHER_STATION_INTERFACEID + "&times=" + time + "&dataCode=" + WeatherConstant.HBWEATHER_STATION_DATACODE + "&dataFormat=" + WeatherConstant.HBWEATHER_STATION_DATAFORMAT;
        String s = WeatherConstant.GET_HBWEATHER_STATION_URL+param;
        System.out.println(s);
        String document = DataCenterUtils.doGet(WeatherConstant.GET_HBWEATHER_STATION_URL, param);
//        if (document != null) {
//            JSONObject rootObject = new JSONObject(Boolean.parseBoolean(document));
//            System.out.println(document);
//        }
//        return null;
        return document;
    }

    /**
     * 字符串转Instant  yyyy-MM-dd HH:mm:ss
     * @return
     * @throws IOException
     */
    public Instant str2Instant(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        String pattern = "yyyy-MM-dd HH:00:00";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant();
    }

    /**
     * 通过api请求数据入库
     */

    public boolean getIOTInfo(String document) throws IOException {
        int statue = 0;
        HBWeatherStation hbWeatherStation = new HBWeatherStation();
        JSONObject jsonObject = JSON.parseObject(document);
        JSONArray message = jsonObject.getJSONArray("DS");
        Instant queryTime = str2Instant(jsonObject.getString("responseTime")).plusSeconds(8*60*60);
        for (int i = 0; i < message.size(); i++) {
            JSONObject object = message.getJSONObject(i);
            hbWeatherStation.setStationId(object.getString("Station_Name"));
            hbWeatherStation.setLat(Float.parseFloat(object.getString("Lat")));
            hbWeatherStation.setLon(Float.parseFloat(object.getString("Lon")));
            hbWeatherStation.setPrs(Float.parseFloat(object.getString("PRS")));
            hbWeatherStation.setTem(Float.parseFloat(object.getString("TEM")));
            hbWeatherStation.setRhu(Float.parseFloat(object.getString("RHU")));
            hbWeatherStation.setPre_1h(Float.parseFloat(object.getString("PRE_1h")));
            hbWeatherStation.setGst(Float.parseFloat(object.getString("GST")));
            hbWeatherStation.setQueryTime(queryTime);
            statue = hbWeatherStationMapper.insertData(hbWeatherStation);
        }
        return statue>0;

    }
}
