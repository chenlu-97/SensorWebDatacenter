package com.sensorweb.datacenterweatherservice.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class FY4A {
    private int id;
    private String fileName;
    private String format;
    private String fileSize;
    private String fileUrl;
    private String imgBasE64;
    private Instant dateTime;
    private String sateSensorChanl;
}