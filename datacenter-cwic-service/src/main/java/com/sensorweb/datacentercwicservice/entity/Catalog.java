package com.sensorweb.datacentercwicservice.entity;

import java.util.List;

public class Catalog {
    private String id;
    private List<DataSet> dataSets;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<DataSet> getDatasets() {
        return dataSets;
    }

    public void setDatasets(List<DataSet> dataSets) {
        this.dataSets = dataSets;
    }
}
