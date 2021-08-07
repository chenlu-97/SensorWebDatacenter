package com.sensorweb.datacenterofflineservice.entity;


import lombok.Data;

@Data
public class WaterPollutionStation {


    private Integer id;

    private String companyName;

    private String stationName;

    private String province;

    private String city;

    private String township;

    private String receivingWater;

    private float lon;

    private float lat;

    private String geom;

    //插入Station表
    private String stationType;


}
