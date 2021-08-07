package com.sensorweb.sossensorservice.entity;

import java.time.Instant;

public class ValidTime {
    private int id;
    private String procedureId;
    private Instant beginPosition;
    private Instant endPosition;

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

    public Instant getBeginPosition() {
        return beginPosition;
    }

    public void setBeginPosition(Instant beginPosition) {
        this.beginPosition = beginPosition;
    }

    public Instant getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(Instant endPosition) {
        this.endPosition = endPosition;
    }
}
