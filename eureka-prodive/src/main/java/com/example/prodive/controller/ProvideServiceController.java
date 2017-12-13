package com.example.prodive.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProvideServiceController {

    @RequestMapping("/getUser")
    public User doDiscoveryService(@RequestBody User us) {
        System.out.println("us = " + us);
        User u = new User();
        u.setId("111");
        u.setName("天天");
        return u;
    }
}
