package com.sensorweb.datacenterweatherservice.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class ChinaWeather {
    private String stationId;
    private String stationName;
    private Instant queryTime;
    private String precipitation;
    private Instant updateTime;
    private String pressure;
    private String windD;
    private String windP;
    private String weatherP;
    private String humidity;
    private String temperature;
    private String lng;
    private String lat;
    private String wp;
    private String qs;

}
