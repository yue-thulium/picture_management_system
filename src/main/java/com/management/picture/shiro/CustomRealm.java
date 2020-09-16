package com.management.picture.shiro;

import com.management.picture.service.UserService;
import com.management.picture.util.JWTUtil;
import com.management.picture.util.RedisUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created on 2020/4/23.
 *
 * @author Yue Wu
 */
@Component
public class CustomRealm extends AuthorizingRealm {
    private final UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    @Resource(name = "createThreadPool")
    private ExecutorService chickPool;

    @Autowired
    public CustomRealm(UserService userService) {
        this.userService = userService;
    }

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     * <p>
     * 使用Redis此处校验Redis缓存，如不存在则直接抛校验异常
     * 存在则正常返回
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();

        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);

        String tokenCache = (String) redisUtil.hasKeyReturn(username);

        if (tokenCache == null) {
            throw new AuthenticationException("token认证失败！");
        } else {
            if (token.equals(tokenCache)) {
                return new SimpleAuthenticationInfo(token, token, "MyRealm");
            } else {
                throw new AuthenticationException("该账号在其他地点登陆！");
            }
        }
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        Future future = chickPool.submit(new Callable() {
            @Override
            public Object call() throws Exception {
                String token = principals.toString();
                String username = JWTUtil.getUsername(token);
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                //获得该用户角色
                String role = JWTUtil.getUserRole(token);
//        //每个角色拥有默认的权限
//        String rolePermission = userService.getRolePermission(username);
                //每个用户可以设置新的权限
                String permission = JWTUtil.getUserPermission(token);
                Set<String> roleSet = new HashSet<>();
                Set<String> permissionSet = new HashSet<>();
                //需要将 role, permission 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
                roleSet.add(role);
//        //若存在默认权限则使用
//        permissionSet.add(rolePermission);
                permissionSet.add(permission);
                //设置该用户拥有的角色和权限
                info.setRoles(roleSet);
                info.setStringPermissions(permissionSet);
                return info;
            }
        });

        try {
            return (AuthorizationInfo) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }
}

