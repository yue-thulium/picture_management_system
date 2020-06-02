package com.management.picture.model.usual;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created on 2020/5/26.
 *
 * @author Yue Wu
 */
@Data
@ApiModel(value = "粉丝对象" ,description = "粉丝所携带的信息")
public class Fan {
    @ApiModelProperty(value = "头像",example="http://url")
    private String icon;
    @ApiModelProperty(value = "粉丝用户名",example="username")
    private String username;
}