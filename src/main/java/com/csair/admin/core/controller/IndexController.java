package com.csair.admin.core.controller;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.csair.admin.core.po.core.OperationLog;
import com.csair.admin.core.po.core.PageResult;
import com.csair.admin.core.po.core.query.OperationLogQueryObject;
import com.csair.admin.core.po.core.query.UserQueryObject;
import com.csair.admin.core.service.OperationLogService;
import com.csair.admin.core.service.UserService;

/**
 * laogaochg
 * 2017/7/10.
 * 首页
 */
@Controller
public class IndexController {

    @Resource
    private UserService userService;
    @Resource
    private OperationLogService operationLogService;

    /**
     * 首页
     *
     * @param qo
     * @param model
     * @return
     */
    @RequestMapping("index")
    public ModelAndView index(UserQueryObject qo,ModelAndView model) {
        PageResult pageResult = userService.query(qo);
        model.addObject("pageResult",pageResult);
        OperationLogQueryObject oqo = new OperationLogQueryObject();
        PageResult<OperationLog> operationLogPageResult = operationLogService.pageQuery(oqo);
        model.addObject("logResult",operationLogPageResult);
        model.setViewName("index");
        model.addObject("selectMenuIdForIntropect",0);
        return model;
    }
}
