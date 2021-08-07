package com.sensorweb.datacenterproductservice.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class DataPath {

    private int id;
    private String dataId;
    private String dataType;
    private Instant beginTime;
    private Instant endTime;
    private String filePath;
    private String geoType;
}
