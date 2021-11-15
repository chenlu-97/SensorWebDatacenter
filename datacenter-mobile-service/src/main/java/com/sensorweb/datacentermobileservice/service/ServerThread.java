package com.sensorweb.datacentermobileservice.service;

import com.sensorweb.datacentermobileservice.util.ApplicationContextUtil;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.net.Socket;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 服务端线程类
 *
 *
 */
public class ServerThread extends Thread {


    @Value("${datacenter.path.mobile.tmpDir}")
    private String exportDir;

    //本线程相关的socket
    Socket socket = null;
    public ServerThread(Socket socket) {
        this.socket = socket;
    }

    @SuppressWarnings("deprecation")
    public void run() {
        try{
            InputStream is = socket.getInputStream();
            //获取到输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            byte[] buf = new byte[1024];
            //接收收到的数据
            int line = 0;
            while ((line = is.read(buf)) != -1) {
                String data = new String(buf, 0, line);
                String time1 = null;

                Matcher m1 = Pattern.compile("(?<=DataTime=).+?(?=;)").matcher(data);
                while (m1.find()) {
                    String time = m1.group().trim();
                    String pattern = "yyyyMMddHHmmss";
                    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                    dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
                    LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
                    time1 = localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant().toString();
                }

                boolean i = false;
                if (data.startsWith("##")) {

                    Matcher data_type1 = Pattern.compile("a01001-Rtd").matcher(data);
                    while (data_type1.find()) {
                        MeasuringVehicleService measuringVehicleService = (MeasuringVehicleService) ApplicationContextUtil.getBean("measuringVehicleService");
                        i = measuringVehicleService.insertVocsData(data);
//                        data2txt("measuring_vehicle_vocs_"+replace(time1),data);
//                        DataCenterUtils.sendMessage("MeasuringVehicle_vocs_"+ time1, "测量车VOCs数据","这是一条测量车推送的VOCs数据");
                        if (i == true) {
                            System.out.println("测量车VOCs数据接收成功!!!!");
                        } else {
                            System.out.println("测量车VOCs数据接收失败!!!!");
                        }
                    }

                    Matcher data_type2 = Pattern.compile("a21026-Avg").matcher(data);
                    while (data_type2.find()) {
                        MeasuringVehicleService measuringVehicleService = (MeasuringVehicleService) ApplicationContextUtil.getBean("measuringVehicleService");
                        i = measuringVehicleService.insertAirData(data);
//                        data2txt("measuring_vehicle_air_"+replace(time1),data);
//                        DataCenterUtils.sendMessage("MeasuringVehicle_air_"+ time1, "测量车空气数据","这是一条测量车推送的空气数据");
                        if (i == true) {
                            System.out.println("测量车气态数据接收成功!!!!");
                        } else {
                            System.out.println("测量车气态数据接收失败!!!!");
                        }
                    }
                    Matcher data_type3 = Pattern.compile("a34002-Avg").matcher(data);
                    while (data_type3.find()) {
                        MeasuringVehicleService measuringVehicleService = (MeasuringVehicleService) ApplicationContextUtil.getBean("measuringVehicleService");
                        i = measuringVehicleService.insertPMData(data);
//                        data2txt("measuring_vehicle_PM_"+replace(time1),data);
//                        DataCenterUtils.sendMessage("MeasuringVehicle_PM_"+ time1, "测量车PM数据","这是一条测量车推送的PM数据");
                        if (i == true) {
                            System.out.println("测量车颗粒物数据接收成功!!!!");
                        } else {
                            System.out.println("测量车颗粒物数据接收失败!!!!");
                        }
                    }
                    Matcher data_type4 = Pattern.compile("a340081-Avg").matcher(data);
                    while (data_type4.find()) {
                        MeasuringVehicleService measuringVehicleService = (MeasuringVehicleService) ApplicationContextUtil.getBean("measuringVehicleService");
                        i = measuringVehicleService.insertHTData(data);
//                        data2txt("measuring_vehicle_HT_"+replace(time1),data);
//                        DataCenterUtils.sendMessage("MeasuringVehicle_HT_"+ time1, "测量车黑炭数据","这是一条测量车推送的黑炭数据");
                        if (i == true) {
                            System.out.println("测量车黑炭数据接收成功!!!!");
                        } else {
                            System.out.println("测量车黑炭数据接收失败!!!!");
                        }
                    }
                    Matcher data_type5 = Pattern.compile("b60004-Avg").matcher(data);
                    while (data_type5.find()) {
                        MeasuringVehicleService measuringVehicleService = (MeasuringVehicleService) ApplicationContextUtil.getBean("measuringVehicleService");
                        i = measuringVehicleService.insertSPMSData(data);
//                        DataCenterUtils.sendMessage("MeasuringVehicle_SPMS_"+ time1, "测量车单颗粒质谱仪数据","这是一条测量车推送的单颗粒质谱仪数据：");
//                        data2txt("measuring_vehicle_SPMS_"+replace(time1),data);
                        if (i == true) {
                            System.out.println("测量车单颗粒质谱仪数据接收成功!!!!");
                        } else {
                            System.out.println("测量车单颗粒质谱仪数据接收失败!!!!");
                        }
                    }
                    Matcher data_type6 = Pattern.compile("w01010-Rtd").matcher(data);
                    while (data_type6.find()) {
                        SurveyingVesselService surveyingVesselService = (SurveyingVesselService) ApplicationContextUtil.getBean("surveyingVesselService");
                        i = surveyingVesselService.insertData(data);
//                        data2txt("surveying_vessel_"+replace(time1),data);
//                        DataCenterUtils.sendMessage("surveying_vessel_"+ time1, "测量船数据","这是一条测量船推送的数据：");
                        if (i == true) {
                            System.out.println("测量船数据接收成功!!!!");
                        } else {
                            System.out.println("测量船数据接收失败!!!!");
                        }
                    }
                }
            }
            socket.shutdownInput();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void data2txt(String filename,String data) throws IOException {

        /* 写入Txt文件 */

        File writename = new File(exportDir + filename + ".txt");
        if (!writename.exists()) {
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write(data);
            out.flush();
            out.close();
        }
    }

    public String replace (String out){
        String out1 =out.replace("-","");
        String out2 = out1.replace("T","");
        String out3 = out2.replace("Z","");
        String out4 = out3.replace(":","");
        return  out4;
    }


}
