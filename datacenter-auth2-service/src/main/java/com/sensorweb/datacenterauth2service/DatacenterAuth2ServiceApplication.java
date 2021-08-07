package com.sensorweb.datacenterauth2service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableAuthorizationServer
@EnableDiscoveryClient
public class DatacenterAuth2ServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatacenterAuth2ServiceApplication.class, args);
    }

}
