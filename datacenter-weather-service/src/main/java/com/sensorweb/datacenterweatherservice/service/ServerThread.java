package com.sensorweb.datacenterweatherservice.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * 服务端线程类
 *
 *
 */
public class ServerThread extends Thread {

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
                MeasuringVehicleService measuringVehicleService = (MeasuringVehicleService) ApplicationContextUtil.getBean("measuringVehicleService");
                boolean i = measuringVehicleService.insertData(new String(buf, 0, line));
                if (i == true) {
                    System.out.println("成功!!!!");
                } else {
                    System.out.println("失败!!!!");
                }
                //将接收到的数据在控制台输出
            }
            socket.shutdownInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
