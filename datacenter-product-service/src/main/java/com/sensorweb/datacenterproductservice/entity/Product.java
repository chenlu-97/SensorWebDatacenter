package com.sensorweb.datacenterproductservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class Product {
    private int id;

    private String productId;

    private String productName;

    private String productDescription;

    private String productKeyword;

    private String manufactureDate;

    private String organizationName;

    private String serviceName;

    private String downloadAddress;

    private String productType;

    private String timeResolution;

    private String spatialResolution;

    private int dimension;

    private String serviceTarget;


//    AirPollutionPrediction的字段

    private String stationName;
    private double so2OneHour;
    private double pm25OneHour;
    private double pm10OneHour;
    private double o3OneHour;
    private double no2OneHour;
    private double coOneHour;
    private double aqi;
//    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private String predictedTime;

}