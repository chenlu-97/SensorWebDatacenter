package com.sensorweb.datacentergeeservice.service;

import com.sensorweb.datacentergeeservice.dao.SentinelMapper;
import com.sensorweb.datacentergeeservice.entity.Sentinel;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class SentinelService {


    @Autowired
    SentinelMapper sentinelMapper;


    public List<Sentinel> getAll() {
        return sentinelMapper.selectAll();
    }

    public List<Sentinel> getByattribute(String spacecraftID, String Date, String Cloudcover, String imageType) {

        return sentinelMapper.selectByattribute(spacecraftID,Date,Cloudcover,imageType);
    }

    public List<Sentinel> getSentinelByPage(int pageNum, int pageSize) {
        return sentinelMapper.selectByPage(pageNum,pageSize);
    }

    public int getSentinelNum() {
        return sentinelMapper.selectNum();
    }


    @Scheduled(cron = "0 20 11 * * ?") //每天上午11:20点接入
    public void getSentinel() {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Shanghai"));
        String time = formatter.format(dateTime);
        LocalDateTime begin_date = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        LocalDateTime end_date = begin_date.plusSeconds(24 * 60 * 60);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("Asia/Shanghai"));
        String begin = formatter1.format(begin_date);
        String end = formatter1.format(end_date);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                        MODIS/006/MCD12Q1
//                        MODIS/006/MOD13A2
//                        MODIS/006/MOD11A2
//                        MODIS/006/MOD11A1
//                        COPERNICUS/S2_SR
                    String Landsat8 = "COPERNICUS/S2_SR";
                    String url = "http://172.16.100.2:8009/getSentinel/";
                    String param = "image=" + Landsat8 + "&startDate=" + begin + "&endDate=" + end;
                    String statue = DataCenterUtils.doGet(url, param);
                    if (statue == "0") {
                        log.info("------ 目前暂无影像！！！！！-----");
                        System.out.println("------ 目前暂无影像！！！！！-----");
                    } else if (statue == "fail") {
                        log.info("------ 连接GEE失败-----");
                        System.out.println("------ 连接GEE失败-----");
                    } else if (statue == "success") {
                        log.info("------获取影像成功！！！！！！！-----");
                        System.out.println("------ 获取影像成功！！！！！！！-----");
                        DataCenterUtils.sendMessage("Sentinel-2A" + time, "Sentinel-2A", "GEE获取的Sentinel-2A影像成功");
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    log.info("GEE获取影像Sentinel-2A " + time + "Status: Fail");
                    System.out.println(e.getMessage());
                }
            }
        }).start();
    }
}
