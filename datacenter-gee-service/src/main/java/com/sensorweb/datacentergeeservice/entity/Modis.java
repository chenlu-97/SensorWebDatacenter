package com.sensorweb.datacentergeeservice.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class Modis {

    private String imageID;
    private String spacecraftID;
    private String Coordinates;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private String Date;
    private String imageSize;
    private String imageType;
    private String filePath;
}
