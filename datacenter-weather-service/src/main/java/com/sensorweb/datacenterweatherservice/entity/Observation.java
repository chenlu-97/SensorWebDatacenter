package com.sensorweb.datacenterweatherservice.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class Observation {
    private int id;
    private String procedureId;
    private String name;
    private String description;
    private Instant obsTime;
    private Instant beginTime;
    private Instant endTime;
    private String bbox;
    private String wkt;
    private String obsProperty;
    private String type;
    private String mapping;
    private int outId;
    private int geoType;

}
