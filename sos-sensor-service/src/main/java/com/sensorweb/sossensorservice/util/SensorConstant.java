package com.sensorweb.sossensorservice.util;

import org.springframework.stereotype.Component;

@Component
public interface SensorConstant {
    /**
     * InsertSensor前缀
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

    /**
     * InsertSensor后缀
     */
    String INSERT_SENSOR_SUFFIX = "</swes:procedureDescription>\n" +
            "</swes:InsertSensor>";
}
