package com.sensorweb.datacenterlaadsservice.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class Entry {
    private int id;
    private String entryId;
    private String title;
    private String updated;
    private String link;
    private String filePath;
    private Instant start;
    private Instant stop;
    private String bbox;
    private String wkt;
    private String summary;
    private String satellite;
    private String productType;

}
