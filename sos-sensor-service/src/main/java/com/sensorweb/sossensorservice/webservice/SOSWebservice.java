package com.sensorweb.sossensorservice.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface SOSWebservice {
    @WebMethod
    String InsertSensor(@WebParam(name = "request") String requestContent);

    @WebMethod
    String DescribeSensor(@WebParam(name = "request") String requestContent);

    /**
     * 根据查询条件查询procedure
     * @param condition
     * @return
     */
    @WebMethod
    String SearchSensor(@WebParam(name = "condition") String condition);

    /**
     * 判断当前id是否已存在
     * @param procedureId
     * @return
     */
    @WebMethod
    String isExist(@WebParam(name = "procedureId") String procedureId);

    /**
     * 通过文件地址获取文件内容
     * @param filePath
     * @return
     */
    @WebMethod
    String getXMLContent(@WebParam(name = "filePath") String filePath);

    /**
     * 通过平台查传感器
     * @param platformId
     * @return
     */
    @WebMethod
    String getComponent(@WebParam(name = "platformId") String platformId);

    /**
     * 获取目录树
     * @return
     */
    @WebMethod
    String getTOC();

}
