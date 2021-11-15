package com.sensorweb.datacenterofflineservice.controller;

import com.sensorweb.datacenterofflineservice.dao.GFMapper;
import com.sensorweb.datacenterofflineservice.entity.GF;
import com.sensorweb.datacenterofflineservice.entity.WaterPollution;
import com.sensorweb.datacenterofflineservice.service.GetGFService;
import com.thoughtworks.xstream.core.util.OrderRetainingMap;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.*;

@Slf4j
@RestController
@CrossOrigin
public class GetGF {

    @Autowired
    GetGFService getGFService;

    @Autowired
    private GFMapper gfMapper;

//    @ApiOperation("启动GF的离线数据的入库")
//    @PostMapping(value = "GFdata2DB")
//    @ResponseBody
//    public String GFdata2DB() throws Exception {
//        int status = getGFService.insertGF();
//        String res ;
//        if (status > 0) {
//            res = "SUCESS!!!";
//        } else {
//            res = "failed!!!";
//        }
//        return res;
//    }



    @ApiOperation("分页查询")
    @GetMapping(path = "getGFByPage")
    public Map<String, Object> getGFByPage(@ApiParam(name = "pageNum", value = "当前页码") @Param("pageNum") int pageNum,
                                                                     @ApiParam(name = "pageSize", value = "每页的数据条目数") @Param("pageSize") int pageSize) {
        Map<String, Object> res = new HashMap<>();
        List<GF> info =  gfMapper.selectByPage(pageNum, pageSize);
        int count = getGFService.getGFNum();
        Object num = new Integer(count);
        res.put("Info", info);
        res.put("num",num);
        return res;
    }


    @ApiOperation("根据id查询高分数据")
    @GetMapping(path = "getGFByID")
    @ResponseBody
    public Map<String, List<GF>> getGFByID(@RequestParam(value = "uniquecode") String uniquecode, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize) {
        Map<String, List<GF>> res = new HashMap<>();
        List<GF> info = gfMapper.selectByName(uniquecode,pageNum,pageSize);
        res.put("Info", info);
        return res;
    }



    @ApiOperation("查询GF卫星、季节、影像类型")
    @GetMapping(value = "selectGF")
    @ResponseBody
    public  Map selectGF() {
        List<GF> tmp = getGFService.selectGF();

        Map res = new LinkedHashMap();
        List<Map> features = new ArrayList<>();

        for(int i=0;i<tmp.size();i++){

            List<List<List<Float>>> coordinate = new ArrayList<>();
            List<List<Float>> bbox = new ArrayList<>();
            List<Float> topleft = new ArrayList<>();
            List<Float> topright = new ArrayList<>();
            List<Float> bottonleft = new ArrayList<>();
            List<Float> bottonright = new ArrayList<>();
            topleft.add(tmp.get(i).getTopleftlongitude());
            topleft.add(tmp.get(i).getTopleftlatitude());
            topright.add(tmp.get(i).getToprightlongitude());
            topright.add(tmp.get(i).getToprightlatitude());
            bottonleft.add(tmp.get(i).getBottomleftlongitude());
            bottonleft.add(tmp.get(i).getBottomleftlatitude());
            bottonright.add(tmp.get(i).getBottomrightlongitude());
            bottonright.add(tmp.get(i).getBottomrightlatitude());

            bbox.add(topleft);
            bbox.add(topright);
            bbox.add(bottonright);
            bbox.add(bottonleft);
            bbox.add(topleft);

            coordinate.add(bbox);

            Map map2 = new LinkedHashMap();
            Map map3 = new LinkedHashMap();
            Map map4 = new LinkedHashMap();

            map4.put("type","Polygon");
            map4.put("coordinates",coordinate);

            map3.put("id",tmp.get(i).getId().toString());

            map2.put("type","Feature");
            map2.put("properties",map3);
            map2.put("geometry",map4);
            features.add(map2);
        }
        res.put("type","FeatureCollection");
        res.put("features",features);

        return res;
    }


