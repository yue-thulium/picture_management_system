package com.management.picture.service.impl;

import com.management.picture.model.result.ResultModel;
import com.management.picture.service.SingleLoginService;
import com.management.picture.service.UserService;
import com.management.picture.util.JWTUtil;
import com.management.picture.util.Md5Encoding;
import com.management.picture.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created on: 2020/9/15
 *
 * @author: Yue Wu
 */
@Service
public class SingleLoginServiceImpl implements SingleLoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private ResultModel resultModel;

    @Resource(name = "createThreadPool")
    private ExecutorService loginPool;

    @Autowired
    RedisUtil redisUtil;

    @Override
    public ResultModel login(String username, String password) {

        Future future = loginPool.submit(new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println(Thread.currentThread().getName());
                String realPassword = userService.getPassword(username);
                String inPutPassword = Md5Encoding.md5SaltEncode(password);
                if (realPassword == null || !realPassword.equals(inPutPassword)) {
                    resultModel.setValue(ResultModel.FAIL,40011,"用户名或密码错误");
                }  else {
                    String token = JWTUtil.createToken(username,userService.getUserId(username)
                            ,userService.getRole(username),userService.getRolePermission(username));

                    String redisCache = (String) redisUtil.hasKeyReturn(username);

                    if (redisCache == null) {
                        redisUtil.set(username,token,60*60*24);
                    } else {
                        redisUtil.del(username);
                        redisUtil.set(username,token,60*60*24);
                    }

                    resultModel.setValue(ResultModel.SUCCESS,200, token);
                }
                return resultModel;
            }
        });

        try {
            return (ResultModel) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }
}
