package com.sensorweb.datacenterhimawariservice.util;

import org.springframework.stereotype.Component;

@Component
public interface HimawariConstant {
    /**
     * Hamawari APR Level2
     */
    String AREOSOL_PROPERTY_LEVEL2 = "/pub/himawari/L2/ARP/021/";

    /**
     * Hamawari APR Level3
     */
    String AREOSOL_PROPERTY_LEVEL3 = "/pub/himawari/L3/ARP/031/";

    /**
     * user & paword &host
     */
    String HAMAWARI_HOST = "ftp.ptree.jaxa.jp";
    String HAMAWARI_USERNAME = "yangyunshan123_gmail.com";
    String HAMAWARI_PASSWORD = "SP+wari8";
}
