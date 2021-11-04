package com.sensorweb.datacentermobileservice.entity;


import lombok.Data;

import java.time.Instant;

@Data
public class MeasuringVehicle {

    private int id;
    private Instant dataTime;
    private float lon;
    private float lat;
    private String geom;
    private float airTemperature;
    private float airHumidity;
    private float airPressure;
    private float windSpeed;
    private float windDirection;
    private float TVOCs;
}
