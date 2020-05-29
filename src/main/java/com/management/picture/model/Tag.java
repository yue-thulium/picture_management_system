package com.management.picture.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created on 2020/5/29.
 *
 * @author Yue Wu
 */
@Data
@ApiModel(value = "标签对象")
public class Tag {
    @ApiModelProperty(value = "标签ID")
    private int id;
    @ApiModelProperty(value = "标签名称")
    private String tag_name;
}
