package com.sensorweb.sosobsservice.controller;

import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.sosobsservice.dao.ObservationMapper;
import com.sensorweb.sosobsservice.entity.Observation;
import com.sensorweb.sosobsservice.feign.*;
import com.sensorweb.sosobsservice.service.GetObservationExpandService;
import com.sensorweb.sosobsservice.util.ObsConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

@Api("观测数据查询相关API")
@CrossOrigin
@RestController
@Slf4j
public class GetObsController implements ObsConstant {
    @Autowired
    private GetObservationExpandService getObservationExpandService;

    @Autowired
    private ObservationMapper observationMapper;

    @Autowired
    private AirFeignClient airFeignClient;
    @Autowired
    private WeatherFeignClient weatherFeignClient;
    @Autowired
    private HimawariFeignClient himawariFeignClient;
    @Autowired
    private GeeFeignClient geeFeignClient;
    @Autowired
    private LaadsFeignClient laadsFeignClient;
    @Autowired
    private OfflineFeignClient offlineFeignClient;
    @Autowired
    private LittleSensorFeignClient littleSensorFeignClient;

    @Autowired
    private DiscoveryClient discoveryClient;


    @RequestMapping("getServicesList")
    @ResponseBody
    public Object getServicesList() {
        List<List<ServiceInstance>> servicesList = new ArrayList<>();
        //获取服务名称
        List<String> serviceNames = discoveryClient.getServices();
        for (String serviceName : serviceNames) {
            //获取服务中的实例列表
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(serviceName);
            servicesList.add(serviceInstances);
        }
        return servicesList;
    }


    @ApiOperation("查询24小时的数据接入数量")
    @GetMapping(path = "getCountOfHour")
    @ResponseBody
    public List<Map<String,Object>> getCountOfHour() {

        List<Map<String,Object>> res = new ArrayList<>();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//        System.out.println(df.format(new Date()));
        String end = df.format(new Date());// new Date()为获取当前系统时间
        String begin = end;
        Instant start = DataCenterUtils.string2Instant(begin).minusSeconds(24*60*60);//减24小时
        Instant stop = DataCenterUtils.string2Instant(end);
        System.out.println("start = " + start);
        while(start.isBefore(stop)){
            int count1 =airFeignClient.getAirCountOfHour(start);
            int count2 =weatherFeignClient.getWeatherCountOfHour(start);
            int count = count1+count2;
            Map<String,Object> tmp = new HashMap<>();
            String time = start.plusSeconds(9*60*60).toString();
            tmp.put("count",count);
            tmp.put("time",time.substring(time.indexOf("T")+1,time.indexOf("Z")).substring(0,2)+"时");
            res.add(tmp);
            start = start.plusSeconds(60*60);
        }
        return res;
    }

    @ApiOperation("查询所有的观测数据量")
    @GetMapping(path = "getObservationNum")
    public int getObservationNum() {
        int HBHJStation = airFeignClient.getHBAirNum() +offlineFeignClient.getWaterQualityNum()+ offlineFeignClient.getWaterPollutionNum();
        int CHAirStation = airFeignClient.getCHAirNum();
        int TWAirStation = airFeignClient.getTWAirNum();
        int CHWeatherStation = weatherFeignClient.getWeatherNum();
        int AirStation = airFeignClient.getCHAirNum()+ airFeignClient.getHBAirNum()+ airFeignClient.getTWAirNum();
        int WeatherStation = weatherFeignClient.getWeatherNum();
        int WaterStation = offlineFeignClient.getWaterQualityNum()+ offlineFeignClient.getWaterPollutionNum();
//        int littleSensor = littleSensorFeignClient.getLittleSensorNumber();
        int num = HBHJStation+CHAirStation+TWAirStation+CHWeatherStation+AirStation+WeatherStation+WaterStation;

        return num;
    }



