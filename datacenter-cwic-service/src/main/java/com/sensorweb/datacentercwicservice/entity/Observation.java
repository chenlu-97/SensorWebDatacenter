package com.sensorweb.datacentercwicservice.entity;

import java.time.Instant;

public class Observation {
    private int id;
    private String procedureId;
    private String name;
    private String description;
    private Instant obsTime;
    private Instant beginTime;
    private Instant endTime;
    private String bbox;
    private String wkt;
    private String obsProperty;
    private String type;
    private String mapping;
    private int outId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getObsTime() {
        return obsTime;
    }

    public void setObsTime(Instant obsTime) {
        this.obsTime = obsTime;
    }

    public Instant getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Instant beginTime) {
        this.beginTime = beginTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public String getBbox() {
        return bbox;
    }

    public void setBbox(String bbox) {
        this.bbox = bbox;
    }

    public String getWkt() {
        return wkt;
    }

    public void setWkt(String wkt) {
        this.wkt = wkt;
    }

    public String getObsProperty() {
        return obsProperty;
    }

    public void setObsProperty(String obsProperty) {
        this.obsProperty = obsProperty;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMapping() {
        return mapping;
    }

    public void setMapping(String mapping) {
        this.mapping = mapping;
    }

    public int getOutId() {
        return outId;
    }

    public void setOutId(int outId) {
        this.outId = outId;
    }
}
