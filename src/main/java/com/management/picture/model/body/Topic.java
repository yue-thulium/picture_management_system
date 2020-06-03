package com.management.picture.model.body;

import com.management.picture.model.Tag;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created on 2020/6/3.
 *
 * @author Yue Wu
 */
@Data
@ApiModel(value = "话题对象",description = "话题返回对象")
public class Topic {
    @ApiModelProperty(value = "标签ID")
    private int id;
    @ApiModelProperty(value = "发布者ID")
    private int pm_id;
    @ApiModelProperty(value = "发布者用户名")
    private String username;
    @ApiModelProperty(value = "发布者昵称")
    private String nick_name;
    @ApiModelProperty(value = "发布者头像")
    private String icon;
    @ApiModelProperty(value = "发布标题")
    private String tittle;
    @ApiModelProperty(value = "发布图片的地址")
    private String picture;
    @ApiModelProperty(value = "话题内容")
    private String text;
    @ApiModelProperty(value = "创建时间")
    private Date create_time;
    @ApiModelProperty(value = "收藏数")
    private int start_number;
    @ApiModelProperty(value = "评论数")
    private int comment_number;
    @ApiModelProperty(value = "分类标签（List）")
    private List<Tag> tagList;

    public Topic(int pm_id, String tittle, String picture, String text) {
        this.pm_id = pm_id;
        this.tittle = tittle;
        this.picture = picture;
        this.text = text;
    }
}
