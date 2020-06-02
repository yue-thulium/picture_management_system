package com.management.picture.controller;

import com.management.picture.model.body.CommentModel;
import com.management.picture.model.result.ResultModel;
import com.management.picture.service.CommentService;
import com.management.picture.util.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2020/6/2.
 *
 * 评论添加、获取操作相关接口
 *
 * @author Yue Wu
 */
@RestController
@Api(tags = "评论添加、获取操作相关接口")
public class CommentController {

    @Autowired
    private ResultModel resultModel;

    @Autowired
    private CommentService commentService;

    /**
     * 图册评论获取接口
     *
     * @param pa_id 当前图册照片ID
     * @return
     */
    @GetMapping("/getAllAlbumComment/{pa_id}")
    @ApiOperation("图册评论获取接口")
    @ApiImplicitParam(name = "pa_id", value = "当前图册照片ID", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public List<CommentModel> getAllAlbumComment(@PathVariable int pa_id) {
        return commentService.getAllAlbumComment(pa_id);
    }

    /**
     * 图册评论添加接口
     *
     * @param token 凭证
     * @param pa_id 图册图片ID
     * @param content 评论内容
     * @return
     */
    @PostMapping("/addAlbumComment")
    @ApiOperation("图册评论添加接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "pa_id", value = "图册图片ID", required = true),
            @ApiImplicitParam(name = "content", value = "评论内容", required = true)
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel addAlbumComment(@RequestHeader String token, @RequestParam("pa_id") int pa_id,
                                       @RequestParam("content") String content) {
        if (commentService.addAlbumComment(pa_id,Integer.valueOf(JWTUtil.getUserID(token)),content) <= 0) {
            resultModel.setValue(ResultModel.FAIL,400,"网络异常，添加失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"评论成功");
        }
        return resultModel;
    }

    /**
     * 话题评论获取接口
     *
     * @param topic_id 当前话题ID
     * @return
     */
    @GetMapping("/getAllTopicComment/{topic_id}")
    @ApiOperation("话题评论获取接口")
    @ApiImplicitParam(name = "topic_id", value = "当前话题ID", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public List<CommentModel> getAllTopicComment(@PathVariable int topic_id) {
        return commentService.getAllTopicComment(topic_id);
    }

    /**
     * 话题评论添加接口
     *
     * @param token 凭证
     * @param topic_id 话题ID
     * @param content 评论内容
     * @return
     */
    @PostMapping("/addTopicComment")
    @ApiOperation("图册评论添加接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "topic_id", value = "话题ID", required = true),
            @ApiImplicitParam(name = "content", value = "评论内容", required = true)
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel addTopicComment(@RequestHeader String token, @RequestParam("topic_id") int topic_id,
                                       @RequestParam("content") String content) {
        if (commentService.addTopicComment(topic_id,Integer.valueOf(JWTUtil.getUserID(token)),content) <= 0) {
            resultModel.setValue(ResultModel.FAIL,400,"网络异常，添加失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"评论成功");
        }
        return resultModel;
    }
}
