package com.example.prodive.controller;

import com.example.consumer.FeignHelloService;
import com.example.eureka.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@RestController
public class FeignServiceController {
    @Autowired
    private FeignHelloService feignHelloService;
    @Autowired
    private Environment environment;
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/getUser")
    public String getUser11() {
        System.out.println("FeignServiceController.getUser");
        String s = restTemplate.getForObject("http://CLOUD-SIMPLE-SERVICE/hello", String.class);
        System.out.println("s = " + s);
        return "";
    }
    @RequestMapping("/configTest")
    public String getUser1() {
        System.out.println("FeignServiceController.getUser");
        System.out.println(environment);
        return "";
    }

    @RequestMapping("/FeignServiceController")
    public String getUser() {
        System.out.println("FeignServiceController.getUser");
        System.out.println(environment);
        System.out.println(feignHelloService.hello2("hello2.name","hello2.age"));
        UserDto userDto = new UserDto();
        userDto.setName("天天");
        userDto.setId("322332");
        System.out.println(feignHelloService.hello3(userDto));
        List<UserDto> us = new ArrayList<>();
        us.add(userDto);
        UserDto use = new UserDto();
        use.setName("死肥宅");
        us.add(use);
        System.out.println(feignHelloService.hello4(us));
        return feignHelloService.hello();
    }

}
