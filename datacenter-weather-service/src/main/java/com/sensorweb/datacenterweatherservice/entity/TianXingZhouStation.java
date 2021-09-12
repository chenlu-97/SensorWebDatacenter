package com.sensorweb.datacenterweatherservice.entity;


import lombok.Data;

import java.time.Instant;


@Data
public class TianXingZhouStation {
   // air-weather
    private int id;
    private String stationId;
    private String stationName = "天兴洲综合站";
    private float lon;
    private float lat;
    private Instant queryTime;
    private String geom;
    private float airTemperature;
    private float airHumidity;
    private float pressure;
    private float so2;
    private float windSpeed;
    private String windDirection;
    private float o3;
    private float co;
    private float no2;
    private float pm25;
    private float pm10;


    //soil
    private float soilTemperature;
    private float soilHumidity;

}
