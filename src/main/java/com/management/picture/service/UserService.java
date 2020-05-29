package com.management.picture.service;

import com.management.picture.model.User;
import com.management.picture.model.usual.Fan;
import com.management.picture.model.usual.Follow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created on 2020/4/23.
 *
 * 用户基础信息相关内容
 *
 * @author Yue Wu
 */
public interface UserService {
    /**
     * 获得密码
     */
    String getPassword(String username);

    /**
     * 获得角色权限
     */
    String getRole(String username);

    /**
     * 修改密码
     */
    void updatePassword(String username, String newPassword);

    /**
     * 获得存在的用户
     */
    List<String> getUser();

    /**
     * 封号
     */
    void banUser(String username);

    /**
     * 解封账号
     */
    void reBanUser(String username);

    /**
     * 检查用户状态
     */
    int checkUserBanStatus(String username);

    /**
     * 获得用户角色默认的权限
     */
    String getRolePermission(String username);

    /**
     * 获得用户的权限
     */
    String getPermission(String username);

    /**
     * 获取用户id
     */
    String getUserId(String username);

    /**
     * 添加用户
     */
    void addUser(String username,String password,String email,String phone);

    /**
     * 校验邮箱
     */
    User verifyMail(String email);

    /**
     * 获取用户信息
     */
    User getUserInf(String username);

    /**
     * 获取用户头像
     */
    String getUserIcon(String username);

    /**
     * 获取用户粉丝
     */
    List<Fan> getUserFans(String id);

    /**
     * 获取用户粉丝
     */
    List<Follow> getUserFollows(String id);

    /**
     * 关注某人
     */
    int setUserFollows(String id,String username);

    /**
     * 取关某人
     */
    int dropUserFollows(String id,String username);
}
