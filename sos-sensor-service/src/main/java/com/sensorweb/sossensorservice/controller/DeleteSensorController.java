package com.sensorweb.sossensorservice.controller;

import com.sensorweb.sossensorservice.service.DeleteSensorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Api("传感器删除相关API")
@Slf4j
@CrossOrigin
public class DeleteSensorController {
    @Autowired
    private DeleteSensorService deleteSensorService;

    @ApiOperation("删除传感器,同时删除该传感器的所有观测数据,如果为平台,则删除平台下所有的传感器")
    @DeleteMapping("delete/{id}")
    public boolean deleteSensorById(@ApiParam(name = "procedureId", value = "传感器或平台id") @PathVariable("id") String procedureId) {
        boolean flag = false;
        try {
            flag = deleteSensorService.deleteSensor(procedureId);
        } catch (Exception e) {
            log.info("传感器删除失败");
        }
        return flag;
    }
}
