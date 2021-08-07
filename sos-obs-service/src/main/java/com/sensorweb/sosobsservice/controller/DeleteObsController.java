package com.sensorweb.sosobsservice.controller;

import com.sensorweb.sosobsservice.service.DeleteObsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api("观测数据删除相关API")
@CrossOrigin
@RestController
public class DeleteObsController {
    @Autowired
    private DeleteObsService deleteObsService;

    @ApiOperation("通过观测数据id删除观测数据")
    @DeleteMapping("delete/{id}")
    public int deleteObservationById(@ApiParam(name = "procedureId", value = "待删除的观测数据id") @PathVariable("id") String procedureId) {
        return deleteObsService.deleteObservationById(procedureId);
    }
}
