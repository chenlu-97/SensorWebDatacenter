package com.sensorweb.sossensorservice.entity;

import lombok.Data;

@Data
public class Procedure {
    private String id;
    private String name;
    private String description;
    private int isPlatform;//0代表非平台，1代表平台
    private int status;
    private String descriptionFile;
}
