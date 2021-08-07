package com.sensorweb.datacenterairservice.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class TWEPA {
    private int id;
    private Instant time;
    private String aqi;
    private String co;
    private String co8hr;
    private String country;
    private Instant importDate;
    private double lon;
    private double lat;
    private String no;
    private String no2;
    private String nox;
    private String o3;
    private String o38hr;
    private String pm10;
    private String pm10Avg;
    private String pm25;
    private String pm25Avg;
    private String pollutant;
    private Instant publishTime;
    private String so2;
    private String so2Avg;
    private String siteEngName;
    private String siteId;
    private String siteName;
    private String siteType;
    private String status;
    private String windDirec;
    private String windSpeed;
    private String app;
    private String date;
    private String fmtOpt;
    private String verFormat;
    private String deviceId;
}
