package com.management.picture.controller;

import com.management.picture.model.result.ResultModel;
import com.management.picture.util.WebSocket;
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

/**
 * Created on 2020/6/4.
 *
 * @author Yue Wu
 */
@RestController
@Api(tags = "消息推送相关接口")
public class WebSocketController {

    @Autowired
    private WebSocket webSocket;

    @Autowired
    private ResultModel resultModel;

    @GetMapping("/sendAllWebSocket/{message}")
    @ApiOperation("群发消息接口")
    @ApiImplicitParam(name = "message", value = "群发的消息内容", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel test(@PathVariable String message) {
        webSocket.sendAllMessage(message);
        resultModel.setValue(ResultModel.SUCCESS,200,"发送成功");
        return resultModel;
    }

    @GetMapping("/sendOneWebSocket/{message}/{username}")
    @ApiOperation("私信发送接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "message", value = "群发的消息内容", required = true),
            @ApiImplicitParam(name = "username", value = "被发送的用户名", required = true)
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel sendOneWebSocket(@PathVariable("message") String message,@PathVariable("username") String username) {

        if (WebSocket.getSessionPool().containsKey(username)) {
            resultModel.setValue(ResultModel.SUCCESS,200,"发送成功");
            webSocket.sendOneMessage(username,message);
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"发送离线消息成功");
        }
        return resultModel;
    }

    @GetMapping("/getOnlineNumber")
    @ApiOperation("获取当前在线人数接口")
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel getOnlinePersion() {
        resultModel.setValue(ResultModel.SUCCESS,200,String.valueOf(WebSocket.getWebSockets().size()));
        return resultModel;
    }
}
