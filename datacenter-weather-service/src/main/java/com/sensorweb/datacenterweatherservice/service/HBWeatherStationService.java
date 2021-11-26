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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@EnableScheduling
public class HBWeatherStationService {
    @Autowired
    HBWeatherStationMapper hbWeatherStationMapper;

    public String getApiDocument() throws IOException {
        String param = "userId=" + WeatherConstant.HBWEATHER_STATION_ID +"&pwd=" + WeatherConstant.HBWEATHER_STATION_PASSWORD + "&interfaceId=" + WeatherConstant.HBWEATHER_STATION_INTERFACEID + "&times=" + WeatherConstant.HBWEATHER_STATION_TIMES + "&dataCode=" + WeatherConstant.HBWEATHER_STATION_DATACODE + "&dataFormat=" + WeatherConstant.HBWEATHER_STATION_DATAFORMAT;
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
        String pattern = "yyyy-MM-dd HH:mm:ss";
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
            statue = hbWeatherStationMapper.insertData(hbWeatherStation);
        }
        return statue>0;

    }
}
