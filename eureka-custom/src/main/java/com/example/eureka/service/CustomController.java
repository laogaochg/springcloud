package com.example.eureka.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.eureka.dto.ExecuteResult;
import com.example.eureka.util.RestTemplateUtil;

@RestController
public class CustomController {
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/find")
    public String find() {
        //url中对应api提供者的名称，全大写
        User u = new User("111","天天");
        String s = restTemplate.postForEntity("http://CLOUD-SIMPLE-SERVICE/getUser",u,String.class).getBody();
        System.out.println("s = " + s);
        return s;
    }
    @RequestMapping("getOrderInnDayList")
    public List<User> getOrderInnDayList(String orderCode,Integer memberId){
        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("memberId", memberId);
        String url = "http://CLOUD-SIMPLE-SERVICE/getUserExecute";
        //use postForList
        ExecuteResult<List<User>> result = RestTemplateUtil.postForList(url, params,
                new ParameterizedTypeReference<ExecuteResult<List<User>>>(){});
        return result.getData();
    }

    /**
     * 测试下外网，也就是如果域名是外网的，不在eureka注册服务中的，会怎样
     *
     * @return
     */
    @GetMapping(value = "/test")
    @ResponseBody
    public String test() {
        //url中对应api提供者的名称，全大写
        return restTemplate.getForEntity("http://www.baidu.com/",String.class).getBody();
    }


}
