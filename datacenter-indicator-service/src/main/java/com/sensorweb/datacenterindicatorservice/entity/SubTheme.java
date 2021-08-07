package com.sensorweb.datacenterindicatorservice.entity;

public class SubTheme {
    private int id;
    private String name;
    private String indicatorName;
    private String scale;
    private String spaResolution;
    private String obsFrequency;
    private String outId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getSpaResolution() {
        return spaResolution;
    }

    public void setSpaResolution(String spaResolution) {
        this.spaResolution = spaResolution;
    }

    public String getObsFrequency() {
        return obsFrequency;
    }

    public void setObsFrequency(String obsFrequency) {
        this.obsFrequency = obsFrequency;
    }

    public String getOutId() {
        return outId;
    }

    public void setOutId(String outId) {
        this.outId = outId;
    }
}
