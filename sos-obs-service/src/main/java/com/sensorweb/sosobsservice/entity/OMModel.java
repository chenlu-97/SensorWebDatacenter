package com.sensorweb.sosobsservice.entity;

import java.time.Instant;
import java.util.List;

public class OMModel {
    private String identifier;
    private String name;
    private String type = "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Measurement";
    private String description;
    private String procedure;
    private String observableProperty;
    private String featureOfInterest;
    private Instant[] phenomenonTime;
    private Instant resultTime;
    private List<OMResult> result;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getObservableProperty() {
        return observableProperty;
    }

    public void setObservableProperty(String observableProperty) {
        this.observableProperty = observableProperty;
    }

    public String getFeatureOfInterest() {
        return featureOfInterest;
    }

    public void setFeatureOfInterest(String featureOfInterest) {
        this.featureOfInterest = featureOfInterest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant[] getPhenomenonTime() {
        return phenomenonTime;
    }

    public void setPhenomenonTime(Instant[] phenomenonTime) {
        this.phenomenonTime = phenomenonTime;
    }

    public Instant getResultTime() {
        return resultTime;
    }

    public void setResultTime(Instant resultTime) {
        this.resultTime = resultTime;
    }

    public List<OMResult> getResult() {
        return result;
    }

    public void setResult(List<OMResult> result) {
        this.result = result;
    }
}
