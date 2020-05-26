package com.management.picture.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created on 2020/5/24.
 *
 * @author Yue Wu
 */
@Data
@Component
@ApiModel(value = "用户信息返回对象" ,description = "返回用户信息对象")
public class ResultUserInfModel {
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    @ApiModelProperty(value = "用户名",example="admin")
    private String username;
    @ApiModelProperty(value = "用户邮箱",example="admin@1222.com")
    private String email;
    @ApiModelProperty(value = "用户权限",example="['user','normal']")
    private List<String> role;
    @ApiModelProperty(value = "账户锁定状态",example="0 or 1")
    private int ban;
    @ApiModelProperty(value = "用户手机",example="17112221222")
    private String phone;

    public void setValue(String username, String email, List<String> role, int ban, String phone) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.ban = ban;
        this.phone = phone;
    }
}
