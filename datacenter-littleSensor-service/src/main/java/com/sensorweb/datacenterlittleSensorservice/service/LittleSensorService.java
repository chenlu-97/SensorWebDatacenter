package com.sensorweb.datacenterlittleSensorservice.service;

import com.sensorweb.datacenterlittleSensorservice.dao.LittleSensorMapper;
import com.sensorweb.datacenterlittleSensorservice.feign.ObsFeignClient;
import com.sensorweb.datacenterlittleSensorservice.feign.SensorFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@EnableScheduling
public class LittleSensorService {

    @Autowired
    private LittleSensorMapper littleSensorMapper;

    @Autowired
    private ObsFeignClient obsFeignClient;

    @Autowired
    private SensorFeignClient sensorFeignClient;

}
