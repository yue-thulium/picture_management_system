package com.management.picture.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created on 2020/4/23.
 *
 * @author Yue Wu
 */
@Data
@ApiModel(description = "用户基础信息请求对象")
public class User {
    @ApiModelProperty(value = "用户ID")
    public int id;
    @ApiModelProperty(value = "用户名")
    public String username;
    @ApiModelProperty(value = "用户密码")
    public String password;
    @ApiModelProperty(value = "用户邮箱")
    public String email;
    @ApiModelProperty(value = "用户手机号")
    public String phone;
    @ApiModelProperty(value = "用户权限")
    public Role role;
    @ApiModelProperty(value = "用户账户状态")
    public int ban;
    @ApiModelProperty(value = "用户头像")
    public String icon;
}
