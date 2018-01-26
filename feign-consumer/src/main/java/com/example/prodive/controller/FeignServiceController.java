package com.example.prodive.controller;

import com.example.consumer.FeignHelloService;
import com.example.eureka.dto.ExecuteResult;
import com.example.eureka.dto.User;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class FeignServiceController {
    @Autowired
    private FeignHelloService feignHelloService;

    @RequestMapping("/FeignServiceController")
    public String getUser() {
        System.out.println(feignHelloService.hello2("hello2.name","hello2.age"));
        User user = new User();
        user.setName("天天");
        user.setId("322332");
        System.out.println(feignHelloService.hello3(user));
        List<User> us = new ArrayList<>();
        us.add(user);
        User use = new User();
        use.setName("死肥宅");
        us.add(use);
        System.out.println(feignHelloService.hello4(us));
        return feignHelloService.hello();
    }

}
