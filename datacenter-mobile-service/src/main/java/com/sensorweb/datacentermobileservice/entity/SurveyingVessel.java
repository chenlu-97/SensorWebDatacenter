package com.sensorweb.datacentermobileservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;


@Data

public class SurveyingVessel {
    private int id;
    private float w20138;
    private float w20139;
    private float w20140;
    private float w20142;
    private float w20143;
    private float w20144;
    private float w24003;
    private float w24071;
    private float w20170;
    private float w24004;
    private float w24017;
    private float w25002;
    private float w24049;
    private float w25003;
    private float w24050;
    private float w25010;
    private float w25004;
    private float w25073;
    private float w25038;
    private float w25006;
    private float w25034;
    private float w25013;
    private float w25011;
    private float w21016;
    private float w23002;
    private float w21003;
    private float w19002;
    private float w20117;
    private float w01019;
    private float w21011;
    private float w21001;
    private float w20125;
    private float w20141;
    private float w22001;
    private float w01001;
    private float w01014;
    private float w01010;
    private float w01009;
    private float w01003;
    private float w01022;
    private float w01016;
    private float w19011;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Instant dataTime;
    private float lon;
    private float lat;


    private Instant begin;
    private Instant end ;
}
