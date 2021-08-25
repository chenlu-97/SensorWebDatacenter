package com.sensorweb.datacenterairservice.entity;

import lombok.Data;

/**
 * 湖北省环境监测站站点模型
 */
@Data
public class AirStationModel {
    private int id;
    private String stationId;
    private String stationName;
    private String township;
    private String region;
    private String city;
    private String province;
    private float lon;
    private float lat;
    private String stationType;
    private String geom;
    private String geoType;
}
