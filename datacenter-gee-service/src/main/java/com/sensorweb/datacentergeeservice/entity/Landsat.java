package com.sensorweb.datacentergeeservice.entity;

import lombok.Data;

import java.time.Instant;
@Data
public class Landsat {

    private String imageID;
    private String sensorID;
    private String spacecraftID;
    private String Coordinates;
    private String Date;
    private String Time;
    private String imageSize;
    private String Ellipsoid;
    private String Cloudcover;
    private String Thumburl;
    private String imageType;
    private String filePath;
    private String waveBand = "430nm~12510nm";
    private String bandInfo = "紫外&可见光&近红外&远红外&微波";
}
