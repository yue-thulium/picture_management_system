package com.management.picture.service.impl;

import com.management.picture.mapper.UserMapper;
import com.management.picture.model.User;
import com.management.picture.model.UserCenter;
import com.management.picture.model.usual.Fan;
import com.management.picture.model.usual.Follow;
import com.management.picture.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2020/4/23.
 *
 * @author Yue Wu
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserMapper userMapper;

    @Override
    public String getPassword(String username) {
        return userMapper.getPassword(username);
    }

    @Override
    public String getRole(String username) {
        return userMapper.getRole(username);
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        userMapper.updatePassword(username, newPassword);
    }

    @Override
    public List<String> getUser() {
        return userMapper.getUser();
    }

    @Override
    public void banUser(String username) {
        userMapper.banUser(username);
    }

    @Override
    public void reBanUser(String username) {
        userMapper.reBanUser(username);
    }

    @Override
    public int checkUserBanStatus(String username) {
        return userMapper.checkUserBanStatus(username);
    }

    @Override
    public String getRolePermission(String username) {
        return userMapper.getRolePermission(username);
    }

    @Override
    public String getPermission(String username) {
        return userMapper.getPermission(username);
    }

    @Override
    public String getUserId(String username) {
        return userMapper.getUserId(username);
    }

    @Override
    public void addUser(String username, String password, String email, String phone) {
        userMapper.addUser(username,password,email,phone);
    }

    @Override
    public User verifyMail(String email) {
        return userMapper.verifyMail(email);
    }

    @Override
    public User verifyUsername(String username) {
        return userMapper.verifyUsername(username);
    }

    @Override
    public User verifyPhone(String phone) {
        return userMapper.verifyPhone(phone);
    }

    @Override
    public User getUserInf(String username) {
        return userMapper.getUserInf(username);
    }

    @Override
    public String getUserNickName(int pm_id) {
        return userMapper.getUserNickName(pm_id);
    }

    @Override
    public int changeUserNickName(int pm_id, String new_nickname) {
        return userMapper.changeUserNickName(pm_id,new_nickname);
    }

    @Override
    public String getUserIcon(String username) {
        return userMapper.getUserIcon(username);
    }

    @Override
    public int changeUserIcon(String username, String picture) {
        return userMapper.changeUserIcon(username,picture);
    }

    @Override
    public UserCenter getUserCenterInf(String username) {
        return userMapper.getUserCenterInf(username);
    }

    @Override
    public int changeUserCenterInf(UserCenter userCenter) {
        return userMapper.changeUserCenterInf(userCenter);
    }

    @Override
    public List<Fan> getUserFans(String id) {
        return userMapper.getUserFans(id);
    }

    @Override
    public List<Follow> getUserFollows(String id) {
        return userMapper.getUserFollows(id);
    }

    @Override
    public int setUserFollows(String id, String username) {
        return userMapper.setUserFollows(id,username);
    }

    @Override
    public int dropUserFollows(String id, String username) {
        return userMapper.dropUserFollows(id,username);
    }
}
