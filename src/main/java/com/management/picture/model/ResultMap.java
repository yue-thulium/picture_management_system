package com.management.picture.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Created on 2020/4/23.
 *
 * @author Yue Wu
 */
@Component
@ApiModel(description = "返回值对象")
public class ResultMap extends HashMap<String, Object> {
    public ResultMap() {
    }

    @ApiModelProperty(value = "请求成功")
    public ResultMap success() {
        this.put("result", "success");
        return this;
    }

    @ApiModelProperty(value = "请求失败")
    public ResultMap fail() {
        this.put("result", "fail");
        return this;
    }

    @ApiModelProperty(value = "请求返回状态码")
    public ResultMap code(int code) {
        this.put("code", code);
        return this;
    }

    @ApiModelProperty(value = "请求返回携带信息")
    public ResultMap message(Object message) {
        this.put("message", message);
        return this;
    }
}
