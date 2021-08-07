package com.sensorweb.sosobsservice;

import com.sensorweb.sosobsservice.dao.ObservationMapper;
import com.sensorweb.sosobsservice.service.InsertObservationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SosObsServiceApplicationTests {
	@Autowired
	private ObservationMapper observationMapper;
	@Autowired
	private InsertObservationService insertObservationService;

	@Autowired
	private DiscoveryClient discoveryClient;
	@Test
	void contextLoads() {
//		String bbox = "POINT (1 2)";
//		List<Integer> cityIds = observationMapper.selectCityIdInWuCityCircle();
//		boolean isWH = insertObservationService.insertBeforeSelectWHSpa(bbox);
////		boolean isCJ = obsFeignClient.insertBeforeSelectCJSpa(bbox);
////		System.out.println(isCJ);
//		System.out.println(isWH);

//			List<List<ServiceInstance>> servicesList = new ArrayList<>();
//			//获取服务名称
//			List<String> serviceNames = discoveryClient.getServices();
//			for (String serviceName : serviceNames) {
//				//获取服务中的实例列表
//				List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
//				servicesList.add(serviceInstances);
//			}
//
//			System.out.println(servicesList);



	}

}
