package com.test.web.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerExecutionChain;

/**
 * @Author: LaoGaoChuang
 * @Date : 2018/5/10 15:23
 */
@RestController
public class HystrixTestController {
    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/HystrixTestController")
    @HystrixCommand(fallbackMethod = "helloFallback")
    public String getUser11() {
        System.out.println("FeignServiceController.getUser");
        String s = restTemplate.getForObject("http://CLOUD-SIMPLE-SERVICE/eureka-prodive/hello", String.class);
        System.out.println("s = " + s);
        return s;
    }

    public String helloFallback() {
        System.out.println("--------------------------------");
        System.out.println("HystrixTestController.helloFallback");
        return "error";
    }

}
