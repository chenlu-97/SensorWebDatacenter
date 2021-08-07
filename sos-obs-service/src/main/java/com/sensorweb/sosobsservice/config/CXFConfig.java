package com.sensorweb.sosobsservice.config;

import com.sensorweb.sosobsservice.webservice.SOSWebservice;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
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
