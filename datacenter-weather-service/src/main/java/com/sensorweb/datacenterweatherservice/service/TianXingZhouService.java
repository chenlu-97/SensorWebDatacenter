package com.sensorweb.datacenterweatherservice.service;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.datacenterweatherservice.dao.TianXingZhouMapper;
import com.sensorweb.datacenterweatherservice.entity.TianXingZhouStation;
import com.sensorweb.datacenterweatherservice.util.WeatherConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Slf4j
@Service
@EnableScheduling
public class TianXingZhouService {
    @Autowired
    TianXingZhouMapper tianXingZhouMapper;

    /**
     * 每小时接入一次数据
     */
    @Scheduled(cron = "0 */1 * * * ?") //每分钟接入
    public void insertDataByHour() {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String flag = getIOTInfo();
                    if (flag.equals("接入成功")) {
                        log.info("天兴洲综合站接入时间: " + dateTime.toString() + "Status: Success");
                        System.out.println("天兴洲综合站接入时间: " + dateTime.toString() + "Status: Success");
                        DataCenterUtils.sendMessage("TianXingZhou_Weather_"+dateTime.toString(), "站网-天兴洲综合站","这是一条武汉天兴洲综合站气象数据的");
                    }else if(flag.equals("接入失败")){
                        log.info("天兴洲综合站接入时间: " + dateTime.toString() + "Status: fail");
                        System.out.println("天兴洲综合站接入时间: " + dateTime.toString() + "Status: fail");
                    }else{
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                    e.printStackTrace();
                    log.info("天兴洲综合站接入时间: " + dateTime.toString() + "Status: fail");
                    System.out.println("天兴洲综合站接入时间: " + dateTime.toString() + "Status: fail");
                }
            }
        }).start();
    }


    /**
     * 通过调用天兴洲传感器IOT的JSON接口获取对应的传感器ID的气象数据，最后以json对象的形式返回
     */
    public JSONObject getIOTResultByID(String Id) throws IOException {
        String param = "";
        String url = WeatherConstant.TianXingZhou_URL + "/" + Id + ".json";
        String document = DataCenterUtils.doGet(url, param);
        if (document != null) {
            return JSON.parseObject(document);
        }
        return null;
    }

    /**
     *
     */

    public String getIOTInfo() throws IOException {
        int statue = 0;
        String station_id = "868739058202031";
        String[] ids = station_id.split(",");
        for (String id : ids) {
            JSONObject jsonObject = getIOTResultByID(id);
//            System.out.println(jsonObject);
            TianXingZhouStation tianXingZhouStation = new TianXingZhouStation();
            if (jsonObject != null) {
                String datetime = jsonObject.get("datetime").toString();
                JSONArray datas = jsonObject.getJSONArray("data");
                tianXingZhouStation.setStationId(id);
                tianXingZhouStation.setLon(Float.valueOf("123.34"));
                tianXingZhouStation.setLat(Float.valueOf("32.35"));
                tianXingZhouStation.setQueryTime(DataCenterUtils.string2Instant(datetime));
                List<TianXingZhouStation> timeSelect = tianXingZhouMapper.selectByTime(DataCenterUtils.string2Instant(datetime));
                if(timeSelect.size()>0){
                    return "已存在数据";
                }
                tianXingZhouStation.setGeom("POINT(123.34 32.35)");

                if(!datas.getJSONObject(1).get("value").toString().isEmpty()) {
                    tianXingZhouStation.setAirHumidity(Float.valueOf(datas.getJSONObject(1).get("value").toString()));
                }
                if(!datas.getJSONObject(0).get("value").toString().isEmpty()) {
                    tianXingZhouStation.setAirTemperature(Float.valueOf(datas.getJSONObject(0).get("value").toString()));
                }
                if(!datas.getJSONObject(2).get("value").toString().isEmpty()){
                    tianXingZhouStation.setPressure(Float.valueOf(datas.getJSONObject(2).get("value").toString()));
                }
                if(!datas.getJSONObject(3).get("value").toString().isEmpty()){
                    tianXingZhouStation.setWindSpeed(Float.valueOf(datas.getJSONObject(3).get("value").toString()));
                }
                if(!datas.getJSONObject(4).get("value").toString().isEmpty()){
                    tianXingZhouStation.setWindDirection(datas.getJSONObject(4).get("value").toString());
                }
                if(!datas.getJSONObject(5).get("value").toString().isEmpty()){
                    tianXingZhouStation.setSo2(Float.valueOf(datas.getJSONObject(5).get("value").toString()));
                }
                if(!datas.getJSONObject(6).get("value").toString().isEmpty()){
                    tianXingZhouStation.setO3(Float.valueOf(datas.getJSONObject(6).get("value").toString()));
                }
                if(!datas.getJSONObject(7).get("value").toString().isEmpty()){
                    tianXingZhouStation.setCo(Float.valueOf(datas.getJSONObject(7).get("value").toString()));
                }
                if(!datas.getJSONObject(8).get("value").toString().isEmpty()){
                    tianXingZhouStation.setNo2(Float.valueOf(datas.getJSONObject(8).get("value").toString()));
                }
                if(!datas.getJSONObject(9).get("value").toString().isEmpty()){
                    tianXingZhouStation.setPm25(Float.valueOf(datas.getJSONObject(9).get("value").toString()));
                }
                if(!datas.getJSONObject(10).get("value").toString().isEmpty()){
                    tianXingZhouStation.setPm10(Float.valueOf(datas.getJSONObject(10).get("value").toString()));
                }
//                tianXingZhouStation.setSoilTemperature(Float.valueOf(datas.getJSONObject(0).get("value").toString()));
//                tianXingZhouStation.setSoilHumidity(Float.valueOf(datas.getJSONObject(1).get("value").toString()));
                statue = tianXingZhouMapper.insertData(tianXingZhouStation);
            }
        }
        if(statue>0){
            return  "接入成功";
        }else{
            return  "接入失败";
        }
    }


    public List<TianXingZhouStation> getDataByPage(int pageNum, int pageSize) {
        return tianXingZhouMapper.selectByPage(pageNum, pageSize);
    }


}
