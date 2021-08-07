package com.sensorweb.datacenterofflineservice.service;


import com.sensorweb.datacenterofflineservice.dao.WH_WaterQualityMapper;
import com.sensorweb.datacenterofflineservice.entity.WH_WaterQuality;
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
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@EnableScheduling
public class GetWH_WaterQualityService {
    @Autowired
    WH_WaterQualityMapper wh_WaterQualityMapper;


    public List<WH_WaterQuality> getQualityInfoByExcel(File excel) {

        List<WH_WaterQuality> Quality = new ArrayList<>();
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
                    WH_WaterQuality wh_WaterQuality = new WH_WaterQuality();
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        Cell riverNameCell = row.getCell(2);
                        if (riverNameCell!=null) {
                            wh_WaterQuality.setRiverName(riverNameCell.getStringCellValue());
                        }
                        Cell sectionNameCell = row.getCell(3);
                        if (sectionNameCell!=null) {
                            wh_WaterQuality .setSectionName(sectionNameCell.getStringCellValue());
                        }
                        Cell timeCell = row.getCell(4);
                        if (timeCell!=null) {
                            String time = timeCell.getStringCellValue();
                            Instant query_time = string2LocalDateTime(time).toInstant(ZoneOffset.ofHours(+8));
                            wh_WaterQuality.setQueryTime(query_time);
                        }
                        Cell waterTemperatureCell = row.getCell(5);
                        if (waterTemperatureCell!=null) {
                            wh_WaterQuality.setWaterTemperature(Float.valueOf(waterTemperatureCell.getStringCellValue()));
                        }
                        Cell pHCell = row.getCell(6);
                        if (pHCell!=null) {
                            wh_WaterQuality.setPH(Float.valueOf(pHCell.getStringCellValue()));
                        }
                        Cell dissolvedOxygenCell = row.getCell(7);
                        if (dissolvedOxygenCell!=null) {
                            wh_WaterQuality.setDissolvedOxygen(Float.valueOf(dissolvedOxygenCell.getStringCellValue()));
                        }
                        Cell permanganateIndexCell = row.getCell(8);
                        if (permanganateIndexCell!=null) {
                            wh_WaterQuality.setPermanganateIndex(Float.valueOf(permanganateIndexCell.getStringCellValue()));
                        }
                        Cell biochemicalOxygenDemandCell = row.getCell(9);
                        if (biochemicalOxygenDemandCell!=null) {
                            wh_WaterQuality.setBiochemicalOxygenDemand(Float.valueOf(biochemicalOxygenDemandCell.getStringCellValue()));
                        }
                        Cell totalPhosphorusCell = row.getCell(10);
                        if (totalPhosphorusCell!=null) {
                            wh_WaterQuality.setTotalPhosphorus(Float.valueOf(totalPhosphorusCell.getStringCellValue()));
                        }
                        Cell ammoniaNitrogenCell = row.getCell(11);
                        if (ammoniaNitrogenCell!=null) {
                            wh_WaterQuality.setAmmoniaNitrogen(Float.valueOf(ammoniaNitrogenCell.getStringCellValue()));
                        }
                        Cell totalNitrogenCell = row.getCell(12);
                        if (totalNitrogenCell!=null) {
                            wh_WaterQuality.setTotalNitrogen(Float.valueOf(totalNitrogenCell.getStringCellValue()));
                        }
                        Quality.add(wh_WaterQuality);
                    }
                }
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Quality;

    }


    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertQualityInfoBatch(List<WH_WaterQuality> waterQuality) {
        int status = wh_WaterQualityMapper.insertDataBatch(waterQuality);
        return status > 0;
    }



    public static LocalDateTime string2LocalDateTime(String time) throws ParseException {
        //处理类似2020年01月格式的时间字符串为2020-09-00 00:00:00
        String str =time;
        String str1=str.substring(0, str.indexOf("年"));
        int year = Integer.valueOf(str1);
        String str2=str.substring(str.indexOf("年")+1, str.indexOf("月"));
        int month = Integer.valueOf(str2);
        int day = 01;
        int hour = 00;
        int minute = 00;
        int second = 00;
        time =year+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day)+" "+(hour<10?"0"+hour:hour)+":"+(minute<10?"0"+minute:minute)+":"+(second<10?"0"+second:second);
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return localDateTime;
    }




    public List<WH_WaterQuality> getQualityInfoByExcel2(File excel) {
        List<WH_WaterQuality> Quality = new ArrayList<>();
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
                    WH_WaterQuality wh_WaterQuality = new WH_WaterQuality();
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        Cell riverNameCell = row.getCell(0);
                        if (riverNameCell!=null) {
                            wh_WaterQuality.setRiverName(riverNameCell.getStringCellValue());
                        }
                        Cell sectionNameCell = row.getCell(1);
                        if (sectionNameCell!=null) {
                            wh_WaterQuality.setSectionName(sectionNameCell.getStringCellValue());
                        }
                        Cell timeCell = row.getCell(2);
                        if (timeCell!=null) {
                            try {
                                LocalDateTime bd = timeCell.getLocalDateTimeCellValue();
                                Instant query_time = bd.toInstant(ZoneOffset.ofHours(+8));
                                wh_WaterQuality.setQueryTime(query_time);
                            }
                            catch (Exception e){
                            String time = timeCell.getStringCellValue();
                            String pattern = "yyyy-MM-dd HH:mm";
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                            dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
                            LocalDateTime localDateTime = LocalDateTime.parse(time , dateTimeFormatter);
                            Instant query_time = localDateTime.toInstant(ZoneOffset.ofHours(+8));
                            wh_WaterQuality.setQueryTime(query_time);
                            }
                        }
                        Cell turbidityCell = row.getCell(3);
                        if (turbidityCell!=null) {
                            try {
                                BigDecimal bd = BigDecimal.valueOf(turbidityCell.getNumericCellValue());
                                wh_WaterQuality.setTurbidity(Float.valueOf(bd.toPlainString()));
                            }
                            catch (Exception e){
                                wh_WaterQuality.setTurbidity(Float.valueOf(turbidityCell.getStringCellValue()));
                            }
                        }

                        Cell chlorophyllCell = row.getCell(4);
                        if (chlorophyllCell!=null) {
                            try {
                                BigDecimal bd2 = BigDecimal.valueOf(chlorophyllCell.getNumericCellValue());
                                wh_WaterQuality.setChlorophyll(Float.valueOf(bd2.toPlainString()));
                            }
                            catch (Exception e){
                                wh_WaterQuality.setChlorophyll(Float.valueOf(chlorophyllCell.getStringCellValue()));
                            }
                        }
                        Quality.add(wh_WaterQuality);
                    }
                }
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Quality;

    }

