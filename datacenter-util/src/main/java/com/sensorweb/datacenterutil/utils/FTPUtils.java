package com.sensorweb.datacenterutil.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
public class FTPUtils {
    /**
     * 获取FTPClient对象
     * @param ftpHost
     * @param userName
     * @param password
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String userName, String password) {
        FTPClient ftpClient = null;

        try {
            ftpClient = new FTPClient();
//            ftpClient.setDefaultPort(2121);
            InetAddress inetAddress = InetAddress.getByName(ftpHost);
            ftpClient.connect(inetAddress);
            ftpClient.login(userName, password);
            ftpClient.setConnectTimeout(50000);
            ftpClient.setControlEncoding("UTF-8");

            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.info("未连接到FTP,用户名或密码错误");
                ftpClient.disconnect();
            } else {
                log.info("FTP连接成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }

    /**
     * 关闭FTP方法
     * @param ftpClient
     * @return
     */
    public static boolean closeFTP(FTPClient ftpClient) {
        try {
            ftpClient.logout();
        } catch (IOException e) {
            log.error("FTP关闭失败");
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    log.error("FTP关闭失败");
                }
            }
        }
        return false;
    }

    /**
     * 下载FTP下指定文件
     * @param ftpClient
     * @param filePath
     * @param fileName
     * @param downPath
     * @return
     */
    public static boolean downloadFTP(FTPClient ftpClient, String filePath, String fileName, String downPath) {
        boolean flag = false;
        try {
            //common-net的ftpclient默认是使用ASCII_FILE_TYPE，文件会经过ASCII编码转换，所以可能会造成文件损坏。所以我们需要手动指定其文件类型为二进制文件，屏蔽ASCII转换的操作，避免文件在转换的过程中受损。
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //跳转到文件目录
            ftpClient.changeWorkingDirectory(filePath);
            //获取目录下文件集合
            ftpClient.enterLocalPassiveMode();
            FTPFile[] files = ftpClient.listFiles();
            for (FTPFile file : files) {
                //取得指定文件并下载
                if (file.getName().equals(fileName)) {
//                    File downFile = new File(downPath + File.separator + file.getName());
                    File downFile = new File(downPath);
                    OutputStream out = new FileOutputStream(downFile);
                    //绑定输出流下载文件,需要设置编码集,不然可能出现文件为空的情况
                    flag = ftpClient.retrieveFile(new String(file.getName().getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1), new FileOutputStream(downFile));
                    out.flush();
                    out.close();
                    if (flag) {
                        log.info("下载成功");
                    } else {
                        log.error("下载失败");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        closeFTP(ftpClient);
        return flag;
    }

    /**
     * FTP文件上传工具类
     * @param ftp
     * @param filePath
     * @param ftpPath
     * @return
     */
    public static boolean uploadFile(FTPClient ftp, String filePath,String ftpPath){
        if (!ftp.isConnected()) {
            log.info("连接未打开");
            return false;
        }

        boolean flag = false;
        InputStream in = null;
        try {
            // 设置PassiveMode传输
            ftp.enterLocalPassiveMode();
            //设置二进制传输，使用BINARY_FILE_TYPE，ASC容易造成文件损坏
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            //判断FPT目标文件夹时候存在不存在则创建
            if(!ftp.changeWorkingDirectory(ftpPath)){
                ftp.makeDirectory(ftpPath);
            }
            //跳转目标目录
            ftp.changeWorkingDirectory(ftpPath);

            //上传文件
            File file = new File(filePath);
            in = new FileInputStream(file);
//            String tempName = ftpPath + File.separator + file.getName();
            flag = ftp.storeFile(new String (file.getName().getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1),in);
            if(flag){
                log.info("上传成功");
            }else{
                log.error("上传失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传失败");
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * FPT上文件的复制
     * @param ftp  FTPClient对象
     * @param olePath 原文件地址
     * @param newPath 新保存地址
     * @param fileName 文件名
     * @return
     */
    public static boolean copyFile(FTPClient ftp, String olePath, String newPath,String fileName) {
        boolean flag = false;

        try {
            // 跳转到文件目录
            ftp.changeWorkingDirectory(olePath);
            //设置连接模式，不设置会获取为空
            ftp.enterLocalPassiveMode();
            // 获取目录下文件集合
            FTPFile[] files = ftp.listFiles();
            ByteArrayInputStream  in = null;
            ByteArrayOutputStream out = null;
            for (FTPFile file : files) {
                // 取得指定文件并下载
                if (file.getName().equals(fileName)) {

                    //读取文件，使用下载文件的方法把文件写入内存,绑定到out流上
                    out = new ByteArrayOutputStream();
                    ftp.retrieveFile(new String(file.getName().getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1), out);
                    in = new ByteArrayInputStream(out.toByteArray());
                    //创建新目录
                    ftp.makeDirectory(newPath);
                    //文件复制，先读，再写
                    //二进制
                    ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
                    flag = ftp.storeFile(newPath+File.separator+(new String(file.getName().getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1)),in);
                    out.flush();
                    out.close();
                    in.close();
                    if(flag){
                        log.info("转存成功");
                    }else{
                        log.error("复制失败");
                    }


                }
            }
        } catch (Exception e) {
            log.error("复制失败");
        }
        return flag;
    }

    /**
     * 实现文件的移动，这里做的是一个文件夹下的所有内容移动到新的文件，
     * 如果要做指定文件移动，加个判断判断文件名
     * 如果不需要移动，只是需要文件重命名，可以使用ftp.rename(oleName,newName)
     * @param ftp
     * @param oldPath
     * @param newPath
     * @return
     */
    public static boolean moveFile(FTPClient ftp, String oldPath, String newPath){
        boolean flag = false;

        try {
            ftp.changeWorkingDirectory(oldPath);
            ftp.enterLocalPassiveMode();
            //获取文件数组
            FTPFile[] files = ftp.listFiles();
            //新文件夹不存在则创建
            if(!ftp.changeWorkingDirectory(newPath)){
                ftp.makeDirectory(newPath);
            }
            //回到原有工作目录
            ftp.changeWorkingDirectory(oldPath);
            for (FTPFile file : files) {

                //转存目录
                flag = ftp.rename(new String(file.getName().getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1), newPath+File.separator+new String(file.getName().getBytes("UTF-8"),"ISO-8859-1"));
                if(flag){
                    log.info(file.getName()+"移动成功");
                }else{
                    log.error(file.getName()+"移动失败");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("移动文件失败");
        }
        return flag;
    }

    /**
     * 删除FTP上指定文件夹下文件及其子文件方法，添加了对中文目录的支持
     * @param ftp FTPClient对象
     * @param FtpFolder 需要删除的文件夹
     * @return
     */
    public static boolean deleteByFolder(FTPClient ftp,String FtpFolder){
        boolean flag = false;
        try {
            ftp.changeWorkingDirectory(new String(FtpFolder.getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1));
            ftp.enterLocalPassiveMode();
            FTPFile[] files = ftp.listFiles();
            for (FTPFile file : files) {
                //判断为文件则删除
                if(file.isFile()){
                    ftp.deleteFile(new String(file.getName().getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1));
                }
                //判断是文件夹
                if(file.isDirectory()){
                    String childPath = FtpFolder + File.separator+file.getName();
                    //递归删除子文件夹
                    deleteByFolder(ftp,childPath);
                }
            }
            //循环完成后删除文件夹
            flag = ftp.removeDirectory(new String(FtpFolder.getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1));
            if(flag){
                log.info(FtpFolder+"文件夹删除成功");
            }else{
                log.error(FtpFolder+"文件夹删除成功");
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error("删除失败");
        }
        return flag;

    }

    /**
     * 遍历解析文件夹下所有文件
     * @param folderPath 需要解析的的文件夹
     * @param ftp FTPClient对象
     * @return
     */
    public static boolean readFileByFolder(FTPClient ftp,String folderPath){
        boolean flage = false;
        try {
            ftp.changeWorkingDirectory(new String(folderPath.getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1));
            //设置FTP连接模式
            ftp.enterLocalPassiveMode();
            //获取指定目录下文件文件对象集合
            FTPFile files[] = ftp.listFiles();
            InputStream in = null;
            BufferedReader reader = null;
            for (FTPFile file : files) {
                //判断为txt文件则解析
                if(file.isFile()){
                    String fileName = file.getName();
                    if(fileName.endsWith(".txt")){
                        in = ftp.retrieveFileStream(new String(file.getName().getBytes(StandardCharsets.UTF_8),StandardCharsets.ISO_8859_1));
                        reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                        String temp;
                        StringBuffer buffer = new StringBuffer();
                        while((temp = reader.readLine())!=null){
                            buffer.append(temp);
                        }
                        if(reader!=null){
                            reader.close();
                        }
                        if(in!=null){
                            in.close();
                        }
                        //ftp.retrieveFileStream使用了流，需要释放一下，不然会返回空指针
                        ftp.completePendingCommand();
                        //这里就把一个txt文件完整解析成了个字符串，就可以调用实际需要操作的方法
                        System.out.println(buffer.toString());
                    }
                }
                //判断为文件夹，递归
                if(file.isDirectory()){
                    String path = folderPath+File.separator+file.getName();
                    readFileByFolder(ftp, path);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            log.error("文件解析失败");
        }

        return flage;

    }
}
