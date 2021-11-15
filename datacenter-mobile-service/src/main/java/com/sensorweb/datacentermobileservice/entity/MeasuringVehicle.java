package com.sensorweb.datacentermobileservice.entity;


import lombok.Data;

import java.time.Instant;

@Data
public class MeasuringVehicle {

    private int id;
    private Instant dataTime;

    //vocs
    private float lon;
    private float lat;
    private float airTemperature;
    private float airHumidity;
    private float airPressure;
    private float windSpeed;
    private float windDirection;
    private float TVOCs;
    //Air
    private float SO2;
    private float NO;
    private float NO2;
    private float NOX;
    private float CO;
    private float O3;
    //PM
    private float PM10;
    private float PM25;
    private float PM1;
    //黑炭
    private float c370nm;
    private float c470nm;
    private float c520nm;
    private float c590nm;
    private float c660nm;
    private float c880nm;
    private float c950nm;
    //SPMS
    private float dust;
    private float biomassBurning;
    private float motorVehicleExhaust;
    private float burningCoal;
    private float industrialProcess;
    private float secondaryInorganicSource;
    private float other;
    private float food;

    //颗粒物雷达
    private String fileName;
    private String siteId;
    private String type;
    private String filePath;
    private String pitch;
    private String heading;


}
