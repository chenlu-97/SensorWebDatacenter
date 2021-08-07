package com.sensorweb.sossensorservice.config;

import com.sensorweb.sossensorservice.webservice.SOSWebservice;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.Bus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;

@Configuration
public class CXFConfig {

    @Autowired
    private Bus bus;

    @Autowired
    private SOSWebservice sosWebservice;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, sosWebservice);
        endpoint.publish("/SOSWebservice");
        return endpoint;
    }
}
