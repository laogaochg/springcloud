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
    public String hello() {
        UserDto u = new UserDto();
        u.setId("111");
        u.setName("天天");
        int i = new Random().nextInt(3000);
        System.out.println("--------------------------------"+i);
        System.out.println("ProvideServiceController.hello");
        try {
            Thread.sleep(i*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return u.toString();
    }

    @RequestMapping("/hello2")
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
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
