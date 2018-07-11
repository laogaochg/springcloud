package com.example.prodive.controller;

import com.example.eureka.dto.ExecuteResult;
import com.example.eureka.dto.UserDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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
    public String hello() throws InterruptedException {
        UserDto u = new UserDto();
        u.setId("111");
        u.setName("天天");
        int i = new Random().nextInt(3000);
        System.out.println("--------------------------------" + i);
        System.out.println("ProvideServiceController.hello");
        return u.toString();
    }


    @RequestMapping("/hello2")
    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    public UserDto hello2(@RequestHeader String name, String age) throws InterruptedException {
        System.out.println("ProvideServiceController.hello2");
        UserDto u = new UserDto();
        u.setId(name);
        u.setName(age);
        int i = new Random().nextInt(3000);
        System.out.println("---------------------------" + i);
        Thread.sleep(i);
        return u;
    }

    @RequestMapping("/hello3")
    public String hello3(@RequestBody UserDto u) {
        System.out.println("ProvideServiceController.hello3");
        return "hello3:" + u.toString();
    }

    @RequestMapping("/hello4")
    public List<UserDto> hello4(@RequestBody List<UserDto> u) throws InterruptedException {
        System.out.println("ProvideServiceController.hello3" + u);
        Thread.sleep(3000);
        u.addAll(u);
        UserDto d = new UserDto();
        d.setName("ProvideServiceController.hello4");
        u.add(d);
        return u;
    }


    @RequestMapping("/getUserExecute")
    public ExecuteResult<List<UserDto>> getUserExecute(@RequestBody UserDto us) throws InterruptedException {
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
        Thread.sleep(3000);
        return result;
    }
}
