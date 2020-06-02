package com.management.picture.controller;

import com.management.picture.model.result.ResultModel;
import com.management.picture.service.CollectionService;
import com.management.picture.util.JWTUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created on 2020/6/2.
 *
 * 收藏添加、获取、取消操作相关接口
 * 该接口更多使用code返回值表示含义
 *
 * @author Yue Wu
 */
@RestController
@Api(tags = "收藏添加、获取、取消操作相关接口")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private ResultModel resultModel;

    /**
     * 图册收藏添加接口
     *
     * @param token 凭证
     * @param pa_id 画册图片ID
     * @return
     */
    @GetMapping("/collectionAlbum/{pa_id}")
    @ApiOperation("图册收藏添加接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "pa_id", value = "画册图片ID", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=400,message="网络异常，添加失败"),
            @ApiResponse(code=200,message="收藏成功")
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel collectionAlbum(@RequestHeader String token, @PathVariable int pa_id) {
        if (collectionService.collectionAlbum(Integer.valueOf(JWTUtil.getUserID(token)),pa_id) <= 0) {
            resultModel.setValue(ResultModel.FAIL,400,"网络异常，添加失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"收藏成功");
        }
        return resultModel;
    }

    /**
     * 图册收藏取消接口
     *
     * @param token 凭证
     * @param pa_id 画册图片ID
     * @return
     */
    @GetMapping("/enCollectionAlbum/{pa_id}")
    @ApiOperation("图册收藏取消接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "pa_id", value = "画册图片ID", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=400,message="网络异常，取消失败"),
            @ApiResponse(code=200,message="取消收藏成功")
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel enCollectionAlbum(@RequestHeader String token, @PathVariable int pa_id) {
        if (collectionService.enCollectionAlbum(Integer.valueOf(JWTUtil.getUserID(token)),pa_id) <= 0) {
            resultModel.setValue(ResultModel.FAIL,400,"网络异常，取消失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"取消成功");
        }
        return resultModel;
    }

    /**
     * 图册获取当前用户是否已经收藏当前显示的这个图片
     *
     * @param token 凭证
     * @param pa_id 画册图片ID
     * @return
     */
    @GetMapping("/ifCollectionAlbum/{pa_id}")
    @ApiOperation("图册获取当前用户是否已经收藏当前显示的这个图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "pa_id", value = "画册图片ID", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=20011,message="当前已收藏"),
            @ApiResponse(code=200,message="当前为未收藏状态")
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel ifCollectionAlbum(@RequestHeader String token, @PathVariable int pa_id) {
        if (collectionService.ifCollectionAlbum(Integer.valueOf(JWTUtil.getUserID(token)),pa_id) > 0) {
            resultModel.setValue(ResultModel.SUCCESS,20011,"当前已收藏");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"当前为未收藏");
        }
        return resultModel;
    }

    /**
     * 话题收藏添加接口
     *
     * @param token 凭证
     * @param topic_id 话题ID
     * @return
     */
    @GetMapping("/collectionTopic/{topic_id}")
    @ApiOperation("图册收藏添加接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "topic_id", value = "话题ID", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=400,message="网络异常，添加失败"),
            @ApiResponse(code=200,message="收藏成功")
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel collectionTopic(@RequestHeader String token, @PathVariable int topic_id) {
        if (collectionService.collectionTopic(Integer.valueOf(JWTUtil.getUserID(token)),topic_id) <= 0) {
            resultModel.setValue(ResultModel.FAIL,400,"网络异常，添加失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"收藏成功");
        }
        return resultModel;
    }

    /**
     * 话题收藏取消接口
     *
     * @param token 凭证
     * @param topic_id 话题ID
     * @return
     */
    @GetMapping("/enCollectionTopic/{topic_id}")
    @ApiOperation("图册收藏取消接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "topic_id", value = "话题ID", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=400,message="网络异常，取消失败"),
            @ApiResponse(code=200,message="取消收藏成功")
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel enCollectionTopic(@RequestHeader String token, @PathVariable int topic_id) {
        if (collectionService.enCollectionTopic(Integer.valueOf(JWTUtil.getUserID(token)),topic_id) <= 0) {
            resultModel.setValue(ResultModel.FAIL,400,"网络异常，取消失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"取消成功");
        }
        return resultModel;
    }

    /**
     * 获取当前用户是否已经收藏当前显示的这个话题
     *
     * @param token 凭证
     * @param topic_id 话题ID
     * @return
     */
    @GetMapping("/ifCollectionTopic/{topic_id}")
    @ApiOperation("图册获取当前用户是否已经收藏当前显示的这个图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "topic_id", value = "话题ID", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=20011,message="当前已收藏"),
            @ApiResponse(code=200,message="当前为未收藏状态")
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel ifCollectionTopic(@RequestHeader String token, @PathVariable int topic_id) {
        if (collectionService.ifCollectionTopic(Integer.valueOf(JWTUtil.getUserID(token)),topic_id) > 0) {
            resultModel.setValue(ResultModel.SUCCESS,20011,"当前已收藏");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"当前为未收藏");
        }
        return resultModel;
    }
}
