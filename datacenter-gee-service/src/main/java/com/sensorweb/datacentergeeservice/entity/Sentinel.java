package com.sensorweb.datacentergeeservice.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Sentinel {
    private String imageID;
    private String sensorID;
    private String spacecraftID;
    private String Coordinates;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String Date;
    private String Time;
    private String imageSize;
    private String Ellipsoid;
    private String Cloudcover;
    private String Thumburl;
    private String imageType;
    private String filePath;
    private String waveBand;
    private String bandInfo;
}
