package com.sensorweb.eurekaserver03;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServer03Application {

	public static void main(String[] args) {
		SpringApplication.run(EurekaServer03Application.class, args);
	}

}
