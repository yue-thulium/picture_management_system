package com.management.picture.controller;

import com.management.picture.model.Tag;
import com.management.picture.model.result.ResultModel;
import com.management.picture.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created on 2020/5/29.
 *
 * 标签Controller
 * 用于标签的修改及标签的获取
 *
 * @author Yue Wu
 */
@RestController
@Api(tags = "分类标签操作相关接口")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ResultModel resultModel;

    /**
     * 获取图册相关标签
     *
     * @return 标签集合
     */
    @GetMapping("/getAllPictureTag")
    @ApiOperation("图片相关标签获取接口")
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public List<Tag> getAllPictureTag() {
        return tagService.getAllPictureTag();
    }

    /**
     * 添加图册相关标签接口
     *
     * @param tagName 标签名
     * @return
     */
    @GetMapping("/addPictureTag/{tagName}")
    @ApiOperation("添加图片相关标签接口*管理员接口")
    @ApiImplicitParam(name = "tagName", value = "标签名", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"admin"})
    public ResultModel addPictureTag(@PathVariable String tagName) {
        if (tagService.addPictureTag(tagName) < 0) {
            resultModel.setValue(ResultModel.FAIL,400,"添加失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"添加成功");
        }
        return resultModel;
    }

    /**
     * 删除图册相关标签接口（ID删除）
     *
     * @param id 标签ID
     * @return
     */
    @GetMapping("/deletePictureTagByID/{id}")
    @ApiOperation("删除图片（ID删除）相关标签接口*管理员接口")
    @ApiImplicitParam(name = "id", value = "标签ID", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"admin"})
    public ResultModel deletePictureTagByID(@PathVariable int id) {
        if (tagService.deletePictureTagByID(id) < 0) {
            resultModel.setValue(ResultModel.FAIL,400,"删除失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"删除成功");
        }
        return resultModel;
    }

    /**
     * 删除图册相关标签接口（标签名删除）
     *
     * @param tagName 标签名
     * @return
     */
    @GetMapping("/deletePictureTagByTagName/{tagName}")
    @ApiOperation("删除图片（标签名删除）相关标签接口*管理员接口")
    @ApiImplicitParam(name = "tagName", value = "标签名", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"admin"})
    public ResultModel deletePictureTagByTagName(@PathVariable String tagName) {
        if (tagService.deletePictureTagByTagName(tagName) < 0) {
            resultModel.setValue(ResultModel.FAIL,400,"删除失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"删除成功");
        }
        return resultModel;
    }

    /**
     * 修改图片相关接口
     *
     * @param id 标签ID
     * @param tagName 标签名
     * @return
     */
    @GetMapping("/modifyPictureTag/{id}/{tagName}")
    @ApiOperation("修改图片相关标签接口*管理员接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "标签ID", required = true),
            @ApiImplicitParam(name = "tagName", value = "标签名", required = true)
    })
    @RequiresRoles(logical = Logical.OR, value = {"admin"})
    public ResultModel modifyPictureTag(@PathVariable int id, @PathVariable String tagName) {
        if (tagService.modifyPictureTag(id,tagName) < 0) {
            resultModel.setValue(ResultModel.FAIL,400,"删除失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"删除成功");
        }
        return resultModel;
    }

    /**
     * 获取话题相关标签
     *
     * @return 标签集合
     */
    @GetMapping("/getAllTopicTag")
    @ApiOperation("话题相关标签获取接口")
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public List<Tag> getAllTopicTag() {
        return tagService.getAllTopicTag();
    }

    /**
     * 添加话题相关标签接口
     *
     * @param tagName 标签名
     * @return
     */
    @GetMapping("/addTopicTag/{tagName}")
    @ApiOperation("添加话题相关标签接口*管理员接口")
    @ApiImplicitParam(name = "tagName", value = "标签名", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"admin"})
    public ResultModel addTopicTag(@PathVariable String tagName) {
        if (tagService.addTopicTag(tagName) < 0) {
            resultModel.setValue(ResultModel.FAIL,400,"添加失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"添加成功");
        }
        return resultModel;
    }

    /**
     * 删除话题相关标签接口（ID删除）
     *
     * @param id 标签ID
     * @return
     */
    @GetMapping("/deleteTopicTagByID/{id}")
    @ApiOperation("删除话题（ID删除）相关标签接口*管理员接口")
    @ApiImplicitParam(name = "id", value = "标签ID", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"admin"})
    public ResultModel deleteTopicTagByID(@PathVariable int id) {
        if (tagService.deleteTopicTagByID(id) < 0) {
            resultModel.setValue(ResultModel.FAIL,400,"删除失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"删除成功");
        }
        return resultModel;
    }

    /**
     * 删除话题相关标签接口（标签名删除）
     *
     * @param tagName 标签名
     * @return
     */
    @GetMapping("/deleteTopicTagByTagName/{tagName}")
    @ApiOperation("删除话题（标签名删除）相关标签接口*管理员接口")
    @ApiImplicitParam(name = "tagName", value = "标签名", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"admin"})
    public ResultModel deleteTopicTagByTagName(@PathVariable String tagName) {
        if (tagService.deleteTopicTagByTagName(tagName) < 0) {
            resultModel.setValue(ResultModel.FAIL,400,"删除失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"删除成功");
        }
        return resultModel;
    }

    /**
     * 修改话题相关接口
     *
     * @param id 标签ID
     * @param tagName 标签名
     * @return
     */
    @GetMapping("/modifyTopicTag/{id}/{tagName}")
    @ApiOperation("修改话题相关标签接口*管理员接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "标签ID", required = true),
            @ApiImplicitParam(name = "tagName", value = "标签名", required = true)
    })
    @RequiresRoles(logical = Logical.OR, value = {"admin"})
    public ResultModel modifyTopicTag(@PathVariable int id, @PathVariable String tagName) {
        if (tagService.modifyTopicTag(id,tagName) < 0) {
            resultModel.setValue(ResultModel.FAIL,400,"删除失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"删除成功");
        }
        return resultModel;
    }
}
