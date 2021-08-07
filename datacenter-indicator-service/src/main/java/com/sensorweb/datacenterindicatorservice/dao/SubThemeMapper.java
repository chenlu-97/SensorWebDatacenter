package com.sensorweb.datacenterindicatorservice.dao;

import com.sensorweb.datacenterindicatorservice.entity.SubTheme;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SubThemeMapper {
    int insertData(SubTheme subTheme);
    int insertDataBatch(List<SubTheme> subThemes);
}
