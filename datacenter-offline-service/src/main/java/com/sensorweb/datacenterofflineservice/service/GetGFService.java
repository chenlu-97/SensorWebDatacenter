package com.sensorweb.datacenterofflineservice.service;


import com.sensorweb.datacenterofflineservice.dao.GFMapper;
import com.sensorweb.datacenterofflineservice.entity.GF;
import com.sensorweb.datacenterofflineservice.util.OfflineConstant;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@EnableScheduling
public class GetGFService {
    @Autowired
    private GFMapper gfMapper;

    public  List<String> getGFByWKT(String wkt, String satelliteID, String season) {
        return gfMapper.selectGFByWKT(wkt,satelliteID,season);
    }


    public int insertGF() throws Exception {

        GF gf = new GF();
        int status = 0;
        String[] path= OfflineConstant.GF_PATH;
        for(int h =0; h < path.length;h++) {
            System.out.println(path[h]);
            ArrayList<String> seasons = getFiles(path[h]);
            for (int i = 0; i < seasons.size(); i++) {
                System.out.println(seasons.get(i));
                ArrayList<String> image_name = getFiles(path[h] + seasons.get(i) + '/');

                for (int j = 0; j < image_name.size(); j++) {
                    System.out.println(image_name.get(j));
                    String filepath = path[h] + seasons.get(i) + '/' + image_name.get(j) + '/';
                    System.out.println(filepath);

                    File[] list = new File(filepath).listFiles();
                    for (File file : list) {
                        if (file.isFile()) {
                            if (file.getName().endsWith(".xml")) {
                                System.out.println(file.getAbsolutePath());

                                Element GFelement = parseXml(file.getAbsolutePath());

                                gf.setSatelliteId(GFelement.element("SatelliteID").getText());
                                gf.setQueryTime(DataCenterUtils.string2LocalDateTime3(GFelement.element("CenterTime").getText()).toInstant(ZoneOffset.ofHours(+8)));
                                gf.setScenePath(GFelement.element("SatPath").getText());
                                gf.setSceneRow(GFelement.element("SatRow").getText());
                                gf.setSeason(seasons.get(i));
                                gf.setImageId(file.getName().substring(0, file.getName().length() - 3) + "tiff");
                                gf.setFilePath(filepath + file.getName().substring(0, file.getName().length() - 3) + "tiff");

                                if(GFelement.element("SatelliteID").getText().equals("GF7-1")){
                                    gf.setImageType(GFelement.element("SensorID").getText());
                                }else{
                                    if (GFelement.element("Bands").getText().equals("5")) {
                                        gf.setImageType("PAN1");
                                    }else{
                                        gf.setImageType("MSS1");
                                    }
                                }
                                gf.setTopleftlatitude(Float.parseFloat(GFelement.element("TopLeftLatitude").getText())) ;
                                gf.setTopleftlongitude(Float.parseFloat(GFelement.element("TopLeftLongitude").getText()));
                                gf.setToprightlatitude(Float.parseFloat(GFelement.element("TopRightLatitude").getText()));
                                gf.setToprightlongitude(Float.parseFloat(GFelement.element("TopRightLongitude").getText()));
                                gf.setBottomleftlatitude(Float.parseFloat(GFelement.element("BottomLeftLatitude").getText()));
                                gf.setBottomleftlongitude(Float.parseFloat(GFelement.element("BottomLeftLongitude").getText()));
                                gf.setBottomrightlatitude(Float.parseFloat(GFelement.element("BottomRightLatitude").getText()));
                                gf.setBottomrightlongitude(Float.parseFloat(GFelement.element("BottomRightLongitude").getText()));

                                String TopLeftLatitude = GFelement.element("TopLeftLatitude").getText();
                                String TopLeftLongitude = GFelement.element("TopLeftLongitude").getText();
                                String TopRightLatitude = GFelement.element("TopRightLatitude").getText();
                                String TopRightLongitude = GFelement.element("TopRightLongitude").getText();
                                String BottomRightLatitude = GFelement.element("BottomRightLatitude").getText();
                                String BottomRightLongitude = GFelement.element("BottomRightLongitude").getText();
                                String BottomLeftLatitude = GFelement.element("BottomLeftLatitude").getText();
                                String BottomLeftLongitude = GFelement.element("BottomLeftLongitude").getText();

                                String bbox_tmp = TopLeftLongitude + ' ' + TopLeftLatitude + ',' + TopRightLongitude + ' ' + TopRightLatitude + ','
                                        + BottomRightLongitude + ' ' + BottomRightLatitude + ',' + BottomLeftLongitude+ ' ' + BottomLeftLatitude + ',' + TopLeftLongitude + ' ' + TopLeftLatitude;
                                String wkt = "POLYGON((" + bbox_tmp  + "))";
                                String bbox = '['+TopLeftLongitude+','+TopLeftLatitude+']'+','+'['+TopRightLongitude+','+TopRightLatitude+']'+','
                                        + '['+BottomRightLongitude+','+BottomRightLatitude+']'+','+'['+BottomLeftLongitude+','+BottomLeftLatitude+']'+','+'['+TopLeftLongitude+','+TopLeftLatitude+']';
                                gf.setBbox(bbox);
                                gf.setGeom(wkt);
                                status = gfMapper.insertGF(gf);
                            }
                        }
                    }
                }
            }
        }

        return status;
    }


    //    解析xml文档，返回根目录下的元素
    public Element parseXml(String str) throws Exception {
//        Object object = new ArrayList<>();
        //读取xml文档，返回Document对象
        //1.创建Reader对象
        SAXReader reader = new SAXReader();
        reader.setEncoding("UTF-8");//这里设置文件编码
        //2.加载xml
        Document document = reader.read(new File(str));
        //3.获取根节点
        Element rootElement = document.getRootElement();
        return rootElement;
    }


    //读取文件路径，获取文件下的所有文件的名称，返回文件名list
    public ArrayList<String> getFiles(String filepath) {
        ArrayList<String> file_names = new ArrayList<>();
        File dirFile = new File(filepath);
        if (dirFile.exists()) {
            File[] files = dirFile.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i ++) {
                    //输出文件名或者文件夹名
                    file_names.add(files[i].getName());
//                    System.out.println(files[i].getName());
                }
            }
        } else {
            System.out.println("查找的文件不存在");
        }
        return file_names;
    }




    public List<GF> selectGFByIds(List<Integer> ids) {
        List<GF> res = gfMapper.selectByIds(ids);
        return res;
    }

    public List<GF> selectGF() {
        List<GF> res = gfMapper.selectGF();
        return res;
    }

    public List<GF> selectGFByAttribute(String satelliteID, String season) {
        List<GF> res = gfMapper.selectGFByAttribute(satelliteID,season);
        return res;
    }


    public int getGFNum() {
        return  gfMapper.selectNum();
    }

    public String selectGFByImageId(String imageId) {
        return gfMapper.selectGFByImageId(imageId);
    }

    public List<GF> selectGFByImageIDAndTime(String imageId, Instant begin, Instant end) {
        return gfMapper.selectGFByImageIDAndTime(imageId,begin,end);
    }

    public List<String> selectGFGeom(Instant begin, Instant end) {
        return gfMapper.selectGFGeom(begin,end);
    }
}
