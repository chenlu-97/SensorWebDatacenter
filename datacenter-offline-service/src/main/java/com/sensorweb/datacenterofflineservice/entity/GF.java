package com.sensorweb.datacenterofflineservice.entity;

import java.time.Instant;

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




    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSatelliteId() {
        return satelliteId;
    }

    public void setSatelliteId(String satelliteId) {
        this.satelliteId = satelliteId;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }



    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public String getBbox() {
        return bbox;
    }

    public void setBbox(String bbox) {
        this.bbox = bbox;
    }


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }


    public Instant getQueryTime() {
        return queryTime;
    }

    public void setQueryTime(Instant queryTime) {
        this.queryTime = queryTime;
    }

    public String getScenePath() {
        return scenePath;
    }

    public void setScenePath(String scenePath) {
        this.scenePath = scenePath;
    }

    public String getSceneRow() {
        return sceneRow;
    }

    public void setSceneRow(String sceneRow) {
        this.sceneRow = sceneRow;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    public float getTopleftlatitude() {
        return topleftlatitude;
    }

    public void setTopleftlatitude(float topleftlatitude) {
        this.topleftlatitude = topleftlatitude;
    }

    public float getTopleftlongitude() {
        return topleftlongitude;
    }

    public void setTopleftlongitude(float topleftlongitude) {
        this.topleftlongitude = topleftlongitude;
    }

    public float getToprightlatitude() {
        return toprightlatitude;
    }

    public void setToprightlatitude(float toprightlatitude) {
        this.toprightlatitude = toprightlatitude;
    }

    public float getToprightlongitude() {
        return toprightlongitude;
    }

    public void setToprightlongitude(float toprightlongitude) {
        this.toprightlongitude = toprightlongitude;
    }

    public float getBottomrightlatitude() {
        return bottomrightlatitude;
    }

    public void setBottomrightlatitude(float bottomrightlatitude) {
        this.bottomrightlatitude = bottomrightlatitude;
    }

    public float getBottomrightlongitude() {
        return bottomrightlongitude;
    }

    public void setBottomrightlongitude(float bottomrightlongitude) {
        this.bottomrightlongitude = bottomrightlongitude;
    }

    public float getBottomleftlatitude() {
        return bottomleftlatitude;
    }

    public void setBottomleftlatitude(float bottomleftlatitude) {
        this.bottomleftlatitude = bottomleftlatitude;
    }

    public float getBottomleftlongitude() {
        return bottomleftlongitude;
    }

    public void setBottomleftlongitude(float bottomleftlongitude) {
        this.bottomleftlongitude = bottomleftlongitude;
    }
}