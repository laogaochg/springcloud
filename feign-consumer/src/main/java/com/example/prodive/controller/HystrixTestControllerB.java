package com.example.prodive.controller;

import com.example.consumer.FeignHelloService;
import com.example.eureka.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: LaoGaoChuang
 * @Date : 2018/5/10 15:23
 */
@RestController
public class HystrixTestControllerB {
    @Autowired
    private FeignHelloService feignHelloService;
    public static long count = 0;
    public static long fileCount = 0;

    @RequestMapping("/getUser11")
//    @HystrixCommand(fallbackMethod = "helloFallback")
    public String getUser11() {
        UserDto userDto = feignHelloService.hello2(count++ + "", "hello2.age");
        System.out.println("FeignServiceController.getUser-----:  " + count);
        System.out.println(count);
        return userDto.toString();

    }

    public String helloFallback() {
        System.out.println("--------------------------------");
        System.out.println(fileCount++);
        return "error";
    }

}
