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
@ApiModel(value = "关注对象" ,description = "关注对象所携带的信息")
public class Follow {
    @ApiModelProperty(value = "头像",example="http://url")
    private String icon;
    @ApiModelProperty(value = "被关注者用户名",example="username")
    private String username;
    @ApiModelProperty(value = "昵称",example="往左8°是魔法的角度 ℡")
    private String nick_name;
}
