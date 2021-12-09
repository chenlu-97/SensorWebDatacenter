package com.sensorweb.datacenterairservice.controller;
import com.sensorweb.datacenterairservice.dao.AirQualityHourMapper;
import com.sensorweb.datacenterairservice.dao.ChinaAirQualityHourMapper;
import com.sensorweb.datacenterairservice.dao.TWEPAMapper;
import com.sensorweb.datacenterairservice.entity.AirQualityHour;
import com.sensorweb.datacenterairservice.entity.ChinaAirQualityHour;
import com.sensorweb.datacenterairservice.entity.TWEPA;
import com.sensorweb.datacenterairservice.service.GetAirService;
import com.sensorweb.datacenterairservice.service.InsertTWEPA;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin
public class GetAirQualityHour {

    @Autowired
    GetAirService getAirService;

    @Autowired
    private AirQualityHourMapper airQualityHourMapper;

    @Autowired
    private TWEPAMapper twepaMapper;

    @Autowired
    private ChinaAirQualityHourMapper chinaAirQualityHourMapper;

    @Autowired
    private InsertTWEPA insertTWEPA;



    @ApiOperation("查询24小时的Air数据接入数量")
    @GetMapping(path = "getAirCountOfHour")
    @ResponseBody
    public Integer getAirCountOfHour(@RequestParam(value = "start") Instant start) {

        Long time1  = Long.valueOf(start.plusSeconds(8*60*60).toString().replace("-","").replace("T","").replace(":","").replace("Z",""));
        Long time2  = Long.valueOf(start.plusSeconds(9*60*60).toString().replace("-","").replace("T","").replace(":","").replace("Z",""));
        int count1 =airQualityHourMapper.selectByTime(start, start.plusSeconds(60*60));
            int count2 =twepaMapper.selectByTime(start, start.plusSeconds(60*60));
            int count3 = chinaAirQualityHourMapper.selectByTime(time1, time2);
            int count = count1+count2+count3;

        return count;
    }


