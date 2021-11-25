package com.sensorweb.datacenterlaadsservice.util;

import org.springframework.stereotype.Component;

@Component
public interface LAADSConstant {


  String LAADS_DOWNLOAD_TOKEN = "Bearer Y3VnX2NoZW5sdTpZMmhsYm14MU56YzNRSGRvZFhRdVpXUjFMbU51OjE2Mzc3NTg4Njc6NzBmMzA3NDk4Njk5NWY4MjBmNDViMWU1ZTI0ZDUwYTRjNjdiNzQzZA";
//  String LAADS_DOWNLOAD_TOKEN = "Bearer EDL-U52b7698a403c9510181aa25a35065cca675aed54a31f4345ca986628eb55";
//
//    String LAADS_DOWNLOAD_TOKEN = "Bearer 40332b1d5cd1e21a85399b2f07ce59b7335430cdca7deee9d895b095a8bda740";

    String LAADS_Web_Service = "https://modwebsrv.modaps.eosdis.nasa.gov/axis2/services/MODAPSservices";

    String MERRA2_Web_Service = "https://goldsmr4.gesdisc.eosdis.nasa.gov/data/MERRA2_MONTHLY/M2IMNXGAS.5.12.4/";
}
