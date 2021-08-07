package com.sensorweb.datacenterofflineservice.entity;

import java.time.Instant;

public class WF{

    private Integer id;

    private String imageId;

    private String bbox;

    private String geom;

    private Instant queryTime;

    private String filePath;

    private Integer geoType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBbox() {
        return bbox;
    }

    public void setBbox(String bbox) {
        this.bbox = bbox;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    public Instant getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Instant queryTime) {
        this.queryTime = queryTime;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public Integer getGeoType() {
        return geoType;
    }

    public void setGeoType(Integer geoType) {
        this.geoType = geoType;
    }
}
