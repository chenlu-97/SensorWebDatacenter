package com.sensorweb.datacentergeeservice.service;

import com.sensorweb.datacentergeeservice.dao.LandsatMapper;
import com.sensorweb.datacentergeeservice.dao.ModisMapper;
import com.sensorweb.datacentergeeservice.entity.Landsat;
import com.sensorweb.datacentergeeservice.entity.Modis;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Slf4j
@Service
public class ModisService {


    @Autowired
    ModisMapper modisMapper;
    public List<Modis> getModisByPage(int pageNum, int pageSize) {
        return modisMapper.selectByPage(pageNum,pageSize);
    }
}
