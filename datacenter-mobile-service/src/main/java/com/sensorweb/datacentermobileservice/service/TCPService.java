package com.sensorweb.datacentermobileservice.service;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

//接收端的代码
public class TCPService implements Runnable {
    @Override
    public void run() {
        try {
            ServerSocket server = new ServerSocket(8081);
            // 调用accept监听，等待客户端连接
            System.out.println("********服务器即将启动，等待客户端连接********************");
            while (true) {
                Socket socket = server.accept();
                System.out.println("客户端ip："+new Date() +"  " +socket.getInetAddress());
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
                InetAddress inetAddress = socket.getInetAddress();
                System.out.println("****==========客户端地址" + inetAddress.getHostAddress()+"==================*****************");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

