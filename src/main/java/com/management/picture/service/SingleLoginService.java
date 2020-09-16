package com.management.picture.service;

import com.management.picture.model.result.ResultModel;

/**
 * Created on: 2020/9/15
 *
 * @author: Yue Wu
 */
public interface SingleLoginService {

    ResultModel login(String username, String password);

}
