package com.example.prodive.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ProvideServiceController {

    @RequestMapping("/config-service/dev/master")
    public Map<String, Object> my(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        map.put("from", 8888888);
        map.put("server.port", 8877);
        return map;
    }

}