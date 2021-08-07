package com.sensorweb.datacentergeeservice.service;

import com.sensorweb.datacentergeeservice.dao.LandsatMapper;
import com.sensorweb.datacentergeeservice.entity.Landsat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
