package com.sensorweb.datacenterindicatorservice.service;

import com.sensorweb.datacenterindicatorservice.dao.SubThemeMapper;
import com.sensorweb.datacenterindicatorservice.dao.ThemeMapper;
import com.sensorweb.datacenterindicatorservice.entity.SubTheme;
import com.sensorweb.datacenterindicatorservice.entity.Theme;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
public class IndicatorService {

    @Autowired
    private ThemeMapper themeMapper;

    @Autowired
    private SubThemeMapper subThemeMapper;

    /**
     * 解析指标xml文档,获取Theme对象
     * @param str
     * @return
     */
    public Theme parseIndicator(String str) throws DocumentException {
        Document document = DocumentHelper.parseText(str);
        Element root = document.getRootElement();
        Theme theme = new Theme();
        //根据root对象获取Tag信息
        Element tag = root.element("Tag");
        theme.setModelId(tag.element("Identification").element("ModelID").getText());
        theme.setModelName(tag.element("Identification").element("ModelName").getText());
        theme.setIndexName(tag.element("Identification").element("IndexName").getText());
        theme.setDescription(tag.element("Description").getText());
        //根据root对象获取Category信息
        Element category = root.element("Category");
        theme.setField(category.element("Field").getText());
        theme.setSubject(category.element("Theme").getText());
        //根据root对象获取Space-Time信息
        Element spaceTime = root.element("Space-Time");
        theme.setBeginTime(Instant.parse(spaceTime.element("Time").element("TimePeriod").elementText("beginPosition") + "Z"));
        theme.setEndTime(Instant.parse(spaceTime.element("Time").element("TimePeriod").elementText("endPosition") + "Z"));
        List<Element> vector = spaceTime.element("Space").element("field").elements("Vector");
        String[] lowerCorner = new String[2];
        String[] upperCorner = new String[2];
        if (vector!=null && vector.size()>0) {
            for (Element corner:vector) {
                if (corner.attributeValue("id").equals("lowerCorner")) {
                    List<Element> coordinates = corner.elements("coordinate");
                    for (Element coordinate:coordinates) {
                        if (coordinate.attributeValue("name").equals("Lon")) {
                            lowerCorner[0] = coordinate.element("Quantity").elementText("value");
                        }
                        if (coordinate.attributeValue("name").equals("Lat")) {
                            lowerCorner[1] = coordinate.element("Quantity").elementText("value");
                        }
                    }
                }
                if (corner.attributeValue("id").equals("upperCorner")) {
                    List<Element> coordinates = corner.elements("coordinate");
                    for (Element coordinate:coordinates) {
                        if (coordinate.attributeValue("name").equals("Lon")) {
                            upperCorner[0] = coordinate.element("Quantity").elementText("value");
                        }
                        if (coordinate.attributeValue("name").equals("Lat")) {
                            upperCorner[1] = coordinate.element("Quantity").elementText("value");
                        }
                    }
                }
            }
        }
        theme.setBbox(lowerCorner[0] + " " + lowerCorner[1] + "," + upperCorner[0] + " " + upperCorner[1]);
        String wkt = "POLYGON((" + lowerCorner[0] + " " + lowerCorner[1] + "," + lowerCorner[0] + " " + upperCorner[1] + "," +
                upperCorner[0] + " " + upperCorner[1] + "," + upperCorner[0] + " " + lowerCorner[1] + "," + lowerCorner[0] + " " + lowerCorner[1] + "))";
        theme.setWkt(wkt);
        //根据root对象获取Sub_Theme信息
        List<Element> subThemes = root.elements("SubTheme");
        if (subThemes!=null && subThemes.size()>0) {
            for (Element subTheme:subThemes) {
                SubTheme temp = new SubTheme();
                temp.setName(subTheme.attributeValue("name"));
                temp.setIndicatorName(subTheme.element("Indicator").attributeValue("name"));
                temp.setScale(subTheme.element("Indicator").elementText("Scale"));
                temp.setSpaResolution(subTheme.element("Indicator").element("SpatialResolution").elementText("Text"));
                temp.setObsFrequency(subTheme.element("Indicator").element("ObserveFrequency").elementText("Text"));
                temp.setOutId(theme.getModelId());
                theme.getSubThemes().add(temp);
            }
        }
        return theme;
    }

    /**
     * 注册指标信息
     */
    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertData(Theme theme) {
        List<SubTheme> subThemes = theme.getSubThemes();
        if (subThemes!=null && subThemes.size()>0) {
            for (SubTheme subTheme:subThemes) {
                subThemeMapper.insertData(subTheme);
            }
        }
        themeMapper.insertData(theme);
    }

    /**
     * 获取所有Theme
     */
    public List<Theme> getAllThemes() {
        return themeMapper.selectAll();
    }

    /**
     * 通过modelId获取Theme
     */
    public Theme getThemeByModelId(String modelId) {
        return themeMapper.selectByModelId(modelId);
    }
}
