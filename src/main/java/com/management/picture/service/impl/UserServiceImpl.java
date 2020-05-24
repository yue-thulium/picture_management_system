package com.management.picture.service.impl;

import com.management.picture.mapper.UserMapper;
import com.management.picture.model.User;
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
    public User getUserInf(String username) {
        return userMapper.getUserInf(username);
    }
}
