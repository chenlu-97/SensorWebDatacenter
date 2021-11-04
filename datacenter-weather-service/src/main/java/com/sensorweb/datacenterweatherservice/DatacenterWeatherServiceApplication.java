package com.sensorweb.datacenterweatherservice;

import com.sensorweb.datacenterweatherservice.service.DD;
import com.sensorweb.datacenterweatherservice.service.TT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableAsync
public class DatacenterWeatherServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(DatacenterWeatherServiceApplication.class, args);
        new Thread(new DD()).start();
//        new Thread(new TT()).start();
    }

}