//    public static LocalDateTime string2LocalDateTime123(String time) throws ParseException {
//        //处理类似2020/9/4 00:00:00格式的时间字符串为2020-09-00 00:00:00
//        int year = Integer.valueOf(time.substring(0,4));
//        int month = Integer.valueOf(time.substring(5,6));
//        int day = Integer.valueOf(time.substring(7,8));
//        int other = Integer.valueOf(time.substring(9));
//        String query_time =year+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day)+" "+other;
//        String pattern = "yyyy-MM-dd HH:mm:ss";
//        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
//        dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
//        LocalDateTime localDateTime = LocalDateTime.parse(query_time, dateTimeFormatter);
//        return localDateTime;
//    }




    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertDataBatch_AutoStation(List<WH_WaterQuality> waterQuality) {
        int status = wh_WaterQualityMapper.insertDataBatch_AutoStation(waterQuality);
        return status > 0;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertWH_WaterQuality_AutoStation(WH_WaterQuality waterQuality) {
        int status = wh_WaterQualityMapper.insertWH_WaterQuality_AutoStation(waterQuality);
        return status > 0;
    }

    public int getWHWaterQualityNum() {
        int count = wh_WaterQualityMapper.selectNum1() + wh_WaterQualityMapper.selectNum2();;
        return count;
    }
}
