package com.sensorweb.datacenterairservice.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class ChinaAirQualityHour {

    private Integer aqi;

    private String area;

    private float co;

    private float co24h;

    private Integer no2;

    private Integer no224h;

    private Integer o3;

    private Integer o324h;

    private Integer o38h;

    private Integer o38h24h;

    private Integer pm10;

    private Integer pm1024h;

    private Integer pm25;

    private Integer pm2524h;

    private String positionName;

    private String primaryPollutant;

    private String quality;

    private Integer so2;

    private Integer so224h;

    private String stationCode;

    private Instant timePoint;

    private String lng;

    private String lat;

    private Long qt;

    private Long time;

}