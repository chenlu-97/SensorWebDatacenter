package com.sensorweb.sosobsservice.util;

import org.springframework.stereotype.Component;

@Component
public interface ObsConstant {
    /**
     * 湖北省环境监测站数据请求用户名UsrName
     */
    String USER_NAME = "allstation";

    /**
     * 湖北省环境监测站数据请求密码passWord
     */
    String PASSWORD = "IWJD5r5j3Nx4kXWO";

    /**
     * 湖北省环境监测站url请求常量---GetLast24HourData
     */
    String GET_LAST_24_HOUR_DATA = "http://59.172.208.250:9400/AirPulish/PublishData.asmx/GetLast24HourData";

    /**
     * 湖北省环境监测站url请求常量---GetLast7DaysData
     */
    String GET_LAST_7_Days_DATA = "http://59.172.208.250:9400/AirPulish/PublishData.asmx/GetLast7DaysData";

    /**
     * 湖北省环境监测站url请求常量---GetLastHoursData
     */
    String GET_LAST_HOURS_DATA = "http://59.172.208.250:9400/AirPulish/PublishData.asmx/GetLastHoursData_New";

    /**
     * 湖北省环境监测站url请求常量---GetOriqinalDayilyData
     */
    String GET_ORIGINAL_DAYILY_DATA = "http://59.172.208.250:9400/AirPulish/PublishData.asmx/GetOriginalDayilyData";

    /**
     * 湖北省环境监测站url请求常量---GetOriginalHourlyData
     */
    String GET_ORIGINAL_HOURLY_DATA = "http://59.172.208.250:9400/AirPulish/PublishData.asmx/GetOriginalHourlyData";

    /**
     * 湖北省环境监测站url请求常量---getLast40DaysData
     */
    String GET_LAST_40_DAYS_DATA = "http://59.172.208.250:9400/AirPulish/AuditData.asmx/GetLast40DaysData";

    /**
     * SOS ObservationType
     */
    String OM_OBSERVATION = "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Observation";
    String OM_MEASUREMENT = "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_Measurement";
    String OM_CATEGORYOBSERVATION = "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_CategoryObservation";
    String OM_COMPLEXOBSERVATION = "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_ComplexObservation";
    String OM_COUNTOBSERVATION = "http://www.opengis.net/def/observationType/OGC-OM/2.0/CountObservation";
    String OM_DISCRETECOVERAGEOBSERVATION = "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_DiscreteCoverageObservation";
    String OM_GEOMETRYOBSERVATION = "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_GeometryObservation";
    String OM_POINTCOVERAGE = "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_PointCoverage";
    String OM_TEMPORALOBSERVATION = "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_TemporalObservation";
    String OM_TIMESERIESOBSERVATION = "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_TimeSeriesObservation";
    String OM_TRUTHOBSERVATION = "http://www.opengis.net/def/observationType/OGC-OM/2.0/OM_TruthObservation";

    /**
     * SOS FeatureOfInterestType
     */
    String SF_SAMPLINGPOINT = "http://www.opengis.net/def/samplingFeatureType/OGC-OM/2.0/SF_SamplingPoint";
    String SF_SAMPLINGCURVE = "http://www.opengis.net/def/samplingFeatureType/OGC-OM/2.0/SF_SamplingCurve";
    String SF_SAMPLINGSURFACE = "http://www.opengis.net/def/samplingFeatureType/OGC-OM/2.0/SF_SamplingSurface";
    String SF_SPECIMEN = "http://www.opengis.net/def/samplingFeatureType/OGC-OM/2.0/SF_Specimen";

    /**
     * InsertSensor前后缀
     */
    String INSERT_SENSOR_PREFIX = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "\n" +
            "<swes:InsertSensor xsi:schemaLocation=\"http://www.opengis.net/sos/2.0 http://schemas.opengis.net/sos/2.0/sosInsertSensor.xsd http://www.opengis.net/swes/2.0 http://schemas.opengis.net/swes/2.0/swes.xsd\"\n" +
            "    xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "    xmlns:xlink=\"http://www.w3.org/1999/xlink\"\n" +
            "    xmlns:gml=\"http://www.opengis.net/gml\"\n" +
            "    xmlns:sml=\"http://www.opengis.net/sensorML/2.0\"\n" +
            "    xmlns:swe=\"http://www.opengis.net/swe/1.0.1\"\n" +
            "    xmlns:sos=\"http://www.opengis.net/sos/2.0\"\n" +
            "    xmlns:swes=\"http://www.opengis.net/swes/2.0\" version=\"2.0.0\" service=\"SOS\">\n" +
            "    <swes:procedureDescriptionFormat>http://www.opengis.net/sensorML/2.0</swes:procedureDescriptionFormat>\n" +
            "    <swes:procedureDescription>";
    String INSERT_SENSOR_SUFFIX = "</swes:procedureDescription>\n" +
            "</swes:InsertSensor>";

    /**
     * InsertObservation前后缀
     */
    String INSERT_OBSERVATION_PREFIX = "<sos:InsertObservation xmlns:sos=\"http://www.opengis.net/sos/2.0\" \n" +
            "xmlns:swes=\"http://www.opengis.net/swes/2.0\" \n" +
            "xmlns:swe=\"http://www.opengis.net/swe/2.0\" \n" +
            "xmlns:sml=\"http://www.opengis.net/sensorML/1.0.1\" \n" +
            "xmlns:gml=\"http://www.opengis.net/gml/3.2\" \n" +
            "xmlns:xlink=\"http://www.w3.org/1999/xlink\" \n" +
            "xmlns:om=\"http://www.opengis.net/om/2.0\" \n" +
            "xmlns:sams=\"http://www.opengis.net/samplingSpatial/2.0\" \n" +
            "xmlns:sf=\"http://www.opengis.net/sampling/2.0\" \n" +
            "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" service=\"SOS\" version=\"2.0.0\" \n" +
            "xsi:schemaLocation=\"http://www.opengis.net/sos/2.0 http://schemas.opengis.net/sos/2.0/sos.xsd http://www.opengis.net/samplingSpatial/2.0 http://schemas.opengis.net/samplingSpatial/2.0/spatialSamplingFeature.xsd\">";
    String INSERT_OBSERVATION_SUFFIX = "</sos:observation>\n" +
            "</sos:InsertObservation>";
    /******************************************************************************************************************/
    /**********************************************Hamawari相关常量*****************************************************/
    /***********************************************以下皆为最新版本*****************************************************/

    /**
     * Hamawari APR Level2
     */
    String AREOSOL_PROPERTY_LEVEL2 = "/pub/himawari/L2/ARP/021/";

    /**
     * Hamawari APR Level3
     */
    String AREOSOL_PROPERTY_LEVEL3 = "/pub/himawari/L3/ARP/030/";

    /**
     * user & paword &host
     */
    String HAMAWARI_HOST = "ftp.ptree.jaxa.jp";
    String HAMAWARI_USERNAME = "yangyunshan123_gmail.com";
    String HAMAWARI_PASSWORD = "SP+wari8";


    /**
    * 文件地址
    * */
    String File_Path = "/data/Ai-Sensing";
//    String File_Path = "C:/Users/chenlu/Desktop";


}
