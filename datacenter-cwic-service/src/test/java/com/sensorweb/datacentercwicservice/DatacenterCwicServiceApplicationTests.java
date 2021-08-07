package com.sensorweb.datacentercwicservice;

import com.sensorweb.datacentercwicservice.entity.Catalog;
import com.sensorweb.datacentercwicservice.entity.DataSet;
import com.sensorweb.datacentercwicservice.service.InsertCWICService;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

@SpringBootTest
class DatacenterCwicServiceApplicationTests {

    @Autowired
    private InsertCWICService insertCWICService;
    @Test
    void contextLoads() throws Exception {
        String lowerCorner = "90.55 24.5";
        String upperCorner = "112.417 34.75";
        LocalDateTime dateTime = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00").withZone(ZoneId.of("Asia/Shanghai"));
//        String endTime = formatter.format(dateTime);
//        String beginTime = formatter.format(dateTime.minusHours(12));

        String endTime = "2020-12-16 12:00:00";
        String beginTime = "2020-12-16 00:00:00";

        String str = DataCenterUtils.readFromFile("D:\\OneDrive\\Download\\discovery.xml");
//        String doc = insertCWICService.getCapabilities();
        List<Catalog> catalogs = insertCWICService.getCatalog(str);
        if (catalogs!=null && catalogs.size()>0) {
            for (Catalog catalog:catalogs) {
                if (catalog.getId().equals("NASA") || catalog.getId().equals("CCMEO") || catalog.getId().equals("EUMETSAT")) {
                    continue;
                }
                List<DataSet> dataSets = catalog.getDatasets();
                if (catalog.getId().equals("USGSLSI")) {
                    if (dataSets!=null && dataSets.size()>0) {
                        for (DataSet dataSet:dataSets) {
                            System.out.println("Catalog: " + catalog.getId() + "-->" + dataSet.getId());
                            insertCWICService.insertData(catalog.getId(), dataSet.getId(), lowerCorner, upperCorner, beginTime, endTime, 1, 100);
//                        insertCWICService.insertData("NRSC", "C1405714988-ISRO", lowerCorner, upperCorner, beginTime, endTime, 1, 100);
                        }
                    }
                }

            }
        }
        System.out.println("time");
    }

    @Test
    public void  test() {
        String ss = "2020-12-16T00:00:00Z";
        Instant time = Instant.parse(ss);
        System.out.println(time);
    }

}
