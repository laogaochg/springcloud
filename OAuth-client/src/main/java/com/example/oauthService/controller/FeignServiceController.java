package com.example.oauthService.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Controller
public class FeignServiceController {
    @RequestMapping("login")
    public String login() {
        return "index";
    }
    @RequestMapping("securedPage")
    public String securedPage() {
        return "securedPage";
    }
}
