package com.sensorweb.datacenteronlineservice.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class OnlineSensor {
    private Integer id;

    private String sensorId;

    private String geom;

    private Instant queryTime;

    private String scenePath;

    private String sceneRow;


}