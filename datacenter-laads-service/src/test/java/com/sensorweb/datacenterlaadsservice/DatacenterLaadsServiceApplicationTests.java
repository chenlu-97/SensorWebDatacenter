package com.sensorweb.datacenterlaadsservice;

import com.sensorweb.datacenterlaadsservice.dao.EntryMapper;
import com.sensorweb.datacenterlaadsservice.service.InsertLAADSService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DatacenterLaadsServiceApplicationTests {

    @Autowired
    private InsertLAADSService insertLAADSService;
    @Autowired
    private EntryMapper entryMapper;
    @Test
    void contextLoads() throws Exception {
        //测试
        String startTime = "2021-11-15 00:00:00";
        String endTime = "2021-11-18 00:00:00";
        String bbox = "90.55,24.5,112.417,34.75";
        String productName = "MOD11A1";
        String test = insertLAADSService.insertData2(startTime, endTime, bbox, productName);
        System.out.println(test);
        //测试
//          boolean test = insertLAADSService.insertData3("2021-09-01T00:00:00Z");
//          System.out.println(test);

        //测试
//        String time = entryMapper.selectNew().getStart().plusSeconds(24*60*60).toString();
//        System.out.println("time = " + time.substring(0, time.indexOf("T")).replace("-",""));


        //测试
//        List<Entry> entryList = new ArrayList<>();
//        Entry entry = new Entry();
//        entry.setSatellite("1");
//        entry.setEntryId("12");
//        entry.setBbox("12133");
//        entry.setSummary("1213");
//        entry.setWkt("POLYGON((119.88921295665621 32.43809123148852,120.7378487376959 32.272775667771676,121.52392173553908 32.11392926097289,121.52713013170285 32.1250148794685,121.59554547834652 32.36691622530554,121.6659985358775 32.61475609245087,121.73618880488671 32.86067740609684,121.80761210153415 33.10986251355583,121.88699656332598 33.38531657669484,121.94369387763581 33.58111802621447,122.01792679103042 33.83646201756913,122.01790658055839 33.838230418830506,121.5224570788418 33.93729939581788,120.67528383639105 34.10151678294821,120.32548043822243 34.1674169945179,120.007483607093 34.22636121664424,119.89051945454977 33.78834710068756,119.77884510492372 33.36646510952478,119.66808149921208 32.94452360765576,119.55290998356116 32.50211881560613,119.55432986776898 32.5015650616325,119.88921295665621 32.43809123148852))");
//        entryList.add(entry);
//        entryList.add(entry);
//        entryMapper.insertDataBatch(entryList);

//            String time = "2021-09-21T00:00:00Z";
//            String time1 = time.replace("T"," ").replace("Z","");
//            String pattern = "yyyy-MM-dd HH:mm:ss";
//            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
//            dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
//            LocalDateTime localDateTime = LocalDateTime.parse(time1, dateTimeFormatter);
//            System.out.println(localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant());
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
//        Calendar calendar = Calendar.getInstance();
//        calendar.add(Calendar.DATE, -1);
//        String stop = format.format(calendar.getTime());
//        calendar.add(Calendar.DATE, -1);
//        String start = format.format(calendar.getTime()).replace("00:00:00", "23:59:59");
//        System.out.println("stop = " + stop);
//        System.out.println("start = " + start);


    }
}
