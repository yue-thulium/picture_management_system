package com.management.picture.model.body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created on 2020/6/2.
 *
 * @author Yue Wu
 */
@Data
@ApiModel(value = "评论对象",description = "评论返回对象")
public class CommentModel {
    @ApiModelProperty(value = "发布者")
    private String username;
    @ApiModelProperty(value = "发布者")
    private String nick_name;
    @ApiModelProperty(value = "发布者头像")
    private String icon;
    @ApiModelProperty(value = "目标ID（图册图片或话题）")
    private int target_id;
    @ApiModelProperty(value = "话题内容")
    private String content;
    @ApiModelProperty(value = "创建时间")
    private Date comment_time;
}
