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
public class Role {
    @ApiModelProperty(value = "权限ID")
    public int id;
    @ApiModelProperty(value = "用户身份")
    public String role;
    @ApiModelProperty(value = "用户权限")
    public String permission;
}
