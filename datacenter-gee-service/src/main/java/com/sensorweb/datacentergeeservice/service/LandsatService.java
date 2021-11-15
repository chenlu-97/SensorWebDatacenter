package com.sensorweb.datacentergeeservice.service;

import com.sensorweb.datacentergeeservice.dao.LandsatMapper;
import com.sensorweb.datacentergeeservice.entity.Landsat;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Service
public class LandsatService {
    @Autowired
    LandsatMapper landsatMapper;

//    public GoogleCredentials getCredentials() throws IOException {
//        HttpTransportFactory httpTransportFactory = getHttpTransportFactory("127.0.0.1", 10808, "", "");
//        return GoogleCredentials.getApplicationDefault(httpTransportFactory);
//    }
//    public HttpTransportFactory getHttpTransportFactory(String proxyHost, int proxyPort, String proxyName, String proxyPassword) {
//        HttpHost proxyHostDetails = new HttpHost(proxyHost, proxyPort);
//        HttpRoutePlanner httpRoutePlanner = new DefaultProxyRoutePlanner(proxyHostDetails);
//        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
//        credentialsProvider.setCredentials(
//                new AuthScope(proxyHostDetails.getHostName(), proxyHostDetails.getPort()),
//                new UsernamePasswordCredentials(proxyName, proxyPassword)
//        );
//        HttpClient httpClient = ApacheHttpTransport.newDefaultHttpClientBuilder()
//                .setRoutePlanner(httpRoutePlanner)
//                .setProxyAuthenticationStrategy(ProxyAuthenticationStrategy.INSTANCE)
//                .setDefaultCredentialsProvider(credentialsProvider)
//                .build();
//        final HttpTransport httpTransport = new ApacheHttpTransport(httpClient);
//        return new HttpTransportFactory() {
//            @Override
//            public HttpTransport create() {
//                return httpTransport;
//            }
//        };
//    }

    public List<Landsat> getAll() {
        return landsatMapper.selectAll();
    }

    public List<Landsat> getByattribute(String spacecraftID, String Date, String Cloudcover, String imageType) {
        return landsatMapper.selectByattribute(spacecraftID,Date,Cloudcover,imageType);
    }

    public List<Landsat> getLandsatByPage(int pageNum, int pageSize) {
        return landsatMapper.selectByPage(pageNum,pageSize);
    }

    public int getLandsatNum() {
        return landsatMapper.selectNum();
    }

    @Scheduled(cron = "0 00 11 * * ?") //每天上午11点接入
    public void getLandsat() {
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Shanghai"));
        String time = formatter.format(dateTime);
        LocalDateTime begin_date = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        LocalDateTime end_date = begin_date.plusSeconds(24*60*60);
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(ZoneId.of("Asia/Shanghai"));
        String begin = formatter1.format(begin_date);
        String end = formatter1.format(end_date);
        new Thread(new Runnable() {
            @Override
            public void run() {
                    try {
//                        MODIS/006/MCD12Q1
//                        MODIS/006/MOD13A2
//                        MODIS/006/MOD11A2
//                        MODIS/006/MOD11A1
//                        COPERNICUS/S2_SR
                        String Landsat8 = "LANDSAT/LC08/C01/T1";
                        String url = "http://172.16.100.2:8009/getLandsat/";
                        String param = "image="+Landsat8+"&startDate="+begin+"&endDate="+end;
                        String statue = DataCenterUtils.doGet(url,param);
                        if (statue=="0") {
                            log.info("------ 目前暂无影像！！！！！-----");
                            System.out.println("------ 目前暂无影像！！！！！-----");
                        } else if(statue=="fail"){
                            log.info("------ 连接GEE失败-----");
                            System.out.println("------ 连接GEE失败-----");
                        }else if(statue=="success"){
                            log.info("------获取影像成功！！！！！！！-----");
                            System.out.println("------ 获取影像成功！！！！！！！-----");
                            DataCenterUtils.sendMessage("Landsat-8"+ time, "Landsat-8","GEE获取的Landsat-8影像成功");
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage());
                        log.info("GEE获取Landsat-8影像 " + time + "Status: Fail");
                        System.out.println(e.getMessage());
                    }
            }
        }).start();
    }



}
