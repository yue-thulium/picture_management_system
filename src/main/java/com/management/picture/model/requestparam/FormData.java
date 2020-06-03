package com.management.picture.model.requestparam;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created on 2020/6/3.
 *
 * @author Yue Wu
 */
@Data
@ApiModel(value = "话题、图册图片添加对象",description = "添加对象")
public class FormData {
    @ApiModelProperty(value = "标题")
    private String tittle;
    @ApiModelProperty(value = "图片")
    private MultipartFile file;
    @ApiModelProperty(value = "标签集合",example = "[{\"id\":1,\"tag_name\":\"涩图1\"},{\"id\":6,\"tag_name\":\"涩图5\"}]")
    private String tags;
}
