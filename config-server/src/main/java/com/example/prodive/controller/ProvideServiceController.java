package com.example.prodive.controller;

import com.example.eureka.dto.ExecuteResult;
import com.example.eureka.dto.UserDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ProvideServiceController {

    @RequestMapping("/getUser")
    public UserDto getUser(@RequestBody UserDto us) {
        System.out.println("us = " + us);
        UserDto u = new UserDto();
        u.setId("111");
        u.setName("天天");
        return u;
    }

    @RequestMapping("/hello")
    public String hello() {
        UserDto u = new UserDto();
        u.setId("111");
        u.setName("天天");
        return u.toString();
    }

    @RequestMapping("/hello2")
    public UserDto hello2(@RequestHeader String name, String age) {
        System.out.println("ProvideServiceController.hello2");
        UserDto u = new UserDto();
        u.setId(name);
        u.setName(age);
        return u;
    }

    @RequestMapping("/hello3")
    public String hello3(@RequestBody UserDto u) {
        System.out.println("ProvideServiceController.hello3");
        return "hello3:" + u.toString();
    }
    @RequestMapping("/hello4")
    public List<UserDto> hello4(@RequestBody List<UserDto> u) {
        System.out.println("ProvideServiceController.hello3"+u);
        u.addAll(u);
        return u;
    }


    @RequestMapping("/getUserExecute")
    public ExecuteResult<List<UserDto>> getUserExecute(@RequestBody UserDto us) {
        System.out.println("us = " + us);
        ExecuteResult<List<UserDto>> result = new ExecuteResult<>();
        List<UserDto> uss = new ArrayList<>();
        UserDto u = new UserDto();
        u.setId("111");
        u.setName("天天");
        uss.add(u);
        UserDto u2 = new UserDto();
        u2.setId("2222");
        u2.setName("aaaaa");
        uss.add(u2);
        result.setData(uss);
        result.setSuccess(true);
        return result;
    }
}
