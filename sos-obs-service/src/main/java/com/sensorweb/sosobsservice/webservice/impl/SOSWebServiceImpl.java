package com.sensorweb.sosobsservice.webservice.impl;

import com.sensorweb.sosobsservice.service.GetObservationService;
import com.sensorweb.sosobsservice.service.InsertObservationService;
import com.sensorweb.sosobsservice.webservice.SOSWebservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;

@WebService(serviceName = "SOS", targetNamespace = "http://webservice.datacenter.sensorweb.com/",
        endpointInterface = "com.sensorweb.sosobsservice.webservice.SOSWebservice")
@Component
public class SOSWebServiceImpl implements SOSWebservice {

    @Autowired
    private InsertObservationService insertObservationService;

    @Autowired
    private GetObservationService getObservationService;

    @Override
    public String InsertObservation(String requestContent) {

        return "str";
    }

    @Override
    public String GetObservation(String requestContent) {
//        try {
//            GetObservationRequest request = getObservationService.getObservationRequest(requestContent);
//            List<Observation> observations = getObservationService.getObservationContent(request);
//            GetObservationResponse response = new GetObservationResponse();
//            if (observations!=null && observations.size()>0) {
//                for (Observation observation : observations) {
//                    getObservationService.getObservationResponse(observation.getValue(), response);
//                }
//            }
//            DOMHelper domHelper = new DOMHelper();
//            GetObservationResponseWriter writer = new GetObservationResponseWriter();
//            Element element = writer.buildXMLResponse(domHelper, response, "2.0");
//            String test = DataCenterUtils.element2String(element);
//            return DataCenterUtils.element2String(element);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        return "";
    }
}
