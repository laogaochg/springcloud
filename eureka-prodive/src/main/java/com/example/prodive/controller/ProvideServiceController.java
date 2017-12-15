package com.example.prodive.controller;
import com.example.eureka.dto.ExecuteResult;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping("/getUserExecute")
    public ExecuteResult<List<User>> getUserExecute(@RequestBody User us) {
        System.out.println("us = " + us);
        ExecuteResult<List<User>> result = new ExecuteResult<>();
        List<User> uss = new ArrayList<>();
        User u = new User();
        u.setId("111");
        u.setName("天天");
        uss.add(u);
        User u2 = new User();
        u2.setId("2222");
        u2.setName("aaaaa");
        uss.add(u2);
        result.setData(uss);
        result.setSuccess(true);
        return result;
    }
}
