package com.example.prodive.controller;

import com.example.eureka.dto.ExecuteResult;
import com.example.eureka.dto.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class ProvideServiceController {

    @RequestMapping("/getUser")
    public User getUser(@RequestBody User us) {
        System.out.println("us = " + us);
        User u = new User();
        u.setId("111");
        u.setName("天天");
        return u;
    }

    @RequestMapping("/hello")
    public String hello() {
        User u = new User();
        u.setId("111");
        u.setName("天天");
        return u.toString();
    }

    @RequestMapping("/hello2")
    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "Integer", paramType = "path")
    public User hello2(@RequestHeader String name,String age) {
        System.out.println("ProvideServiceController.hello2");
        User u = new User();
        u.setId(name);
        u.setName(age);
        return u;
    }

    @RequestMapping("/hello3")
    public String hello3(@RequestBody User u) {
        System.out.println("ProvideServiceController.hello3");
        return "hello3:" + u.toString();
    }
    @RequestMapping("/hello4")
    public List<User> hello4(@RequestBody List<User> u) {
        System.out.println("ProvideServiceController.hello3"+u);
        u.addAll(u);
        return u;
    }


    @RequestMapping("/getUserExecute")
    public ExecuteResult<List<User>> getUserExecute(@RequestBody User us) {
        System.out.println("us = " + us);
        ExecuteResult<List<User>> result = new ExecuteResult<>();
        List<User> uss = new ArrayList<>();
        User u = new User();
        u.setId("111");
        u.setName("天天");
        uss.add(u);
        User u2 = new  User();
        u2.setId("2222");
        u2.setName("aaaaa");
        uss.add(u2);
        result.setData(uss);
        result.setSuccess(true);
        return result;
    }
}
