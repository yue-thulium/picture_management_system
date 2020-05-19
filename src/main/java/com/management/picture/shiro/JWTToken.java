package com.management.picture.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Created on 2020/4/23.
 *
 * Shiro集成Token配置类
 *
 * @author Yue Wu
 */
public class JWTToken implements AuthenticationToken {
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
