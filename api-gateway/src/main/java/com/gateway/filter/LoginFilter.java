package com.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @Author: LaoGaoChuang
 * @Date : 2019/5/21 9:47
 */
@Component
public class LoginFilter extends ZuulFilter {
    // 前置过滤器
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }
    // 过滤顺序
    @Override
    public int filterOrder() {
        return 4;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        System.out.println("address:" + request.getRemoteAddr());
        System.out.println("uri:" + request.getRequestURI());
        System.out.println("url:" + request.getRequestURL());
        // 这里可以结合ACL进行本地化或者放入到redis
        if ("/apigateway/order/api/v1/order/saveforribbon".equalsIgnoreCase(request.getRequestURI())) {
            return true;
        }
        return false;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            token = request.getParameter("token");
        }
        if (StringUtils.isBlank(token)) {
            requestContext.setSendZuulResponse(false);//不对其进行路由
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
        }
        return null;
    }
}
