package com.management.picture.controller;

import com.alibaba.fastjson.JSON;
import com.management.picture.model.Tag;
import com.management.picture.model.body.MessageModel;
import com.management.picture.model.result.ResultModel;
import com.management.picture.service.MessageService;
import com.management.picture.util.JWTUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 2020/6/8.
 *
 * “消息集合”参数说明：
 * [
 *      {
 *         "id": 22,
 *         "pm_id": 20,
 *         "nick_name": "mi9",
 *         "icon": "group1/M00/00/00/rBDDUl5bcO2ANCAWAAAGsa4U3Is409.png",
 *         "message_content": "测试服务器推送消息",
 *         "message_time": "2020-06-06 15:28:28",
 *         "message_type": "私聊"
 *     }
 * ]
 *
 * @author Yue Wu
 */
@RestController
@Api(tags = "消息系统批量操作接口")
public class MessageBatchController {

    @Autowired
    private ResultModel resultModel;

    @Autowired
    private MessageService messageService;

    /**
     * 批量阅读未读消息接口
     *
     * @param token 凭证
     * @param messages 消息集合
     * @return
     */
    @PostMapping("/batchReadMessage")
    @ApiOperation("批量阅读未读消息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "messages", value = "消息集合", required = true)
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel batchReadMessage(@RequestHeader String token, @RequestParam("messages") String messages) {

        if (messageService.batchReadMessage(JSON.parseArray(messages, MessageModel.class), JWTUtil.getUserID(token)) <= 0) {
            resultModel.setValue(ResultModel.FAIL,400,"网络异常");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"操作成功");
        }

        return resultModel;
    }

    /**
     * 批量删除已读消息（非发布者删除）接口
     *
     * @param token 凭证
     * @param messages 消息集合
     * @return
     */
    @PostMapping("/batchHiddenMessage")
    @ApiOperation("批量删除已读消息（非发布者删除）接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "messages", value = "消息集合", required = true)
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel batchHiddenMessage(@RequestHeader String token, @RequestParam("messages") String messages) {

        if (messageService.batchHiddenMessage(JSON.parseArray(messages, MessageModel.class), JWTUtil.getUserID(token)) <= 0) {
            resultModel.setValue(ResultModel.FAIL,400,"网络异常");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"操作成功");
        }

        return resultModel;
    }

    /**
     * 发布者批量删除自己发布的消息接口
     *
     * @param token 凭证
     * @param messages 消息集合
     * @return
     */
    @PostMapping("/batchDeleteMessage")
    @ApiOperation("发布者批量删除自己发布的消息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "messages", value = "消息集合", required = true)
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel batchDeleteMessage(@RequestHeader String token, @RequestParam("messages") String messages) {

        if (messageService.batchDeleteMessage(JSON.parseArray(messages, MessageModel.class), JWTUtil.getUserID(token)) <= 0) {
            resultModel.setValue(ResultModel.FAIL,400,"网络异常");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"操作成功");
        }

        return resultModel;
    }
}
