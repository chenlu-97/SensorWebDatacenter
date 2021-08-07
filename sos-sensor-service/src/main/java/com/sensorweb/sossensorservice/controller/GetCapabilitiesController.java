package com.sensorweb.sossensorservice.controller;

import com.sensorweb.datacenterutil.utils.DataCenterUtils;
import com.sensorweb.sossensorservice.service.GetCapabilitiesService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.vast.ows.GetCapabilitiesRequest;
import org.w3c.dom.Element;

@Slf4j
@Api("GetCapabilities接口API")
@CrossOrigin
@RestController
public class GetCapabilitiesController {

    @Autowired
    private GetCapabilitiesService service;

    @RequestMapping(path = "/getCapabilities", method = RequestMethod.POST)
    public String getCapabilities(Model model, String requestContent) {
        Element element = null;
        try {
            GetCapabilitiesRequest request = service.getGetCapabilitiesRequest(requestContent);
            element = service.getGetCapabilitiesResponse(request);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (element!=null) {
            model.addAttribute("GetCapabilitiesResponse", DataCenterUtils.element2String(element));
        }
        model.addAttribute("GetCapabilitiesRequest", requestContent);
        model.addAttribute("tag","GetCapabilities");

        return "index";
    }
}
