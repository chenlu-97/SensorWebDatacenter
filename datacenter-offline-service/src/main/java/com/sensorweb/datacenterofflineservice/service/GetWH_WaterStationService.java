package com.sensorweb.datacenterofflineservice.service;


import com.alibaba.fastjson.JSONObject;
import com.sensorweb.datacenterofflineservice.dao.WH_WaterStationMapper;
import com.sensorweb.datacenterofflineservice.entity.WH_WaterStation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@EnableScheduling
public class GetWH_WaterStationService {

    @Autowired
    WH_WaterStationMapper wh_WaterStationMapper;


    public List<WH_WaterStation> getStationInfoByExcel(File excel) {
        List<WH_WaterStation> stations = new ArrayList<>();
        try {
            if (excel.isFile() && excel.exists()) {   //判断文件是否存在
                String[] split = excel.getName().split("\\.");  //.是特殊字符，需要转义！！！！！
                Workbook wb;
                //根据文件后缀（xls/xlsx）进行判断
                if ( "xls".equals(split[1])){
                    FileInputStream fis = new FileInputStream(excel);   //文件流对象
                    wb = new HSSFWorkbook(fis);
                }else if ("xlsx".equals(split[1])){
                    wb = new XSSFWorkbook(excel);
                }else {
                    System.out.println("文件类型错误!");
                    return null;
                }

                //开始解析
                Sheet sheet = wb.getSheetAt(0);     //读取sheet 0

                int firstRowIndex = sheet.getFirstRowNum()+1;   //第一行是列名，所以不读
                int lastRowIndex = sheet.getLastRowNum();

                for(int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {   //遍历行
                    WH_WaterStation wh_WaterStation = new WH_WaterStation();
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        Cell riverNameCell = row.getCell(1);
                        if (riverNameCell!=null) {
                            wh_WaterStation.setRiverName(riverNameCell.getStringCellValue());
                        }
                        Cell sectionNameCell = row.getCell(2);
                        if (sectionNameCell!=null) {
                            wh_WaterStation.setSectionName(sectionNameCell.getStringCellValue());
                            wh_WaterStation.setStationId(sectionNameCell.getStringCellValue());
                        }

                        Cell lonCell = row.getCell(3);
                        if (lonCell!=null) {
                            BigDecimal bd = BigDecimal.valueOf(lonCell.getNumericCellValue());
                            wh_WaterStation.setLon(Float.valueOf(bd.toPlainString()));
                        }
                        Cell latCell = row.getCell(4);
                        if (latCell!=null) {
                            BigDecimal bd = BigDecimal.valueOf(latCell.getNumericCellValue());
                            wh_WaterStation.setLat(Float.valueOf(bd.toPlainString()));
                        }
                        Cell typeCell = row.getCell(5);
                        if (typeCell!=null) {
                            wh_WaterStation.setStationType(typeCell.getStringCellValue());
                        }
                        wh_WaterStation.setGeom("POINT(" + lonCell.getNumericCellValue() + ' ' + latCell.getNumericCellValue() + ")");
                        wh_WaterStation.setStationTypeOut("wh_1+8_Water");
                        stations.add(wh_WaterStation);
                    }
                }
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stations;

    }



    //插入到Station集合表中
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertStationInfoBatchInStation(List<WH_WaterStation> waterStation) {
        int status = wh_WaterStationMapper.insertDataBatchInStation(waterStation);
        return status > 0;
    }


    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertStationInfoBatch(List<WH_WaterStation> waterStation) {
        int status = wh_WaterStationMapper.insertDataBatch(waterStation);
        return status > 0;
    }
}
