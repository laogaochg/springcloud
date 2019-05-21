package com.gateway.filter;

import javax.servlet.http.HttpServletRequest;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @Author: LaoGaoChuang
 * @Date : 2019/5/21 9:56
 */
@Component
public class OrderRateLimitFilter extends ZuulFilter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    //每秒钟产生1000个令牌，guava
    private static final RateLimiter rateLimiter = RateLimiter.create(1000);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -4;
    }

    @Override
    public boolean shouldFilter() {

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        if ("/apigateway/order/api/v1/order/saveforribbon".equalsIgnoreCase(request.getRequestURI())) {
            return true;
        }
        return false;
    }

    @Override
    public Object run()  {
        RequestContext requestContext = RequestContext.getCurrentContext();
        if (!rateLimiter.tryAcquire()) {
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }
        return null;
    }
}