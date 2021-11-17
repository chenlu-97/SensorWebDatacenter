package com.sensorweb.datacenterlaadsservice.util;

import org.springframework.stereotype.Component;

@Component
public interface LAADSConstant {
    String LAADS_DOWNLOAD_TOKEN = "Bearer EDL-Ude7312a8d20d49d6991c543ca62c2b43ac05e12db8a2b09c333a99b0fd1";

//    String LAADS_DOWNLOAD_TOKEN = "Bearer 40332b1d5cd1e21a85399b2f07ce59b7335430cdca7deee9d895b095a8bda740";

    String LAADS_Web_Service = "https://modwebsrv.modaps.eosdis.nasa.gov/axis2/services/MODAPSservices";

    String MERRA2_Web_Service = "https://goldsmr4.gesdisc.eosdis.nasa.gov/data/MERRA2_MONTHLY/M2IMNXGAS.5.12.4/";
}
