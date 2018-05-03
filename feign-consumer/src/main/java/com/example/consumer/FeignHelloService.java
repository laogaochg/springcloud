package com.example.consumer;

import com.example.eureka.dto.User;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Author: LaoGaoChuang
 * @Date : 2018/1/26 23:23
 */
@FeignClient("CLOUD-SIMPLE-SERVICE")
public interface FeignHelloService {
    @RequestMapping("/eureka-prodive/hello")
    String hello();
    @RequestMapping("/eureka-prodive/hello2")
    User hello2(@RequestHeader("name") String name,String age);
    @RequestMapping("/eureka-prodive/hello3")
    String hello3(@RequestBody User us);
    @RequestMapping("/eureka-prodive/hello4")
    List<User> hello4(@RequestBody List<User> us);
}
