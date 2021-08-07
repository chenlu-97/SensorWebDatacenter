package com.sensorweb.datacenterofflineservice.service;


import com.sensorweb.datacenterofflineservice.dao.WFMapper;
import com.sensorweb.datacenterofflineservice.entity.WF;
import com.sensorweb.datacenterofflineservice.util.OfflineConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Slf4j
@Service
@EnableScheduling
public class GetWFService {

    @Autowired
    private WFMapper wfMapper;

    public int insertWeatherForecast() throws Exception{
        int status = 0;
        WF weatherforecast = new WF();
        ArrayList<String> files = getFiles(OfflineConstant.WF_PATH);
            for (int j = 0; j < files.size(); j++) {
                System.out.println(files.get(j));
                    String filepath = OfflineConstant.WF_PATH + "/" + files.get(j)+"/";
                    File[] list = new File(filepath).listFiles();
                    for (File file : list) {
                        if (file.isFile()) {
                            if (file.getName().endsWith(".nc")) {
                                System.out.println(filepath+file.getName());
                                weatherforecast.setImageId(file.getName());
                                weatherforecast.setFilePath(filepath+file.getName());

//                                NetcdfFile openNC = NetcdfFile.open(filepath+file.getName());  //filePath:文件地址
//                                System.out.println(openNC);
//                                Dimension lon = openNC.findDimension("lon");  //"n"这个方法是找到变量名称为n的变量
//                                System.out.println(lon.toString());
//                                Dimension lat = openNC.findDimension("lat");  //"n"这个方法是找到变量名称为n的变量
//                                System.out.println(lat.toString());
//                                openNC.close();
//                                System.out.println(string2LocalDateTime(file.getName()));
                                weatherforecast.setQueryTime(string2LocalDateTime(file.getName()).toInstant(ZoneOffset.ofHours(+8)));
                                weatherforecast.setGeoType(1);
                                status = wfMapper.insertWF(weatherforecast);
                            }
                        }
                    }
                }
//            }

        return status;
    }


    //读取文件路径，获取文件下的所有文件的名称，返回文件名list
    public ArrayList<String> getFiles(String filepath) {
        ArrayList<String> file_names = new ArrayList<>();
        File dirFile = new File(filepath);
        if (dirFile.exists()) {
            File[] files = dirFile.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i ++) {
                    //输出文件名或者文件夹名
                    file_names.add(files[i].getName());
//                    System.out.println(files[i].getName());
                }
            }
        } else {
            System.out.println("查找的文件不存在");
        }
        return file_names;
    }


    public static LocalDateTime string2LocalDateTime(String time) throws ParseException {
        //处理类似xxxx.202091700.xxxx格式的时间字符串为2020-09-17 00:00:00
        String str =time;
        String str1=str.substring(0, str.indexOf("."));
        int index=str.indexOf(".");
        //根据第一个点的位置 获得第二个点的位置
        index=str.indexOf(".", index+1);
        String str2=str.substring(str1.length()+1, index);
//        System.out.println(str2);
        int year = Integer.valueOf(str2.substring(0,4));
        int month = Integer.valueOf(str2.substring(4,6));
        int day = Integer.valueOf(str2.substring(6,8));
        int hour = Integer.valueOf(str2.substring(8,10));
        int minute = 00;
        int second = 00;
        time =year+"-"+(month<10?"0"+month:month)+"-"+(day<10?"0"+day:day)+" "+(hour<10?"0"+hour:hour)+":"+(minute<10?"0"+minute:minute)+":"+(second<10?"0"+second:second);
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return localDateTime;
    }


    //行列转为经纬度

//    public static double[] XYZtoLonlat(int z, int x, int y) {
//
//        double n = Math.pow(2, z);
//        double lon = x / n * 360.0 - 180.0;
//        double lat = Math.atan(Math.sinh(Math.PI * (1 - 2 * y / n)));
//        lat = lat * 180.0 / Math.PI;
//        double[] lonlat = new double[2];
//        lonlat[0] = lon;
//        lonlat[1] = lat;
//        return lonlat;
//    }


}
