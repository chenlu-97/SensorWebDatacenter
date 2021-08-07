package com.sensorweb.datacenterairservice.service;

import com.sensorweb.datacenterairservice.dao.AirQualityHourMapper;
import com.sensorweb.datacenterairservice.dao.AirStationMapper;
import com.sensorweb.datacenterairservice.dao.ChinaAirQualityHourMapper;
import com.sensorweb.datacenterairservice.dao.TWEPAMapper;
import com.sensorweb.datacenterairservice.entity.AirQualityHour;
import com.sensorweb.datacenterairservice.entity.AirStationModel;
import com.sensorweb.datacenterairservice.entity.ChinaAirQualityHour;
import com.sensorweb.datacenterairservice.entity.TWEPA;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

@Slf4j
@Service
@EnableScheduling
public class GetAirService {

    @Autowired
    private AirQualityHourMapper airQualityHourMapper;

    @Value("${datacenter.path.exportDir}")
    private String exportDir;

    @Autowired
    private AirStationMapper airStationMapper;


    @Autowired
    private TWEPAMapper twepaMapper;

    @Autowired
    private ChinaAirQualityHourMapper chinaAirQualityHourMapper;




    public List<AirQualityHour> getAll() { return airQualityHourMapper.selectAll(); }


    public List<AirQualityHour> getAirQualityHourlyByPage(int pageNum, int pageSize) {
        return airQualityHourMapper.selectByPage(pageNum, pageSize);
    }

    public List<AirQualityHour> getAirQualityHourlyByID(List<String> uniquecode,int pageNum, int pageSize) {
        return airQualityHourMapper.selectByIds(uniquecode,pageNum,pageSize);
    }


    public int getHBAirQualityHourNum() {
        return airQualityHourMapper.selectNum();
    }
    public int getCHAirQualityHourNum() {
        return chinaAirQualityHourMapper.selectNum();
    }
    public int getTWAirQualityHourNum() {
        return twepaMapper.selectNum();
    }



    public int getAirQualityHourlyNumberByID(List<String> unidecode) {
        return airQualityHourMapper.selectNumberByIds(unidecode);
    }

    /**
     * 将湖北省环境监测站的所有数据导出为txt文本文件
     */
    public String exportTXT_WH_AllData(List<AirQualityHour> airQualityHours,String filename) {
        StringBuilder sb = new StringBuilder();
        //写文件第一行
        sb.append("UniqueCode").append("\t").append("StationName").append("\t").append("PM10OneHour").append("\t").append("SO2OneHour").append("\t")
                .append("NO2OneHour").append("\t").append("COOneHour").append("\t").append("PM25OneHour").append("\t").append("O3OneHour").append("\t")
                .append("AQI").append("\r\n");
        if (airQualityHours!=null && airQualityHours.size()>0) {
            for (AirQualityHour airQualityHour:airQualityHours) {
                AirStationModel stationModel = airStationMapper.selectByStationId(airQualityHour.getUniqueCode()).get(0);
                //写文件信息
                sb.append(stationModel.getStationId()).append("\t").append(stationModel.getStationName()).append("\t")
                        .append(airQualityHour.getPm10OneHour()).append("\t").append(airQualityHour.getSo2OneHour()).append("\t")
                        .append(airQualityHour.getNo2OneHour()).append("\t").append(airQualityHour.getCoOneHour()).append("\t")
                        .append(airQualityHour.getPm25OneHour()).append("\t").append(airQualityHour.getO3OneHour()).append("\t")
                        .append(airQualityHour.getAqi()).append("\t").append("\r\n");
            }
        }
        return writeTXT2(sb.toString(),filename);

    }


    /**
     * 将湖北省环境监测站的PM2.5数据导出为txt文本文件
     */
    public String exportTXT_WH(List<AirQualityHour> airQualityHours,String filename) {
        StringBuilder sb = new StringBuilder();
        //写文件第一行
        sb.append("StationName").append("\t").append("UniqueCode").append("\t").append("longitude").append("\t").append("latitude").append("\t")
                .append("PM25OneHour").append("\r\n");
        if (airQualityHours!=null && airQualityHours.size()>0) {
            for (AirQualityHour airQualityHour:airQualityHours) {
                AirStationModel stationModel = airStationMapper.selectByStationId(airQualityHour.getUniqueCode()).get(0);
                //写文件信息
                sb.append(stationModel.getStationName()).append("\t").append(stationModel.getStationId()).append("\t").
                        append(stationModel.getLon()).append("\t").append(stationModel.getLat()).append("\t")
                        .append(airQualityHour.getPm25OneHour()).append("\r\n");
            }
        }
        return writeTXT(sb.toString(),filename);

    }

    /**
     * 将台湾EPA的PM数据导出为txt文本文件
     */
    public void exportTXT_TW(List<TWEPA> twepas,String filename) {
        StringBuilder sb = new StringBuilder();
        //写文件第一行
//        sb.append("StationName").append("\t").append("UniqueCode").append("\t").append("longitude").append("\t").append("latitude").append("\t")
//                .append("PM25OneHour").append("\r\n");
        if (twepas!=null && twepas.size()>0) {
            for (TWEPA twepa:twepas) {
                AirStationModel stationModel = airStationMapper.selectByStationId(twepa.getSiteId()).get(0);
                //写文件信息
                sb.append(stationModel.getStationName()).append("\t").append(stationModel.getStationId()).append("\t").
                        append(stationModel.getLon()).append("\t").append(stationModel.getLat()).append("\t")
                        .append(twepa.getPm25Avg()).append("\r\n");
            }
        }
//        return writeTXT3(sb.toString(),filename);

    }

    /**
     * 将全国的PM数据导出为txt文本文件
     */
    public String exportTXT_CH(List<ChinaAirQualityHour> chinaAirQualityHours,String filename) {
        StringBuilder sb = new StringBuilder();
        //写文件第一行
        sb.append("StationName").append("\t").append("UniqueCode").append("\t").append("longitude").append("\t").append("latitude").append("\t")
                .append("PM25OneHour").append("\r\n");
        if (chinaAirQualityHours!=null && chinaAirQualityHours.size()>0) {
            for (ChinaAirQualityHour chinaAirQualityHou:chinaAirQualityHours) {
                AirStationModel stationModel = airStationMapper.selectByStationId(chinaAirQualityHou.getStationCode()).get(0);
                //写文件信息
                sb.append(stationModel.getStationName()).append("\t").append(stationModel.getStationId()).append("\t").
                        append(stationModel.getLon()).append("\t").append(stationModel.getLat()).append("\t")
                        .append(chinaAirQualityHou.getPm2524h()).append("\r\n");
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


    public String writeTXT2(String str,String filename) {
        try {
            /* 写入Txt文件 */
//            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//            String timetmp = df.format(new Date());
            File writename = new File(exportDir + filename + ".txt");
            if(!writename.exists()) {
                BufferedWriter out = new BufferedWriter(new FileWriter(writename));
                out.write(str);
                out.flush();
                out.close();
                return exportDir + filename + ".txt";
            }else{
                return exportDir + filename + ".txt";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void writeTXT3(String str,String filename) {
        try {
            /* 写入Txt文件 */
//            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
//            String timetmp = df.format(new Date());
            File writename = new File(exportDir + filename + ".txt");
            if(writename.exists()) {
//                return exportDir + "null" + ".txt";
                BufferedWriter output = new BufferedWriter(new FileWriter(writename,true));//true,则追加写入text文本
                output.write(str);
                output.flush();
                output.close();
//                return exportDir + filename + ".txt";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        return null;
    }

}
