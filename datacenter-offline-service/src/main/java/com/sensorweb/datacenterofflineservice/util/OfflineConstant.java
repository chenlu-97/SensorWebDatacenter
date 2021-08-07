package com.sensorweb.datacenterofflineservice.util;


import org.springframework.stereotype.Component;

@Component
public interface OfflineConstant {


    String GF1_PATH = "/data/Ai-Sensing/Data-tmp/GF1/3、RPC-Correction/";

    String GF2_PATH = "/data/Ai-Sensing/Data-tmp/GF2_new/3、RPC-Correction/";

    String GF6_PATH = "/data/Ai-Sensing/Data-tmp/GF6/3、RPC-Correction/";

    String GF7_PATH = "/data/Ai-Sensing/Data-tmp/GF7/3、RPC-Correction/";

    String[] GF_PATH = {GF1_PATH,GF2_PATH,GF6_PATH,GF7_PATH};


    String WF_PATH = "/data/Ai-Sensing/DataCenter/weatherForecastData_whu1km";

    String BAIDU_ADDRESS_API = "http://api.map.baidu.com/geocoding/v3/";

    String BAIDU_AK = "DUljkTDcLCQ00d6ZofaN8FgTnRf4YWAu";


}
