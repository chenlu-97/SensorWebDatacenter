package com.sensorweb.datacentermobileservice.service;


import com.sensorweb.datacentermobileservice.dao.SurveyingVesselMapper;
import com.sensorweb.datacentermobileservice.entity.MeasuringVehicle;
import com.sensorweb.datacentermobileservice.entity.SurveyingVessel;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@EnableScheduling
public class SurveyingVesselService {

    @Autowired
    SurveyingVesselMapper surveyingVesselMapper;

    public List<SurveyingVessel> getDataByPage(int pageNum, int pageSize) {
        return surveyingVesselMapper.selectByPage(pageNum, pageSize);
    }



    public boolean insertData(String data) throws IOException {
        int statue = 0;
        String lon = null;
        String lat = null;
        SurveyingVessel surveyingVessel = new SurveyingVessel();
        String cp = null ; //CP=&&数据区&&  整个数据包
        System.out.println("获取到的数据为： " + data);
        if(data.startsWith("##")){
            try {
                Instant time1 = null;
                Matcher m1 = Pattern.compile("(?<=DataTime=).+?(?=;)").matcher(data);
                while (m1.find()) {
                    String time = m1.group().trim();
                    String pattern = "yyyyMMddHHmmss";
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                    dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
                    LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
                    time1 = localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant();
                    surveyingVessel.setDataTime(time1);
                }
//                Matcher cp_match = Pattern.compile("(?<=CP=&&).+?(?=&&)").matcher(data);
//                while (cp_match.find()) {
//                    cp = cp_match.group().trim();
//                }
//                if(cp.length()!=0){
//                    String[] cp_arrs = cp.split(";");
//                    for(int i = 1 ; i < cp_arrs.length; i++){
//                        String item = cp_arrs[i].substring(cp_arrs[i].indexOf("=")+1,cp_arrs[i].indexOf(","));
//                        System.out.println("item = " + item);
//                    }
//                }
                Matcher m2 = Pattern.compile("(?<=w01001-Rtd=).+?(?=,)").matcher(data);
                while (m2.find()) {
                    String w01001 = m2.group().trim();
                    surveyingVessel.setW01001(Float.valueOf(w01001));
                }
                Matcher m3 = Pattern.compile("(?<=w01003-Rtd=).+?(?=,)").matcher(data);
                while (m3.find()) {
                    String w01003 = m3.group().trim();
                    surveyingVessel.setW01003(Float.valueOf(w01003));
                }
                Matcher m4 = Pattern.compile("(?<=w01009-Rtd=).+?(?=,)").matcher(data);
                while (m4.find()) {
                    String w01009 = m4.group().trim();
                    surveyingVessel.setW01009(Float.valueOf(w01009));
                }
                Matcher m5 = Pattern.compile("(?<=w01010-Rtd=).+?(?=,)").matcher(data);
                while (m5.find()) {
                    String w01010 = m5.group().trim();
                    surveyingVessel.setW01010(Float.valueOf(w01010));
                }
                Matcher m6 = Pattern.compile("(?<=w01014-Rtd=).+?(?=,)").matcher(data);
                while (m6.find()) {
                    String w01014 = m6.group().trim();
                    surveyingVessel.setW01014(Float.valueOf(w01014));
                }
                Matcher m7 = Pattern.compile("(?<=w01016-Rtd=).+?(?=,)").matcher(data);
                while (m7.find()) {
                    String w01016 = m7.group().trim();
                    surveyingVessel.setW01016(Float.valueOf(w01016));
                }
                Matcher m8 = Pattern.compile("(?<=w01019-Rtd=).+?(?=,)").matcher(data);
                while (m8.find()) {
                    String w01019 = m8.group().trim();
                    surveyingVessel.setW01019(Float.valueOf(w01019));
                }
                Matcher m9 = Pattern.compile("(?<=w01022-Rtd=).+?(?=,)").matcher(data);
                while (m9.find()) {
                    String w01022 = m9.group().trim();
                    surveyingVessel.setW01022(Float.valueOf(w01022));
                }
                Matcher m10 = Pattern.compile("(?<=w20117-Rtd=).+?(?=,)").matcher(data);
                while (m10.find()) {
                    String w20117 = m10.group().trim();
                    surveyingVessel.setW20117(Float.valueOf(w20117));
                }
                Matcher m11 = Pattern.compile("(?<=w20125-Rtd=).+?(?=,)").matcher(data);
                while (m11.find()) {
                    String w20125 = m11.group().trim();
                    surveyingVessel.setW20125(Float.valueOf(w20125));
                }
                Matcher m12 = Pattern.compile("(?<=w20138-Rtd=).+?(?=,)").matcher(data);
                while (m12.find()) {
                    String w20138 = m12.group().trim();
                    surveyingVessel.setW20138(Float.valueOf(w20138));
                }
                Matcher m13 = Pattern.compile("(?<=w20139-Rtd=).+?(?=,)").matcher(data);
                while (m13.find()) {
                    String w20139 = m13.group().trim();
                    surveyingVessel.setW20139(Float.valueOf(w20139));
                }
                Matcher m14 = Pattern.compile("(?<=w20140-Rtd=).+?(?=,)").matcher(data);
                while (m14.find()) {
                    String w20140 = m14.group().trim();
                    surveyingVessel.setW20140(Float.valueOf(w20140));
                }
                Matcher m15 = Pattern.compile("(?<=w20140-Rtd=).+?(?=,)").matcher(data);
                while (m15.find()) {
                    String w20140 = m15.group().trim();
                    surveyingVessel.setW20141(Float.valueOf(w20140));
                }
                Matcher m16 = Pattern.compile("(?<=w20141-Rtd=).+?(?=,)").matcher(data);
                while (m16.find()) {
                    String w20141 = m16.group().trim();
                    surveyingVessel.setW20141(Float.valueOf(w20141));
                }
                Matcher m17 = Pattern.compile("(?<=w20142-Rtd=).+?(?=,)").matcher(data);
                while (m17.find()) {
                    String w20142 = m17.group().trim();
                    surveyingVessel.setW20142(Float.valueOf(w20142));
                }
                Matcher m18 = Pattern.compile("(?<=w20143-Rtd=).+?(?=,)").matcher(data);
                while (m18.find()) {
                    String w20143 = m18.group().trim();
                    surveyingVessel.setW20143(Float.valueOf(w20143));
                }
                Matcher m19 = Pattern.compile("(?<=w20144-Rtd=).+?(?=,)").matcher(data);
                while (m19.find()) {
                    String w20144 = m19.group().trim();
                    surveyingVessel.setW20144(Float.valueOf(w20144));
                }
                Matcher m20 = Pattern.compile("(?<=w20170-Rtd=).+?(?=,)").matcher(data);
                while (m20.find()) {
                    String w20170 = m20.group().trim();
                    surveyingVessel.setW20170(Float.valueOf(w20170));
                }
                Matcher m21 = Pattern.compile("(?<=w21001-Rtd=).+?(?=,)").matcher(data);
                while (m21.find()) {
                    String w21001 = m21.group().trim();
                    surveyingVessel.setW21001(Float.valueOf(w21001));
                }
                Matcher m22 = Pattern.compile("(?<=w21003-Rtd=).+?(?=,)").matcher(data);
                while (m22.find()) {
                    String w21003 = m22.group().trim();
                    surveyingVessel.setW21003(Float.valueOf(w21003));
                }
                Matcher m23 = Pattern.compile("(?<=w21011-Rtd=).+?(?=,)").matcher(data);
                while (m23.find()) {
                    String w21011 = m23.group().trim();
                    surveyingVessel.setW21011(Float.valueOf(w21011));
                }
                Matcher m24 = Pattern.compile("(?<=w21016-Rtd=).+?(?=,)").matcher(data);
                while (m24.find()) {
                    String w21016 = m24.group().trim();
                    surveyingVessel.setW21016(Float.valueOf(w21016));
                }
                Matcher m25 = Pattern.compile("(?<=w22001-Rtd=).+?(?=,)").matcher(data);
                while (m25.find()) {
                    String w22001 = m25.group().trim();
                    surveyingVessel.setW22001(Float.valueOf(w22001));
                }
                Matcher m26 = Pattern.compile("(?<=w23002-Rtd=).+?(?=,)").matcher(data);
                while (m26.find()) {
                    String w23002 = m26.group().trim();
                    surveyingVessel.setW23002(Float.valueOf(w23002));
                }
                Matcher m27 = Pattern.compile("(?<=w24003-Rtd=).+?(?=,)").matcher(data);
                while (m27.find()) {
                    String w24003 = m27.group().trim();
                    surveyingVessel.setW24003(Float.valueOf(w24003));
                }
                Matcher m28 = Pattern.compile("(?<=w24004-Rtd=).+?(?=,)").matcher(data);
                while (m28.find()) {
                    String w24004 = m28.group().trim();
                    surveyingVessel.setW24004(Float.valueOf(w24004));
                }
                Matcher m29 = Pattern.compile("(?<=w24017-Rtd=).+?(?=,)").matcher(data);
                while (m29.find()) {
                    String w24017 = m29.group().trim();
                    surveyingVessel.setW24017(Float.valueOf(w24017));
                }
                Matcher m30 = Pattern.compile("(?<=w24049-Rtd=).+?(?=,)").matcher(data);
                while (m30.find()) {
                    String w24049 = m30.group().trim();
                    surveyingVessel.setW24049(Float.valueOf(w24049));
                }
                Matcher m31 = Pattern.compile("(?<=w24050-Rtd=).+?(?=,)").matcher(data);
                while (m31.find()) {
                    String w24050 = m31.group().trim();
                    surveyingVessel.setW24050(Float.valueOf(w24050));
                }
                Matcher m32 = Pattern.compile("(?<=w24071-Rtd=).+?(?=,)").matcher(data);
                while (m32.find()) {
                    String w24071 = m32.group().trim();
                    surveyingVessel.setW24071(Float.valueOf(w24071));
                }
                Matcher m33 = Pattern.compile("(?<=w25002-Rtd=).+?(?=,)").matcher(data);
                while (m33.find()) {
                    String w25002 = m33.group().trim();
                    surveyingVessel.setW25002(Float.valueOf(w25002));
                }
                Matcher m34 = Pattern.compile("(?<=w25003-Rtd=).+?(?=,)").matcher(data);
                while (m34.find()) {
                    String w25003 = m34.group().trim();
                    surveyingVessel.setW25003(Float.valueOf(w25003));
                }
                Matcher m35 = Pattern.compile("(?<=w25004-Rtd=).+?(?=,)").matcher(data);
                while (m35.find()) {
                    String w25004 = m35.group().trim();
                    surveyingVessel.setW25004(Float.valueOf(w25004));
                }
                Matcher m36 = Pattern.compile("(?<=w25006-Rtd=).+?(?=,)").matcher(data);
                while (m36.find()) {
                    String w25006 = m36.group().trim();
                    surveyingVessel.setW25006(Float.valueOf(w25006));
                }
                Matcher m37 = Pattern.compile("(?<=w25010-Rtd=).+?(?=,)").matcher(data);
                while (m37.find()) {
                    String w25010 = m37.group().trim();
                    surveyingVessel.setW25010(Float.valueOf(w25010));
                }
                Matcher m38 = Pattern.compile("(?<=w25011-Rtd=).+?(?=,)").matcher(data);
                while (m38.find()) {
                    String w25011 = m38.group().trim();
                    surveyingVessel.setW25011(Float.valueOf(w25011));
                }
                Matcher m39 = Pattern.compile("(?<=w25013-Rtd=).+?(?=,)").matcher(data);
                while (m39.find()) {
                    String w25013 = m39.group().trim();
                    surveyingVessel.setW25013(Float.valueOf(w25013));
                }
                Matcher m40 = Pattern.compile("(?<=w25034-Rtd=).+?(?=,)").matcher(data);
                while (m40.find()) {
                    String w25034 = m40.group().trim();
                    surveyingVessel.setW25034(Float.valueOf(w25034));
                }
                Matcher m41 = Pattern.compile("(?<=w25038-Rtd=).+?(?=,)").matcher(data);
                while (m41.find()) {
                    String w25038 = m41.group().trim();
                    surveyingVessel.setW25038(Float.valueOf(w25038));
                }
                Matcher m42 = Pattern.compile("(?<=w19002-Rtd=).+?(?=,)").matcher(data);
                while (m42.find()) {
                    String w19002 = m42.group().trim();
                    surveyingVessel.setW19002(Float.valueOf(w19002));
                }
                Matcher m43 = Pattern.compile("(?<=w19011-Rtd=).+?(?=,)").matcher(data);
                while (m43.find()) {
                    String w19011 = m43.group().trim();
                    surveyingVessel.setW19011(Float.valueOf(w19011));
                }

                if(surveyingVessel != null) {
                    statue =surveyingVesselMapper.insertData(surveyingVessel);
//                    Instant end = time1;
//                    Instant begin = end.minusSeconds(2*60*60);
//                    surveyingVessel.setBegin(begin);
//                    surveyingVessel.setEnd(end);
//                    statue =surveyingVesselMapper.UpdateDataByTime(surveyingVessel);
//                    System.out.println("statue = " + statue);
//                    if(statue==0){
//                        statue =surveyingVesselMapper.insertData(surveyingVessel);
//                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return statue > 0;
        }else{
            System.out.println("获取数据内容/格式有问题，没有入库");
            return statue > 0;
        }

    }



}
