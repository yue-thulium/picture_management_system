package com.management.picture.model.result;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Created on 2020/5/24.
 *
 * @author Yue Wu
 */
@Data
@Component
@ApiModel(value = "基础返回对象" ,description = "请求状态、状态码以及携带的返回信息")
public class ResultModel {
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";
    @ApiModelProperty(value = "请求返回状态",example="success&fail")
    private String result;
    @ApiModelProperty(value = "请求返回码",example="200")
    private int code;
    @ApiModelProperty(value = "请求返回信息（部分接口）",example="message")
    private String message;

    public void setValue(String res,int cod,String mess) {
        this.result = res;
        this.code = cod;
        this.message = mess;
    }

    public void setValue(String res,int cod) {
        this.result = res;
        this.code = cod;
    }
}
