package com.sensorweb.datacenterauth2service.controller;

import com.sensorweb.datacenterauth2service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@Slf4j
public class AuthenticationController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/path/{name}", produces = "application/json;charset=utf-8")
    public List<String> getPathsByRoleName(@PathVariable("name") String name) {
        System.out.println("!!!");
        return userService.getPathByRoleName(name);
    }
}