    @ApiOperation("查询各平台的接入数据总量")
    @GetMapping(path = "getGroundStationDataNum")
    public List<Integer> getDataNumByPlatform() {
        List<Integer> res = new ArrayList<>();
        int HBHJStation = airFeignClient.getHBAirNum() +offlineFeignClient.getWaterQualityNum()+ offlineFeignClient.getWaterPollutionNum();
        int CHAirStation = airFeignClient.getCHAirNum();
        int TWAirStation = airFeignClient.getTWAirNum();
        int CHWeatherStation = weatherFeignClient.getWeatherNum();
        res.add(HBHJStation);
        res.add(CHAirStation);
        res.add(TWAirStation);
        res.add(CHWeatherStation);
        return res;
    }

    @ApiOperation("查询各个数据类型的的接入数据总量")
    @GetMapping(path = "getTypeDataNum")
    public List<Integer> getDataNumByDataType() {
        List<Integer> res = new ArrayList<>();
        int AirStation = airFeignClient.getCHAirNum()+ airFeignClient.getHBAirNum()+ airFeignClient.getTWAirNum();
        int WeatherStation = weatherFeignClient.getWeatherNum();
        int WaterStation = offlineFeignClient.getWaterQualityNum()+ offlineFeignClient.getWaterPollutionNum();
        res.add(AirStation);
        res.add(WeatherStation);
        res.add(WaterStation);
        return res;
    }


    @ApiOperation("查询各个遥感数据的接入数据总量")
    @GetMapping(path = "getSatelliteDataNum")
    public List<Integer> getDataNumBySatellite() {
        List<Integer> res = new ArrayList<>();
        int LandsatCount = geeFeignClient.getLandsatNum();
        int ModisCount = laadsFeignClient.getModisNum();
        int HimawariCount = himawariFeignClient.getHimawariNum();
        int GFCount = offlineFeignClient.getGFNum();
        res.add(LandsatCount);
        res.add(ModisCount);
        res.add(HimawariCount);
        res.add(GFCount);
        return res;
    }



    @ApiOperation("查询近一年各月的观测数据接入数量")
    @GetMapping(path = "getObservationCountOfMonth")
    public List<Map<String, Integer>> getObservationCountOfMonth() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai")));
        List<Map<String, Integer>> res = new ArrayList<>();
        calendar.add(Calendar.MONTH, -12);
        for (int i=0; i<12; i++) {
            int month = calendar.get(Calendar.MONTH);
            String begin = DataCenterUtils.getFirstDay(calendar);
            String end = DataCenterUtils.getLastDay(calendar);

            Instant start = DataCenterUtils.string2Instant(begin);
            Instant stop = DataCenterUtils.string2Instant(end);
            int count = getObservationExpandService.getObservationByTemporal(start, stop).size();

            Map<String, Integer> temp = new HashMap<>();
            temp.put(month+"", count);
            res.add(temp);
            calendar.add(Calendar.MONTH, 1);
        }
        return res;
    }


    @ApiOperation("查询近30天的观测数据接入数量")
    @GetMapping(path = "getObservationCountOfDay")
    @ResponseBody
    public List<Integer> getObservationCountOfWeek() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai")));
        List<Integer> res = new ArrayList<>();
        calendar.add(Calendar.DAY_OF_MONTH, -30);
        for (int i=0; i<30; i++) {
//            int week = calendar.get(Calendar.DATE);
            String begin = DataCenterUtils.getFirstDay(calendar);
            String end = DataCenterUtils.getLastDay(calendar);

            Instant start = DataCenterUtils.string2Instant(begin);
            Instant stop = DataCenterUtils.string2Instant(end);
            int count = getObservationExpandService.getObservationByTemporal(start, stop).size();
            Integer temp = count;
            res.add(temp);
            calendar.add(Calendar.DAY_OF_MONTH, 1);

        }
        return res;
    }


    @ApiOperation("查询每小时的观测数据接入数量")
    @GetMapping(path = "getObservationCountOfHour")
    @ResponseBody
    public List<Integer> getObservationCountOfHour() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(ZoneId.of("Asia/Shanghai")));
        List<Integer> res = new ArrayList<>();
        calendar.add(Calendar.HOUR_OF_DAY, -24);
        for (int i=0; i<24; i++) {

            String begin = DataCenterUtils.getFirstDay(calendar);
            String end = DataCenterUtils.getLastDay(calendar);

            Instant start = DataCenterUtils.string2Instant(begin);
            Instant stop = DataCenterUtils.string2Instant(end);
            int count = getObservationExpandService.getObservationByTemporal(start, stop).size();
            Integer temp = count;
            res.add(temp);
            calendar.add(Calendar.HOUR_OF_DAY, 1);
        }
        return res;
    }



