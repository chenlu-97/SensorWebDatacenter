package com.sensorweb.sossensorservice.entity;

import lombok.Data;

/**
 * 湖北省环境监测站站点模型
 */
@Data
public class StationModel {
    private int id;
    private String stationId;
    private String stationName;
    private String township;
    private String region;
    private String city;
    private String province;
    private double lon;
    private double lat;
    private String stationType;
}
