package com.sensorweb.datacenterairservice.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class AirQualityHour {
    private String stationName;
    private String uniqueCode;
    private Instant queryTime;
    private String pm25OneHour;
    private String pm10OneHour;
    private String so2OneHour;
    private String no2OneHour;
    private String coOneHour;
    private String o3OneHour;
    private String aqi;
    private String primaryEP;
    private String aqDegree;
    private String aqType;

}
