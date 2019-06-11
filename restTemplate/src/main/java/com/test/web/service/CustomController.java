package com.test.web.service;

import com.test.web.util.ExecuteResult;
import com.test.web.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

@RestController
public class CustomController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private TestFeignService testFeignService;

    @RequestMapping("/loginSuccess")
    public String testFeignService(HttpServletRequest r) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute("user", "1");
        return "登录成功";
    }

    @RequestMapping("/userInfo")
    public String testFeignService1() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return "session:" + request.getSession().getAttribute("user") + "";
    }

    @RequestMapping("/index")
    public String index() throws IOException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        if ("1".equals(request.getSession().getAttribute("user"))) {
            requestAttributes.getResponse().sendRedirect("/userInfo");
            return "转发个人主页";
        }else{
            return "登陆页面";
        }
    }

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
        UserDto u = new UserDto("111", "天天" + "com.test.web.service.CustomController.find()");
        return u;
    }

    @RequestMapping("getOrderInnDayList")
    public List<UserDto> getOrderInnDayList(String orderCode, Integer memberId) {
        Map<String, Object> params = new HashMap<>();
        params.put("orderCode", orderCode);
        params.put("memberId", memberId);
        String url = "http://CLOUD-SIMPLE-SERVICE/getUserExecute";
        //use postForList
        ExecuteResult<List<UserDto>> result = RestTemplateUtil.postForList(url, params,
                new ParameterizedTypeReference<ExecuteResult<List<UserDto>>>() {
                });
        return result.getData();
    }

    /**
     * 测试下外网，也就是如果域名是外网的，不在eureka注册服务中的，会怎样
     *
     * @return
     */
    @RequestMapping("/test")
    @ResponseBody
    public String test() throws InterruptedException {
        Thread.sleep(5000);
        //url中对应api提供者的名称，全大写
        return "";
    }


}
