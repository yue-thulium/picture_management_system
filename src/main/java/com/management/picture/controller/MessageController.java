package com.management.picture.controller;

import com.management.picture.model.body.MessageModel;
import com.management.picture.model.result.ResultListModel;
import com.management.picture.model.result.ResultModel;
import com.management.picture.service.MessageService;
import com.management.picture.util.JWTUtil;
import com.management.picture.util.WebSocket;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created on 2020/6/5.
 *
 * @author Yue Wu
 */
@RestController
@Api(tags = "消息系统接口")
public class MessageController {

    @Autowired
    private ResultModel resultModel;

    @Autowired
    private ResultListModel resultListModel;

    @Autowired
    private MessageService messageService;

    @Autowired
    private WebSocket webSocket;

    /**
     * 获取当前用户的未读群发消息接口
     *
     * @param token 凭证
     * @return 消息集合
     */
    @GetMapping("/getNoReadAllMess")
    @ApiOperation("获取当前用户的未读群发消息接口")
    @ApiImplicitParam(name = "token", value = "凭证", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public List<MessageModel> getNoReadAllMess(@RequestHeader String token) {
        return messageService.getNoReadAllMess(Integer.valueOf(JWTUtil.getUserID(token)));
    }

    /**
     * 获取当前用户已读的群发消息接口
     *
     * @param token 凭证
     * @return 消息集合
     */
    @GetMapping("/getReadAllMess")
    @ApiOperation("获取当前用户已读的群发消息接口")
    @ApiImplicitParam(name = "token", value = "凭证", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public List<MessageModel> getReadAllMess(@RequestHeader String token) {
        return messageService.getReadAllMess(Integer.valueOf(JWTUtil.getUserID(token)));
    }

    /**
     * 获取当前用户的未读私信接口
     *
     * @param token 凭证
     * @return 消息集合
     */
    @GetMapping("/getNoReadPriMess")
    @ApiOperation("获取当前用户的未读私信接口")
    @ApiImplicitParam(name = "token", value = "凭证", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public List<MessageModel> getNoReadPriMess(@RequestHeader String token) {
        return messageService.getNoReadPriMess(Integer.valueOf(JWTUtil.getUserID(token)));
    }

    /**
     * 获取当前用户的已读私信
     *
     * @param token 凭证
     * @return 消息集合
     */
    @GetMapping("/getReadPriMess")
    @ApiOperation("获取当前用户的已读私信接口")
    @ApiImplicitParam(name = "token", value = "凭证", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public List<MessageModel> getReadPriMess(@RequestHeader String token) {
        return messageService.getReadPriMess(Integer.valueOf(JWTUtil.getUserID(token)));
    }

    /**
     * 查看自己发过的全部信息
     *
     * @param token 凭证
     * @return 消息集合
     */
    @GetMapping("/getMyMess")
    @ApiOperation("查看自己发过的全部信息接口")
    @ApiImplicitParam(name = "token", value = "凭证", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public List<MessageModel> getMyMess(@RequestHeader String token) {
        return messageService.getMyMess(Integer.valueOf(JWTUtil.getUserID(token)));
    }

    /**
     * 获取当前用户删除了的消息
     *
     * @param token 凭证
     * @return 消息集合
     */
    @GetMapping("/getHiddenMess")
    @ApiOperation("获取当前用户删除了的消息接口")
    @ApiImplicitParam(name = "token", value = "凭证", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public List<MessageModel> getHiddenMess(@RequestHeader String token) {
        return messageService.getHiddenMess(Integer.valueOf(JWTUtil.getUserID(token)));
    }

    /**
     * 发布消息（群发及私信）
     *
     * @param token 凭证
     * @param message 消息内容
     * @param message_to 消息传递对象【0：全部；其他：私信接受者ID】
     * @return
     */
    @PostMapping("/sendMess")
    @ApiOperation("发布消息（群发及私信）接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "message", value = "消息内容", required = true),
            @ApiImplicitParam(name = "message_to", value = "消息传递对象【0：全部；其他：私信接受者ID】", required = true)
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel sendMess(@RequestHeader String token,
                                @RequestParam("message") String message,@RequestParam("message_to") int message_to) {
        if (message_to == 0) {
            webSocket.sendAllMessage(message);
            messageService.sendMess(Integer.valueOf(JWTUtil.getUserID(token)),message,message_to);
            resultModel.setValue(ResultModel.SUCCESS,200,"发送成功");
        } else {
            String to_username = messageService.getUsername(message_to);
            if (to_username != null) {
                if (WebSocket.getSessionPool().containsKey(to_username)) {
                    webSocket.sendOneMessage(to_username,message);
                    messageService.sendMess(Integer.valueOf(JWTUtil.getUserID(token)),message,message_to);
                    resultModel.setValue(ResultModel.SUCCESS,200,"发送成功");
                } else {
                    messageService.sendMess(Integer.valueOf(JWTUtil.getUserID(token)),message,message_to);
                    resultModel.setValue(ResultModel.SUCCESS,200,"发送离线消息成功");
                }
            } else {
                resultModel.setValue(ResultModel.FAIL,400,"发送的用户不存在");
            }
        }
        return resultModel;
    }

    /**
     * 非发布者删除消息接口
     *
     * @param token 凭证
     * @param message_id 消息ID
     * @return
     */
    @GetMapping("/hiddenMess/{message_id}")
    @ApiOperation("非发布者删除消息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "message_id", value = "消息ID", required = true),
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel hiddenMess(@RequestHeader String token, @PathVariable int message_id) {
        if (messageService.hiddenMess(Integer.valueOf(JWTUtil.getUserID(token)),message_id) <= 0) {
            resultModel.setValue(ResultModel.FAIL,400,"检查网络");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"删除成功");
        }
        return resultModel;
    }

    /**
     * 将消息标为已读
     *
     * @param token 凭证
     * @param message_id 消息ID
     * @return
     */
    @GetMapping("/makeMessRead/{message_id}")
    @ApiOperation("将消息标为已读接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "message_id", value = "消息ID", required = true),
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel makeMessRead(@RequestHeader String token, @PathVariable int message_id) {
        if (messageService.makeMessRead(Integer.valueOf(JWTUtil.getUserID(token)),message_id) <= 0) {
            resultModel.setValue(ResultModel.FAIL,400,"检查网络");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"完成");
        }
        return resultModel;
    }

    /**
     * 发布者删除自己发布过的消息接口
     *
     * @param token 凭证
     * @param message_id 消息ID
     * @return
     */
    @GetMapping("/deleteSelfMess/{message_id}")
    @ApiOperation("发布者删除自己发布过的消息接口(该处为彻底删除)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "message_id", value = "消息ID", required = true),
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel deleteSelfMess(@RequestHeader String token, @PathVariable int message_id) {
        if (messageService.deleteSelfMess(Integer.valueOf(JWTUtil.getUserID(token)),message_id) <= 0) {
            resultModel.setValue(ResultModel.FAIL,400,"检查网络");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"完成");
        }
        return resultModel;
    }

    @GetMapping("/getCountMessNeedRead")
    @ApiOperation("未读消息（包括群发及私聊）接口")
    @ApiImplicitParam(name = "token", value = "凭证", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel getCountMessNeedRead(@RequestHeader String token) {
        resultModel.setValue(ResultModel.SUCCESS,200,
                String.valueOf(messageService.getCountMessNeedRead(Integer.valueOf(JWTUtil.getUserID(token)))));
        return resultModel;
    }
}
