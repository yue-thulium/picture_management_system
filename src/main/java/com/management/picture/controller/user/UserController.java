package com.management.picture.controller.user;

import com.management.picture.model.result.ResultModel;
import com.management.picture.model.result.ResultUserInfModel;
import com.management.picture.model.User;
import com.management.picture.model.usual.Fan;
import com.management.picture.model.usual.Follow;
import com.management.picture.service.UserService;
import com.management.picture.util.FastdfsUtils;
import com.management.picture.util.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private ResultModel resultModel;

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

    /**
     * 获取用户头像
     *
     * 返回头像图片存储地址
     *
     * @param token 凭证
     * @return 图片存储地址
     */
    @GetMapping("/getIcon")
    @ApiOperation("用户头像获取接口")
    @ApiImplicitParam(name = "token", value = "用户凭证", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public String getIcon(@RequestHeader String token) {
        return FastdfsUtils.BASE_URL + userService.getUserIcon(JWTUtil.getUsername(token));
    }

    /**
     * 获取用户粉丝信息
     * @param token 凭证
     * @return 粉丝列表
     */
    @GetMapping("/getFans")
    @ApiOperation("用户粉丝获取接口")
    @ApiImplicitParam(name = "token", value = "用户凭证", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public List<Fan> getFans(@RequestHeader String token) {
        return userService.getUserFans(JWTUtil.getUserID(token));
    }

    /**
     * 获取用户粉丝信息
     * @param token 凭证
     * @return 粉丝列表
     */
    @GetMapping("/getFollows")
    @ApiOperation("用户关注获取接口")
    @ApiImplicitParam(name = "token", value = "用户凭证", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public List<Follow> getFollows(@RequestHeader String token) {
        return userService.getUserFollows(JWTUtil.getUserID(token));
    }

    @GetMapping("/setFollows/{username}")
    @ApiOperation("用户关注他人操作接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "当前用户凭证", required = true),
            @ApiImplicitParam(name = "username", value = "被关注者用户名", required = true)
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel setFollows(@RequestHeader String token, @PathVariable String username) {
        if (userService.setUserFollows(JWTUtil.getUserID(token),username) > 0) {
            resultModel.setValue(ResultModel.SUCCESS,200,"添加关注成功！");
        } else {
            resultModel.setValue(ResultModel.FAIL,400,"添加关注失败！");
        }
        return resultModel;
    }
}
