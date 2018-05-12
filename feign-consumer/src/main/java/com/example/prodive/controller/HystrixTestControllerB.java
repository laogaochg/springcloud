package com.example.prodive.controller;

import com.example.consumer.FeignHelloService;
import com.example.eureka.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LaoGaoChuang
 * @Date : 2018/5/10 15:23
 */
@RestController
public class HystrixTestControllerB {
    @Autowired
    private FeignHelloService feignHelloService;

    @RequestMapping("/getUser11")
    public String getUser11() {
        feignHelloService.hello();
        UserDto userDto = feignHelloService.hello2("", "hello2.age");
        List<UserDto> userDtos = feignHelloService.hello4(new ArrayList<>());
        System.out.println("FeignServiceController.getUser-----:  " );
        System.out.println("userDtos = " + userDtos);
        return userDto.toString();

    }
    @RequestMapping("/feignHelloServiceHello")
    public String feignHelloServiceHello() {
        return feignHelloService.hello();
    }


}
