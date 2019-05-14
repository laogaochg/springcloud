package com.example.eureka.service;

import com.example.eureka.util.ExecuteResult;
import com.example.eureka.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CustomController {
    @Autowired
    private RestTemplate restTemplate;


    @RequestMapping("/getUser")
    public String getUser11() {
        System.out.println("FeignServiceController.getUser");
        String s = restTemplate.getForObject("http://CLOUD-SIMPLE-SERVICE/eureka-prodive/hello", String.class);
        System.out.println("s = " + s);
        return s;
    }

    @GetMapping(value = "/find")
    public UserDto find() {
        //url中对应api提供者的名称，全大写
        UserDto u = new UserDto("111","天天"+ "com.example.eureka.service.CustomController.find()");
        return u;
    }
    @RequestMapping("getOrderInnDayList")
    public List<UserDto> getOrderInnDayList(String orderCode, Integer memberId){
        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("memberId", memberId);
        String url = "http://CLOUD-SIMPLE-SERVICE/getUserExecute";
        //use postForList
        ExecuteResult<List<UserDto>> result = RestTemplateUtil.postForList(url, params,
                new ParameterizedTypeReference<ExecuteResult<List<UserDto>>>(){});
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
