package com.sensorweb.datacenterweatherservice.service;

import com.sensorweb.datacenterweatherservice.dao.WeatherMapper;
import com.sensorweb.datacenterweatherservice.dao.WeatherStationMapper;
import com.sensorweb.datacenterweatherservice.entity.ChinaWeather;
import com.sensorweb.datacenterweatherservice.entity.WeatherStationModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class GetWeatherInfo {

    @Autowired
    private WeatherStationMapper weatherStationMapper;
    @Autowired
    private WeatherMapper weatherMapper;

    @Value("${datacenter.path.exportDir}")
    private String exportDir;


    public List<ChinaWeather> getWeatherByPage(int pageNum, int pageSize) {
        return weatherMapper.selectByPage(pageNum, pageSize);
    }



    /**
     * 将天气信息导出为.txt文本文件,格式指定
     */
    public String exportTXT(List<ChinaWeather> chinaWeathers,String filename) {
        StringBuilder sb = new StringBuilder();
        //写文件第一行
        sb.append("Station_id_C").append("\t").append("longitude").append("\t").append("latitude").append("\t")
                .append("WIN_S_AVG_2mi").append("\t").append("TEM").append("\t").append("RHU").append("\t").append("PRS").append("\r\n");
        if (chinaWeathers!=null && chinaWeathers.size()>0) {
            for (ChinaWeather chinaWeather:chinaWeathers) {
                WeatherStationModel stationModel = weatherStationMapper.selectByStationId(chinaWeather.getStationId());
                //写文件信息
                sb.append(stationModel.getStationId()).append("\t").append(stationModel.getLon()).append("\t")
                        .append(stationModel.getLat()).append("\t").append(chinaWeather.getWindP()).append("\t")
                        .append(chinaWeather.getTemperature()).append("\t").append(chinaWeather.getHumidity()).append("\t")
                        .append(chinaWeather.getPressure()).append("\r\n");
            }
        }
        return writeTXT(sb.toString(),filename);

    }

    public String writeTXT(String str,String filename) {
        try {
            /* 写入Txt文件 */
//            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//            String timetmp = df.format(new Date());

            File writename = new File(exportDir + filename + ".txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(str);
            out.flush();
            out.close();
            return exportDir + filename + ".txt";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
