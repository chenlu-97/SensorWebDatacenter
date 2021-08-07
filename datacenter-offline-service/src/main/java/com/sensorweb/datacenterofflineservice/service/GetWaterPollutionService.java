package com.sensorweb.datacenterofflineservice.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensorweb.datacenterofflineservice.dao.WaterPollutionMapper;
import com.sensorweb.datacenterofflineservice.dao.WaterPollutionStationMapper;
import com.sensorweb.datacenterofflineservice.entity.WaterPollution;
import com.sensorweb.datacenterofflineservice.entity.WaterPollutionStation;
import com.sensorweb.datacenterofflineservice.util.OfflineConstant;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
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
import java.io.IOException;
import java.net.URLEncoder;
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
public class GetWaterPollutionService {

    @Autowired
    WaterPollutionMapper waterPollutionMapper;

    public List<WaterPollution> getInfoByExcel(File excel) {
        List<WaterPollution> stations = new ArrayList<>();
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
                    WaterPollution waterPollution = new WaterPollution();
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        Cell companyNameCell = row.getCell(0);
                        if (companyNameCell!=null) {
                            waterPollution.setCompanyName(companyNameCell.getStringCellValue());
                        }
                        Cell stationNameCell = row.getCell(5);
                        if (stationNameCell!=null) {
                            waterPollution.setStationName(stationNameCell.getStringCellValue());
                        }

                        Cell stationTypeCell = row.getCell(6);
                        if (stationTypeCell!=null) {
                            waterPollution.setStationType(stationTypeCell.getStringCellValue());
                        }

                        Cell stationItemCell = row.getCell(7);
                        if (stationItemCell!=null) {
                            waterPollution.setStationItem(stationItemCell.getStringCellValue());
                        }

                        Cell flowCell = row.getCell(8);
                        if (flowCell!=null) {
                            try {
                                waterPollution.setFlow(Float.valueOf(flowCell.getStringCellValue()));
                            }catch(Exception e){
                                waterPollution.setFlow(0);
                            }
                        }
                        Cell concentrationCell = row.getCell(9);
                        if (concentrationCell!=null) {
                            try {
                                waterPollution.setConcentration(Float.valueOf(concentrationCell.getStringCellValue()));
                            }catch(Exception e){
                                waterPollution.setConcentration(0);
                            }
                        }

                        Cell isOverStandardCell = row.getCell(10);
                        if (isOverStandardCell!=null) {
                            waterPollution.setIsOverStandard(isOverStandardCell.getStringCellValue());
                        }

                        Cell OverStandardReasonCell = row.getCell(11);
                        if (OverStandardReasonCell!=null) {
                            waterPollution.setOverStandardReason(OverStandardReasonCell.getStringCellValue());
                        }
                        Cell emissionCapCell = row.getCell(12);
                        if (emissionCapCell!=null) {
                            try {
                                waterPollution.setEmissionCap(Float.valueOf(emissionCapCell.getStringCellValue()));
                            }catch(Exception e){
                                waterPollution.setEmissionCap(0);
                            }
                        }
                        Cell emissionLimitCell = row.getCell(13);
                        if (emissionLimitCell!=null) {
                            try {
                                waterPollution.setEmissionLimit(Float.valueOf(emissionLimitCell.getStringCellValue()));
                            }catch(Exception e){
                                waterPollution.setEmissionLimit(0);
                            }
                        }
                        Cell monitoringTimeCell = row.getCell(15);
                        if (monitoringTimeCell!=null) {
                            String time = monitoringTimeCell.getStringCellValue()+" ";
                            LocalDateTime localDateTime = string2LocalDateTime(time);
                            Instant query_time = localDateTime.toInstant(ZoneOffset.ofHours(+8));
                            waterPollution.setMonitoringTime(query_time);
                        }
//                        JSONObject jsonObject = null;
//                        if (stationNameCell!=null) {
//                            jsonObject = getGeoAddress(stationNameCell.getStringCellValue());
//                        }
//                        if (jsonObject!=null) {
//                            Object result = jsonObject.get("result");
//                            if (result!=null) {
//                                Object location = ((JSONObject) result).get("location");
//                                Double lon= ((JSONObject) location).getDoubleValue("lng");
//                                Double lat = ((JSONObject) location).getDoubleValue("lat");
//                                waterPollution.setLon(lon);
//                                waterPollution.setLat(lat);
//                                waterPollution.setGeom("POINT(" + lon.toString() + ' ' + lat.toString() + ")");
//                            }
//                        }
//                        Double lon= 0.1;
//                        Double lat = 0.1;
//                        waterPollution.setLon(lon);
//                        waterPollution.setLat(lat);
//                        waterPollution.setGeom("POINT(" + lon.toString() + ' ' + lat.toString() + ")");
                        stations.add(waterPollution);
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

    /**
     * 根据地址获取经纬度信息,转换成JSON对象返回
     * @param address
     */
    public JSONObject getGeoAddress(String address) throws IOException {
        String param = "address=" + URLEncoder.encode(address, "utf-8") + "&output=json" + "&ak=" + OfflineConstant.BAIDU_AK;
        String document = DataCenterUtils.doGet(OfflineConstant.BAIDU_ADDRESS_API, param);
        if (document!=null) {
            return JSON.parseObject(document);
        }
        return null;
    }


        public static LocalDateTime string2LocalDateTime(String time) throws ParseException {
        //处理类似2020-9-4 格式的时间字符串为2020-09-00 00:00:00
        String str =time;
        String str1=str.substring(0, str.indexOf("-"));
        int index=str.indexOf("-");
        //根据第一个点的位置 获得第二个点的位置
        index=str.indexOf("-", index+1);
        String str2=str.substring(str1.length()+1, index);
        String str3 = str.substring(index+1, str.indexOf(" "));
        int year = Integer.valueOf(str1);
        int month = Integer.valueOf(str2);
        int day = Integer.valueOf(str3);
        time =year+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day)+" "+"00:00:00";
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return localDateTime;
    }

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertDataInfo(WaterPollution waterPollution) {
        int status = waterPollutionMapper.insertWaterPollution(waterPollution);
        return status > 0;
    }

    public int getHBWaterPollutionNum() {
        return waterPollutionMapper.selectNum();
    }
}
