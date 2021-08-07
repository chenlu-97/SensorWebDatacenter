package com.sensorweb.datacenterlaadsservice.util;

import org.springframework.stereotype.Component;

@Component
public interface LAADSConstant {
    String LAADS_DOWNLOAD_TOKEN = "Bearer EDL-U8c8fc742b8d0e48dc39cf55a6d46382b307634d64206469228169d95289";

//    String LAADS_DOWNLOAD_TOKEN = "Bearer 40332b1d5cd1e21a85399b2f07ce59b7335430cdca7deee9d895b095a8bda740";

    String LAADS_Web_Service = "https://modwebsrv.modaps.eosdis.nasa.gov/axis2/services/MODAPSservices";
}
