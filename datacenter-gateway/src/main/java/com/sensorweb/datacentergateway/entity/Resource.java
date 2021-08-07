package com.sensorweb.datacentergateway.entity;

import lombok.Data;

@Data
public class Resource {
    private int id;
    private String url;
    private String path;
    private String component;
    private String name;
    private String iconCls;
    private String keepAlive;
    private int requireAuth;
    private int parentId;
}
