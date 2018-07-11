package com.example.consumer.fallBack;

import com.example.consumer.FeignHelloService;
import com.example.eureka.dto.UserDto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: LaoGaoChuang
 * @Date : 2018/5/11 14:44
 */
//@Component
public class FeignHelloServiceFallBack implements FeignHelloService {
    @Override
    public String hello() {
        System.out.println("FeignHelloServiceFallBack.hello");
        return "ERROR";
    }

    @Override
    public UserDto hello2(String name, String age) {
        UserDto result =new UserDto();
        result.setName("ERROR");
        System.out.println("FeignHelloServiceFallBack.hello2");
        return result;
    }

    @Override
    public String hello3(UserDto us) {
        System.out.println("FeignHelloServiceFallBack.hello3");
        return "ERROR";
    }

    @Override
    public List<UserDto> hello4(List<UserDto> us) {
        System.out.println("FeignHelloServiceFallBack.hello4");
        return new ArrayList<>();
    }
}
