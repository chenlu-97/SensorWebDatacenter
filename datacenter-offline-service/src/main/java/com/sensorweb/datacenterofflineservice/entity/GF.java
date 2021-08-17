package com.sensorweb.datacenterofflineservice.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class GF {
    private Integer id;

    private String satelliteId;

    private String season;

    private String imageId;

    private String imageType;

    private String bbox;

    private String geom;

    private Instant queryTime;

    private String scenePath;

    private String sceneRow;

    private String filePath;

    private float topleftlatitude;

    private float topleftlongitude;

    private float toprightlatitude;

    private float toprightlongitude;

    private float bottomrightlatitude;

    private float bottomrightlongitude;

    private float bottomleftlatitude;

    private float bottomleftlongitude;

    private String waveBand = "450nm~900nm";

    private String bandInfo = "可见光&近红外";

}