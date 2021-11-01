package com.sensorweb.datacenterweatherservice.util;

public interface FY4AConstant {
    /**
     * FY4A_AWX数据请求URL
     */
    String GET_FY4A_AWX_URL = "http://61.183.207.181:8008/cimiss-web/api";

    /**
     * FY4A_AWX user_id
     */
    String FY4A_AWX_USER_ID = "BCWH_QXT_WHUZHY";

    /**
     * FY4A_AWX password
     */
    String FY4A_AWX_USER_PASSWORD = "whuzhy_210323";

    /**
     * FY4A_AWX interfaceid
     */
    String FY4A_AWX_USER_INTERFACEID = "getSateFileByTimeRange";

    /**
     * FY4A_AWX elements
     */
    String FY4A_AWX_USER_ELEMENTS = "Datetime,FILE_SIZE,File_URL,SATE_Sensor_Chanl";

    /**
     * FY4A_AWX dataCode
     */
    String FY4A_AWX_USER_DATACODE = "SATE_GEO_RAW_DMZ_FY4A";

    /**
     * FY4A_AWX limitCnt
     */
    int FY4A_AWX_USER_LIMITCNT = 30;

    /**
     * FY4A_AWX dataFormat
     */
    String FY4A_AWX_USER_DATAFORMAT = "json";
}
