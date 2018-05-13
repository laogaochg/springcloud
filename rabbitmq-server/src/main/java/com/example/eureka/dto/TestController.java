package com.example.eureka.dto;

import com.example.eureka.service.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: LaoGaoChuang
 * @Date : 2018/3/14 22:45
 */
@Controller
public class TestController {
    @Autowired
    private Sender sender;

    @RequestMapping("aaa")
    @ResponseBody
    public String aa() {
        System.out.println(21111111);
        sender.send();
        return "gd gd ";
    }
}
