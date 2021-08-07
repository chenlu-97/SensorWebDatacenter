package com.sensorweb.datacenterweatherservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.datacenterweatherservice.dao.WeatherStationMapper;
import com.sensorweb.datacenterweatherservice.entity.WeatherStationModel;
import com.sensorweb.datacenterweatherservice.util.WeatherConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class InsertStationInfo implements WeatherConstant {

    @Autowired
    private WeatherStationMapper weatherStationMapper;

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public boolean insertStationInfoBatch(List<WeatherStationModel> weatherStationModels) {
        int status = weatherStationMapper.insertDataBatch(weatherStationModels);
        return status > 0;
    }

    public List<WeatherStationModel> getStationInfoByExcel(File excel) {
        List<WeatherStationModel> stations = new ArrayList<>();
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
                    WeatherStationModel weatherStationModel = new WeatherStationModel();
                    Row row = sheet.getRow(rIndex);
                    if (row != null) {
                        Cell stationIdCell = row.getCell(0);
                        if (stationIdCell != null) {
                            BigDecimal bd = BigDecimal.valueOf(stationIdCell.getNumericCellValue());
                            weatherStationModel.setStationId(bd.toPlainString());
                        }
                        Cell townshipCell = row.getCell(1);
                        if (townshipCell!=null) {
                            weatherStationModel.setStationName(townshipCell.getStringCellValue());
                            weatherStationModel.setTownship(townshipCell.getStringCellValue());
                        }
                        Cell regionCell = row.getCell(2);
                        if (regionCell!=null) {
                            weatherStationModel.setRegion(regionCell.getStringCellValue());
                        }
                        Cell cityCell = row.getCell(3);
                        if (cityCell!=null) {
                            weatherStationModel.setCity(cityCell.getStringCellValue());
                        }
                        Cell proCell = row.getCell(4);
                        if (proCell!=null) {
                            weatherStationModel.setProvince(proCell.getStringCellValue());
                        }
                        JSONObject jsonObject = null;
                        if (weatherStationModel.getStationName()!=null) {
                            jsonObject = getGeoAddress(weatherStationModel.getStationName());
                        }
                        if (jsonObject!=null) {
                            Object result = jsonObject.get("result");
                            if (result!=null) {
                                Object location = ((JSONObject) result).get("location");
                                weatherStationModel.setLon(((JSONObject) location).getDoubleValue("lng"));
                                weatherStationModel.setLat(((JSONObject) location).getDoubleValue("lat"));
                            }
                        }
                        weatherStationModel.setStationType("wh_1+8_weather");
                        stations.add(weatherStationModel);
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
        String param = "address=" + URLEncoder.encode(address, "utf-8") + "&output=json" + "&ak=" + WeatherConstant.BAIDU_AK;
        String document = DataCenterUtils.doGet(WeatherConstant.BAIDU_ADDRESS_API, param);
        if (document!=null) {
            return JSON.parseObject(document);
        }
        return null;
    }
}
