package com.management.picture.controller.user;

import com.management.picture.model.ResultMap;
import com.management.picture.model.ResultUserInfMap;
import com.management.picture.model.ResultUserInfModel;
import com.management.picture.model.User;
import com.management.picture.service.UserService;
import com.management.picture.util.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2020/4/23.
 *
 * @author Yue Wu
 */
@RestController
@Api(tags = "用户操作获取相关接口")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ResultUserInfModel resultUserInfModel;

    /**
     * 获取用户信息
     *
     * 此处信息只返回Vuex中所存储的对应信息
     *
     * @param token 凭证
     * @return
     */
    @GetMapping("/getInf")
    @ApiOperation("用户信息获取接口")
    @ApiImplicitParam(name = "token", value = "用户凭证", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultUserInfModel getUser(@RequestHeader String token){
        String username = JWTUtil.getUsername(token);
        User user = userService.getUserInf(username);
        List<String> list = new ArrayList<>();
        list.add(user.getRole().getRole());
        list.add(user.getRole().getPermission());
        resultUserInfModel.setValue(user.getUsername(),user.getEmail(),list,user.getBan(),user.getPhone());
        return resultUserInfModel;
    }
}
