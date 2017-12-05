package com.csair.admin.config;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import com.csair.admin.core.po.core.User;
import com.csair.admin.util.PasswordUtils;

/**
 * Shiro密码匹配
 */
public class CredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken utoken=(UsernamePasswordToken) token;
        //获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
        String inPassword = new String(utoken.getPassword());
        //获得数据库中的密码
        String dbPassword=(String) info.getCredentials();
        //进行密码的比对
        User u = (User)info.getPrincipals().getPrimaryPrincipal();
        inPassword = PasswordUtils.getPassword(inPassword,u.getHashCode());
        return this.equals(inPassword, dbPassword);
    }

}