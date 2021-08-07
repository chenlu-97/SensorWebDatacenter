package com.sensorweb.datacenterofflineservice.entity;


import lombok.Data;

import java.time.Instant;

@Data
public class WH_WaterQuality {

    private Integer id;

    private String riverName;

    private String sectionName;

    private Instant queryTime;

    private float waterTemperature;

    private float pH;

    private float dissolvedOxygen;

    private float permanganateIndex;

    private float biochemicalOxygenDemand;

    private float totalPhosphorus;

    private float ammoniaNitrogen;

    private float totalNitrogen;

    //自动站的监测内容
    private float turbidity;

    private float chlorophyll;


}
