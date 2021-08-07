package com.sensorweb.datacenterproductservice.entity;

import lombok.Data;

/**
 * 生成产品的信息模型
 */

@Data
public class GenProduct {
    private int id;
    private String productType;
    private String dataNeeded;
    private String moduleNeeded;
    private String region;
}
