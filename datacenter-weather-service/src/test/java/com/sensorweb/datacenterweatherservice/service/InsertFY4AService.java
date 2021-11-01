package com.sensorweb.datacenterweatherservice.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.datacenterweatherservice.dao.FY4AMapper;
import com.sensorweb.datacenterweatherservice.entity.FY4A;
import com.sensorweb.datacenterweatherservice.entity.TianXingZhouStation;
import com.sensorweb.datacenterweatherservice.util.FY4AConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@EnableScheduling
public class InsertFY4AService {
    @Autowired
    FY4AMapper fY4AMapper;

    public String getApiDocument() throws IOException {
        String param = "userId=" + FY4AConstant.FY4A_AWX_USER_ID +"&pwd=" + FY4AConstant.FY4A_AWX_USER_PASSWORD + "&interfaceId=" + FY4AConstant.FY4A_AWX_USER_INTERFACEID + "&elements=" + FY4AConstant.FY4A_AWX_USER_ELEMENTS + "&dataCode=" + FY4AConstant.FY4A_AWX_USER_DATACODE + "&timeRange=" + "[20211027000000,20211027150000]" + "&limitCnt=" + FY4AConstant.FY4A_AWX_USER_LIMITCNT + "&dataFormat=" + FY4AConstant.FY4A_AWX_USER_DATAFORMAT;
        String document = DataCenterUtils.doGet(FY4AConstant.GET_FY4A_AWX_URL, param);
//        if (document != null) {
//            JSONObject rootObject = new JSONObject(Boolean.parseBoolean(document));
//            System.out.println(document);
//        }
//        return null;
        return document;
    }

    /**
     * 字符串转Instant  yyyy-MM-dd'T'HH:mm:ss'Z'
     * @return
     * @throws IOException
     */
    public Instant str2Instant(String time) {
        if (StringUtils.isBlank(time)) {
            return null;
        }
        String pattern = "yyyy-MM-dd HH:mm:ss";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        dateTimeFormatter.withZone(ZoneId.of("Asia/Shanghai"));
        LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
        return localDateTime.atZone(ZoneId.of("Asia/Shanghai")).toInstant();
    }

    /**
     * 通过api请求数据入库
     */

    public boolean getIOTInfo(String document) throws IOException {
        int statue = 0;
        FY4A fY4A = new FY4A();
        JSONObject jsonObject = JSON.parseObject(document);
        JSONArray message = jsonObject.getJSONArray("DS");
        for (int i = 0; i < message.size(); i++) {
            JSONObject object = message.getJSONObject(i);
            fY4A.setFileName(object.getString("FILE_NAME"));
            fY4A.setFormat(object.getString("FORMAT"));
            fY4A.setFileSize(object.getString("FILE_SIZE"));
            fY4A.setFileUrl(object.getString("FILE_URL"));
            fY4A.setImgBasE64(object.getString("IMGBASE64"));
            fY4A.setDateTime(str2Instant((object.getString("Datetime"))));
            fY4A.setSateSensorChanl(object.getString("FILE_NAME"));
            statue = fY4AMapper.insertData(fY4A);
        }
        return statue>0;

    }

}
