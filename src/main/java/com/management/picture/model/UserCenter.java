package com.management.picture.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created on 2020/6/6.
 *
 * @author Yue Wu
 */
@Data
@ApiModel(value = "用户中心个人信息对象")
public class UserCenter {
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "昵称")
    private String nick_name;
    @ApiModelProperty(value = "电子邮箱")
    private String email;
    @ApiModelProperty(value = "手机号")
    private String phone;
}