    @GetMapping(value = "getAllAirQualityHourly")
    @ResponseBody
    public Map<String, List<AirQualityHour>> getAllInfo() {
        Map<String, List<AirQualityHour>> res = new HashMap<>();
        List<AirQualityHour> info =  getAirService.getAll();
        res.put("Info", info);
        return res;
    }

//这个为模板的分页查询，改动有两个地方： 一个是PostMapping   另一个是返回数据有num格式
    @ApiOperation("分页查询HB_Air数据")
    @GetMapping(path = "getHBAirQualityHourlyByPage")
    public Map<String, Object> getHBAirQualityHourByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                  @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<AirQualityHour> info =  getAirService.getAirQualityHourlyByPage(pageNum, pageSize);
        int count = getAirService.getHBAirQualityHourNum();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;

    }


    @ApiOperation("根据id的分页查询HB_Air数据")
    @GetMapping(path = "getHBAirQualityHourlyByID")
    @ResponseBody
    public Map<String, List<AirQualityHour>> getHBAirQualityHourByPageID(@RequestParam(value = "uniquecode") List<String>  uniquecode, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
        Map<String, List<AirQualityHour>> res = new HashMap<>();
        List<AirQualityHour> info =  getAirService.getAirQualityHourlyByID(uniquecode,pageNum,pageSize);
        res.put("Info", info);
        return res;
    }



    @ApiOperation("分页查询CH_Air数据")
    @GetMapping(path = "getCHAirQualityHourlyByPage")
    public Map<String, Object> getCHAirQualityHourByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                                     @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<ChinaAirQualityHour> info =  chinaAirQualityHourMapper.selectByPage(pageNum, pageSize);
        int count = getAirService.getCHAirQualityHourNum();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;

    }


    @ApiOperation("根据id的分页查询CH_Air数据")
    @GetMapping(path = "getCHAirQualityHourlyByID")
    @ResponseBody
    public Map<String, List<ChinaAirQualityHour>> getCHAirQualityHourByPageID(@RequestParam(value = "uniquecode") List<String> uniquecode, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
        Map<String, List<ChinaAirQualityHour>> res = new HashMap<>();
        List<ChinaAirQualityHour> info =  chinaAirQualityHourMapper.selectByIds(uniquecode,pageNum,pageSize);
        res.put("Info", info);
        return res;
    }


    @ApiOperation("分页查询TW_Air数据")
    @GetMapping(path = "getTWAirQualityHourlyByPage")
    public Map<String, List<TWEPA>> getTWAirQualityHourByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                                            @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, List<TWEPA>> res = new HashMap<>();
        List<TWEPA> info =  twepaMapper.selectByPage(pageNum, pageSize);
        res.put("Info", info);
        return res;
    }

    @ApiOperation("根据id的分页查询TW_Air数据")
    @GetMapping(path = "getTWAirQualityHourlyByID")
    @ResponseBody
    public Map<String, List<TWEPA>> getTWAirQualityHourByPageID(@RequestParam(value = "uniquecode") List<String>  uniquecode, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
        Map<String, List<TWEPA>> res = new HashMap<>();
        List<TWEPA> info =  twepaMapper.selectByIds(uniquecode,pageNum,pageSize);
        res.put("Info", info);
        return res;
    }



    @ApiOperation("id的查询数量number")
    @GetMapping(path = "getAirQualityHourlyNumberByID")
    public int getAirQualityHourlyNumberByID(@RequestParam(value = "uniquecode") List<String> uniquecode) {
        return getAirService.getAirQualityHourlyNumberByID(uniquecode);
    }


    @ApiOperation("查询气象数据数据总量")
    @GetMapping(path = "getHBAirNumber")
    public int getHBAirNum() {
        int count = getAirService.getHBAirQualityHourNum();
        return count;
    }
    @ApiOperation("查询气象数据数据总量")
    @GetMapping(path = "getCHAirNumber")
    public int getCHAirNum() {
        int count = getAirService.getCHAirQualityHourNum();
        return count;
    }
    @ApiOperation("查询气象数据数据总量")
    @GetMapping(path = "getTWAirNumber")
    public int getTWAirNum() {
        int count = getAirService.getTWAirQualityHourNum();
        return count;
    }


    @GetMapping(path = "getExportAll_HBAirDataByIds")
    public Map<String, String> getExportAll_HBAirDataByIds(@RequestParam(value = "ids") List<String> ids, @RequestParam(value = "time") Instant time,@RequestParam(value = "geotype") String geotype)  {
        Map<String, String> res = new HashMap<>();
        List<AirQualityHour> airQualityHours = new ArrayList<>();
        String filename = null;
        if (ids!=null && ids.size()>0) {
            for (String id:ids) {
                AirQualityHour airQualityHour = airQualityHourMapper.selectByIdAndTime(id,time);
                String out_time = null;
                if(airQualityHour !=null) {
                    airQualityHours.add(airQualityHour);
                    String tmp = time.plusSeconds(8*60*60).toString();
                    filename = replace(tmp);
                }
            }
        }
        filename = "AIR"+ "_AllData" +"_"+geotype+"_" +filename;
        String filePath = getAirService.exportTXT_WH_AllData(airQualityHours,filename);
        res.put("filePath", filePath);
        return res;
    }

    @GetMapping(path = "getExportHBAirDataByIds")
    public Map<String, String> getExportHBAirDataByIds( @RequestParam(value = "ids") List<String> ids,@RequestParam(value = "time") Instant time,@RequestParam(value = "geotype") String geotype)  {
        Map<String, String> res = new HashMap<>();
        List<AirQualityHour> airQualityHours = airQualityHourMapper.selectByIdAndTimeNew("HB_AIR",geotype,time);
        String filePath =null;
        if(airQualityHours !=null && airQualityHours.size()>0) {
            String tmp = time.plusSeconds(8 * 60 * 60).toString();
            String filename = "AIR" + "_" + geotype + "_" + replace(tmp);
            filePath = getAirService.exportTXT_WH(airQualityHours, filename);
        }
            res.put("filePath", filePath);
        return res;
    }


    @GetMapping(path = "getExportTWAirDataByIds")
    public void getExportTWAirDataByIds(@RequestParam(value = "ids") List<String> ids, @RequestParam(value = "time") Instant time,@RequestParam(value = "geotype") String geotype)  {
        Map<String, String> res = new HashMap<>();
        List<TWEPA> twepas = twepaMapper.selectByIdAndTimeNew("TW_EPA_AIR",geotype,time);
        if(twepas  !=null && twepas.size()>0) {
            String tmp = time.plusSeconds(8*60*60).toString();
            String filename =  "AIR" +"_"+geotype+"_" +replace(tmp);
            getAirService.exportTXT_TW(twepas,filename);
        }

//        res.put("filePath", filePath);
//        return res;
    }

    @GetMapping(path = "getExportCHAirDataByIds")
    public Map<String, String> getExportCHAirDataByIds(@RequestParam(value = "ids") List<String> ids, @RequestParam(value = "time") Long time,@RequestParam(value = "geotype") String geotype)  {
        Map<String, String> res = new HashMap<>();
        List<ChinaAirQualityHour> chinaAirQualityHours = chinaAirQualityHourMapper.selectByIdAndTimeNew("CH_AIR",geotype,time);
        String filePath = null;
        if (chinaAirQualityHours != null && chinaAirQualityHours.size()>0) {
            String filename = "AIR" +"_"+geotype+"_" +time.toString();
            filePath = getAirService.exportTXT_CH(chinaAirQualityHours,filename);
        }
            res.put("filePath", filePath);
        return res;
    }

    public String replace (String out){
        String out1 =out.replace("-","");
        String out2 = out1.replace("T","");
        String out3 = out2.replace("Z","");
        String out4 = out3.replace(":","");
        return  out4;
    }


    @ApiOperation("更新台湾缺失的数据")
    @GetMapping(path = "UpdateTWAir")
    public boolean UpdateTWAir() {

//      String filepath = "C:/Users/chenlu/Desktop/tw";
        String filepath = "/data/Ai-Sensing/DataCenter/air-quality/tmp";
        List<String> paths =getFiles(filepath);

        boolean count = true;
        for(String path :paths) {
            String document = insertTWEPA.getDocumentByGZip(path);
            List<TWEPA> twepas = insertTWEPA.getEPAInfo(document);
            for(TWEPA twepa :twepas) {
//            TWEPA twepa = twepas.get(0);
//              int status = twepaMapper.insertData(twepa);
                int status = twepaMapper.updateTWData(twepa);
                count = status >0 ;

            }
        }

        return count;
    }

    public static List<String> getFiles(String path) {
        List<String> files = new ArrayList();
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
                //文件名，不包含路径
                //String fileName = tempList[i].getName();
            }
            if (tempList[i].isDirectory()) {
                //这里就不递归了，
            }
        }
        return files;
    }



    @ApiOperation("手动接入和查询")
    @GetMapping(path = "getData")
    public List<AirQualityHour> getData(@RequestParam(value = "name") String name){
        List<AirQualityHour> res = getAirService.getDataByNameAndTime(name);
        return res;
    }



}
