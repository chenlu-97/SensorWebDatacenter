package com.sensorweb.datacenterofflineservice.entity;


import lombok.Data;

import java.time.Instant;

@Data
public class WaterPollution {

//    CREATE TABLE HB_WaterPollution(id SERIAL NOT NULL PRIMARY KEY,company_name VARCHAR(50),
//    station_name VARCHAR(25),station_type VARCHAR(25),station_item VARCHAR(25),flow REAL, concentration REAL,
//    isoverstandard VARCHAR(25),overstandard_reason VARCHAR(25), emission_cap REAL ,
//    emission_limit REAL, lon REAL, lat REAL, geom GEOMETRY, monitoring_time timestamp);

    private Integer id;

    private String companyName;

    private String stationName;

    private String stationType;

    private String stationItem;

    private float flow;

    private float concentration;

    private String isOverStandard;

    private String OverStandardReason;

    private float emissionCap;

    private float emissionLimit;

    private Instant monitoringTime;

}
