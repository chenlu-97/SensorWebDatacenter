package com.sensorweb.sosobsservice.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface SOSWebservice {

    @WebMethod
    String InsertObservation(@WebParam(name = "request") String requestContent);

    @WebMethod
    String GetObservation(@WebParam(name = "request") String requestContent);

}
