package com.example.oauthService.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("authentication = " + authentication);
        return "securedPage";
    }
}
