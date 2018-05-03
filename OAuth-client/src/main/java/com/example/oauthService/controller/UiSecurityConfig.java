package com.example.oauthService.controller;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: LaoGaoChuang
 * @Date : 2018/4/20 11:07
 */
@Configuration
@EnableOAuth2Sso
public class UiSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/**")
                .authorizeRequests()// 对请求进行认证
                .antMatchers("/", "/login**").permitAll()// 所有 /login 请求 都放行
                // 权限检查
//                .antMatchers("/hello").hasAuthority("AUTH_WRITE")
                // 角色检查
//                .antMatchers("/world").hasRole("ADMIN")
                .anyRequest().authenticated();
    }
}
