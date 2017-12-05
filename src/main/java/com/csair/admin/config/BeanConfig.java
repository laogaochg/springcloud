package com.csair.admin.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * laogaochg
 * 2017/7/1.
 */
@Configuration
public class BeanConfig {

    //消费者bean
    // 可以自己订制
    @Bean
    public RestTemplate getRestTemplate() {
        RestTemplate ds = new RestTemplate();
        return ds;
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer(){
        return new EmbeddedServletContainerCustomizer(){
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
                container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/404"));
                container.addErrorPages(new ErrorPage(java.lang.Throwable.class,"/404"));
            }
        };
    }



}
