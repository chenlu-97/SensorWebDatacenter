package com.sensorweb.datacentermobileservice.service;

import org.springframework.scheduling.annotation.Async;

import java.io.*;
import java.net.Socket;

//发送端的代码如下：
public class TCPClient implements Runnable {
    @Override
    public void run() {
        try {
            Socket s = new Socket("114.55.99.71", 8080);
            //创建一个socket绑定的端口和地址为：9977，本机。
            OutputStream oos = s.getOutputStream();
            //获取到输出流
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(oos));
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String line = null;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.flush();
                //将内容写到控制台
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

}
