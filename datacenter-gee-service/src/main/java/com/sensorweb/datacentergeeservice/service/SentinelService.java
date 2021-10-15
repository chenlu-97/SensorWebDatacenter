package com.sensorweb.datacentergeeservice.service;

import com.sensorweb.datacentergeeservice.dao.SentinelMapper;
import com.sensorweb.datacentergeeservice.entity.Sentinel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
