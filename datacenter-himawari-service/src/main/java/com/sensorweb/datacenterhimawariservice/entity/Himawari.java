package com.sensorweb.datacenterhimawariservice.entity;

import lombok.Data;

import java.time.Instant;


@Data
public class Himawari {
    private int id;
    private String sName = "Himawari-8";
    private String name;
    private Instant time;
    private String area = "full-disk";
    private int pixelNum = 2401;
    private int lineNum = 2401;
    private String url;
    private String localPath;
    private String waveBand = "470nm~13300nm";
    private String bandInfo = "紫外&可见光&近红外&远红外&微波";
}