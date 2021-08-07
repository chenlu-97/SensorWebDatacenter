package com.sensorweb.datacenterofflineservice.entity;

import lombok.Data;

@Data
public class WH_WaterStation {

    private Integer id;

    private String stationId;

    private String riverName;

    private String sectionName;

    private float lon;

    private float lat;

    private String geom;

    private String stationType;

    private String stationTypeOut;


}
