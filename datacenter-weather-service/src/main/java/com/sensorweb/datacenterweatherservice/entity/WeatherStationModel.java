package com.sensorweb.datacenterweatherservice.entity;

import lombok.Data;

@Data
public class WeatherStationModel {
    private int id;
    private String stationId; //站点号
    private String stationName; //站点名称
    private String township; //乡镇
    private String region; //县、区
    private String city; //城市
    private String province; //省份
    private double lon;
    private double lat;
    private String stationType;
}
