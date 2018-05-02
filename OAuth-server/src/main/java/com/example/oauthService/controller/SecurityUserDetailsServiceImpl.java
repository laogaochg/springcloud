package com.example.oauthService.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @Author: LaoGaoChuang
 * @Date : 2018/4/22 17:00
 */
@Component
public class SecurityUserDetailsServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//            logger.debug("查询得到用户：{}", userFromQuery);
//            logger.debug("得到其权限：{}", authorities);
        Collection<GrantedAuthority> rolesList = new ArrayList<>();
        GrantedAuthority role = new SimpleGrantedAuthority("USER");
        rolesList.add(role);
        String password = "890";
        UserDetails user = new User(s, password, rolesList);
        return user;
    }
}
