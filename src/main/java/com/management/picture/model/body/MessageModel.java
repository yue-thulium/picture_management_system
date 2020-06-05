package com.management.picture.model.body;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created on 2020/6/5.
 *
 * @author Yue Wu
 */
@Data
@ApiModel(value = "消息对象",description = "消息返回对象")
public class MessageModel {
    @ApiModelProperty(value = "消息ID")
    private int id;
    @ApiModelProperty(value = "发布者ID")
    private int pm_id;
    @ApiModelProperty(value = "发布者昵称")
    private String nick_name;
    @ApiModelProperty(value = "发布者头像")
    private String icon;
    @ApiModelProperty(value = "消息内容")
    private String message_content;
    @ApiModelProperty(value = "消息发布时间D")
    private Date message_time;
    @ApiModelProperty(value = "消息类型【群发 or 私信】")
    private String message_type;
}