//    @ApiOperation("查询所有的观测数据量")
//    @GetMapping(path = "getObservationNum")
//    public int getObservationNum() {
//        return getObservationExpandService.getAllObservationNum();
//    }


    @ApiOperation("查询所有数据文件的大小")
    @GetMapping(path = "getFileSize")
    @ResponseBody
    public String getFileSize() {
        long tmp = getObservationExpandService.getFileSize(new File(ObsConstant.File_Path));
        DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
        String res = df.format((float)tmp/1024/1024/1024/1024)+"TB";
        System.out.println(res);
        return res;
    }


    @ApiOperation("分页查询观测数据")
    @GetMapping(path = "getObservationByPage")
    public List<Observation> getObservationByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                  @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        return getObservationExpandService.getObservationByPage(pageNum, pageSize);
    }




    @GetMapping(path = "getObservationByConditions")
    public List<Observation> getObservationByConditions(@RequestParam("dataType") String dataType, @RequestParam("ranSpa") String ranSpa,
                                                        @RequestParam("timeBegin") Instant timeBegin, @RequestParam("timeEnd") Instant timeEnd) {

        if(dataType.equals("Himawari")){
            return observationMapper.selectByTemAndType(timeBegin, timeEnd, dataType);
        }
        else {

            if (ranSpa.equals("武汉城市圈")) {
                List<Integer> cityIds = observationMapper.selectCityIdInWuCityCircle();
                if (cityIds != null && cityIds.size() > 0) {
                    return observationMapper.selectByTemAndSpaAndType(timeBegin, timeEnd, dataType, ranSpa, cityIds);
                }
            } else if (ranSpa.equals("全国")) {
                List<Integer> cityIds = observationMapper.selectCityIdInChina();
                if (cityIds != null && cityIds.size() > 0) {
                    return observationMapper.selectByTemAndSpaAndType(timeBegin, timeEnd, dataType, ranSpa, cityIds);
                }
            } else if (ranSpa.equals("长江经济带")) {
                List<Integer> cityIds = observationMapper.selectCityIdInChangjiang();
                if (cityIds != null && cityIds.size() > 0) {
                    return observationMapper.selectByTemAndSpaAndType(timeBegin, timeEnd, dataType, ranSpa, cityIds);
                }
            }
        }
        return null;

    }


    @ApiOperation("修改后的空间查询")
    @GetMapping(path = "getObservationByNewConditions")
    public List<Observation> getObservationByNewConditions(@RequestParam("dataType") String dataType, @RequestParam("ranSpa") String ranSpa,
                                                        @RequestParam("timeBegin") Instant timeBegin, @RequestParam("timeEnd") Instant timeEnd) {

        if(dataType.equals("Himawari")){
            return observationMapper.selectByTemAndType(timeBegin, timeEnd, dataType);
        }
        else {
            if (ranSpa.equals("武汉城市圈")) {
                    return observationMapper.selectByTemAndSpaAndTypeNew(timeBegin, timeEnd, dataType, ranSpa);
            } else if (ranSpa.equals("全国")) {
                    return observationMapper.selectByTemAndSpaAndTypeNew(timeBegin, timeEnd, dataType, ranSpa);
            } else if (ranSpa.equals("长江经济带")) {
                    return observationMapper.selectByTemAndSpaAndTypeNew(timeBegin, timeEnd, dataType, ranSpa);
            }
        }
        return null;

    }


    @ApiOperation("修改后的空间查询")
    @GetMapping(path = "getObservationByTimeAndSpa")
    public List<Observation> getObservationByTimeAndSpa(@RequestParam("ranSpa") String ranSpa,@RequestParam("timeBegin") Instant timeBegin, @RequestParam("timeEnd") Instant timeEnd) {
        List<Observation> observation = new ArrayList<>();
        observation = observationMapper.selectByTimeAndSpa(timeBegin, timeEnd, ranSpa);
        if(observation !=null && observation.size()>0){
            return observation;
        }else {
            return null;
        }
    }

}
