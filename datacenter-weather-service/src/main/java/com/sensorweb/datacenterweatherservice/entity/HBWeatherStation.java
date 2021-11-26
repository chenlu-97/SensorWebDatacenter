package com.sensorweb.datacenterweatherservice.entity;

import lombok.Data;

@Data
public class HBWeatherStation {
    int id;
    String stationId;
    float lat;
    float lon;
    float prs;
    float tem;
    float rhu;
    float pre_1h;
    float gst;
}
