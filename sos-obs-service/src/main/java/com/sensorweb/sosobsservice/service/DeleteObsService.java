package com.sensorweb.sosobsservice.service;

import com.sensorweb.sosobsservice.dao.ObservationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteObsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeleteObsService.class);

    @Autowired
    private ObservationMapper observationMapper;

    /**
     * 根据procedureId删除观测数据信息
     */
    public int deleteObservationById(String procedureId) {
        return observationMapper.deleteByProcedureId(procedureId);
    }
}
