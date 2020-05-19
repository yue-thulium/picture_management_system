package com.management.picture.controller.user;

import com.management.picture.model.ResultMap;
import com.management.picture.model.User;
import com.management.picture.service.UserService;
import com.management.picture.util.JWTUtil;
import com.management.picture.util.Md5Encoding;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2020/4/23.
 *
 * 用户登录注册用Controller
 *
 * @author Yue Wu
 */
@RestController
@Api(tags = "用户登录、注册相关接口")
public class LoginAndRegisterController {

    @Autowired
    private ResultMap resultMap;
    @Autowired
    private UserService userService;

    /**
     * 登陆接口
     *
     * 返回码说明：
     * code: 40010 => 用户名错误（用户名不存在）
     * code: 40011 => 用户密码校验出错
     * code: 200 => 登录完成，发放token
     *
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping("/login")
    @ApiOperation("用户登录的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=40010,message="用户名错误（用户名不存在）"),
            @ApiResponse(code=40011,message="用户密码校验出错"),
            @ApiResponse(code=200,message="登录完成，发放token")
    })
    public ResultMap login(@RequestParam("username") String username,
                           @RequestParam("password") String password) {
        String realPassword = userService.getPassword(username);
        String inPutPassword = Md5Encoding.md5SaltEncode(password);
        resultMap.clear();
        if (realPassword == null) {
            return resultMap.fail().code(40010).message("用户名错误");
        } else if (!realPassword.equals(inPutPassword)) {
            return resultMap.fail().code(40011).message("密码错误");
        } else {
            return resultMap.success().code(200).message(JWTUtil.createToken(username,userService.getUserId(username)));
        }
    }

    /**
     * 注册接口
     *
     * 返回码说明：
     * code: 1000 => 用户输入的用户名已被注册
     * code: 200 => 注册成功
     *
     * @param username 用户名
     * @param password 密码
     * @param email 邮箱
     * @param phone 电话
     * @return
     */
    @PostMapping("/register")
    @ApiOperation("用户注册的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "用户密码", required = true),
            @ApiImplicitParam(name = "email", value = "用户邮箱", required = true),
            @ApiImplicitParam(name = "phone", value = "用户电话", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=1000,message="用户输入的用户名已被注册"),
            @ApiResponse(code=200,message="注册成功")
    })
    public ResultMap register(@RequestParam("username") String username,
                              @RequestParam("password") String password,
                              @RequestParam("email") String email,
                              @RequestParam("phone") String phone) {
        resultMap.clear();
        String if_password = userService.getPassword(username);
        if (if_password != null) {
            return resultMap.fail().code(1000).message("该用户名已被注册！");
        } else {
            String md5Password = Md5Encoding.md5SaltEncode(password);
            userService.addUser(username,md5Password,email,phone);
            return resultMap.success().code(200).message("注册成功！");
        }
    }

    /**
     * 注册邮箱校验接口
     * 返回码说明：
     * code: 40010 => 用户输入的邮箱已存在绑定账号
     * code: 200 => 邮箱未被使用
     * @param email 邮箱地址
     * @return
     */
    @GetMapping("/verifyMail")
    @ApiOperation(value = "用户注册的接口", notes = "用于注册邮箱校验")
    @ApiImplicitParam(name = "email", value = "邮箱地址", required = true)
    @ApiResponses({
            @ApiResponse(code=40010,message="用户输入的邮箱已存在绑定账号"),
            @ApiResponse(code=200,message="邮箱未被使用，可以用于当前注册")
    })
    public ResultMap verifyMail(@RequestParam("email") String email) {
        resultMap.clear();
        User user = userService.verifyMail(email);
        if (user != null) {
            return resultMap.fail().code(40010);
        } else {
            return resultMap.success().code(200);
        }

    }
}
