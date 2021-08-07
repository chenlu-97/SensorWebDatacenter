package com.sensorweb.sossensorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class SosSensorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SosSensorServiceApplication.class, args);
	}

}
