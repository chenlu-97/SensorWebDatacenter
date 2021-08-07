package com.sensorweb.datacenterlittleSensorservice.entity;

import lombok.Data;

@Data
public class LittleSensor {

    private String id;
    private String receiptTime;
    private String sampleTime;
    private String humidity;
    private String temperature;
    private String pressure;
    private String lat;
    private String lon;
    private String power;
    private String deviceId;
}