    @ApiOperation("查询GF卫星、季节、影像类型")
    @GetMapping(value = "selectGFByAttribute")
    @ResponseBody
    public Map selectGFByAttribute(@Param(value = "SatelliteID") String SatelliteID, @Param(value = "Season") String Season) {
        List<GF> tmp = getGFService.selectGFByAttribute(SatelliteID,Season);

        Map res = new LinkedHashMap();
        List<Map> features = new ArrayList<>();

        for(int i=0;i<tmp.size();i++){

            List<List<List<Float>>> coordinate = new ArrayList<>();
            List<List<Float>> bbox = new ArrayList<>();
            List<Float> topleft = new ArrayList<>();
            List<Float> topright = new ArrayList<>();
            List<Float> bottonleft = new ArrayList<>();
            List<Float> bottonright = new ArrayList<>();
            topleft.add(tmp.get(i).getTopleftlongitude());
            topleft.add(tmp.get(i).getTopleftlatitude());
            topright.add(tmp.get(i).getToprightlongitude());
            topright.add(tmp.get(i).getToprightlatitude());
            bottonleft.add(tmp.get(i).getBottomleftlongitude());
            bottonleft.add(tmp.get(i).getBottomleftlatitude());
            bottonright.add(tmp.get(i).getBottomrightlongitude());
            bottonright.add(tmp.get(i).getBottomrightlatitude());

            bbox.add(topleft);
            bbox.add(topright);
            bbox.add(bottonright);
            bbox.add(bottonleft);
            bbox.add(topleft);

            coordinate.add(bbox);

            Map map2 = new LinkedHashMap();
            Map map3 = new LinkedHashMap();
            Map map4 = new LinkedHashMap();

            map4.put("type","Polygon");
            map4.put("coordinates",coordinate);

            map3.put("id",tmp.get(i).getId().toString());
            map3.put("image_name",tmp.get(i).getImageId());

            map2.put("type","Feature");
            map2.put("properties",map3);
            map2.put("geometry",map4);
            features.add(map2);

        }
        res.put("type","FeatureCollection");
        res.put("features",features);

        return res;
    }




    @ApiOperation("根据ID查询，返回文件路径")
    @GetMapping (value = "selectGFByIds")
    @ResponseBody
    public List<Map<String,String>> selectGFByIds(@RequestParam(value = "ids") List<Integer> ids)  {
        List<GF> tmp = getGFService.selectGFByIds(ids);
        List<Map<String,String>> res = new ArrayList<>();

        for(int i=0;i<tmp.size();i++){
            Map<String,String> tmp2 = new HashMap<>();
            tmp2.put(tmp.get(i).getId().toString(),tmp.get(i).getFilePath());
            res.add(tmp2);
        }
        return res;
    }


    @ApiOperation("根据影像ID查询，返回文件路径")
    @GetMapping (value = "selectGFByImageID")
    @ResponseBody
    public String selectGFByImageID(@RequestParam(value = "imageId") String imageId)  {
        String res = getGFService.selectGFByImageId(imageId);
        return res;
    }


    @ApiOperation("根据影像ID查询，返回文件路径")
    @GetMapping (value = "selectGFByImageIDAndTime")
    @ResponseBody
    public Map<String,List<String>> selectGFByImageIDAndTime(@RequestParam(value = "imageId") String imageId,@RequestParam(value = "begin") Instant begin,@RequestParam(value = "end") Instant end)  {
//        List<String> geoms = getGFService.selectGFGeom(begin,end);
        List<GF> images =  getGFService.selectGFByImageIDAndTime(imageId,begin,end);

        Map<String,List<String>> res = new HashMap<>();
        for(GF image :images){
            if (image !=null) {
                String out_time = replace(image.getQueryTime().plusSeconds(8 * 60 * 60).toString());
                List<String> filepath = new ArrayList<>();
                filepath.add(image.getFilePath());
                res.put(out_time, filepath);
            }
        }
        return res;
    }

    public String replace (String out){
        String out1 =out.replace("-","");
        String out2 = out1.replace("T","");
        String out3 = out2.replace("Z","");
        String out4 = out3.replace(":","");
        return  out4;
    }



    @ApiOperation("查询数据总量")
    @GetMapping(path = "getGFNumber")
    public int getGFNum() {
        int count = getGFService.getGFNum();
        return count;
    }


    @ApiOperation("查询影像数据的空间范围")
    @GetMapping(value = "getGFCoordinates")
    @ResponseBody
    public List<Map> getGFCoordinates() {
        List<Map> res = new ArrayList<>();
        List<GF> info =  getGFService.selectGF();
        if(info.size()>0&& info !=null) {
            for (GF gf : info) {
                Map map = new LinkedHashMap();
                String id = gf.getImageId();
                String str = gf.getBbox();
                String time = gf.getQueryTime().toString();
                String coordinate = str.replace("[", "").replace("]","");
                String[] tmp = coordinate.split(",");
                List<Float> point = new ArrayList<>();
                for(String num :tmp){
                    point.add(Float.valueOf(num));
                }
                map.put("id",id);
                map.put("coordinate",point);
                map.put("time",time);
                res.add(map);
            }
        }
        return res;
    }


    @ApiOperation("根据wkt和一些参数查询GF，返回文件路径")
    @GetMapping (value = "selectGFByWKT")
    @ResponseBody
    public List<String> selectGFByWKT(@RequestParam(value = "wkt") String wkt,@Param(value = "SatelliteID") String SatelliteID, @Param(value = "Season") String Season)  {

        List<String> filePaths = getGFService.getGFByWKT(wkt,SatelliteID,Season);
        return filePaths;
    }



}
