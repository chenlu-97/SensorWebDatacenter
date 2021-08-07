package com.sensorweb.datacenterhikvisionservice;

import com.sensorweb.datacenterhikvisionservice.util.HCNetSDKPath;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DatacenterHikvisionServiceApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(HCNetSDKPath.DLL_PATH);
    }

}
