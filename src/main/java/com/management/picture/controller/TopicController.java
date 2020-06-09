package com.management.picture.controller;

import com.alibaba.fastjson.JSON;
import com.management.picture.model.Tag;
import com.management.picture.model.body.Topic;
import com.management.picture.model.result.ResultListModel;
import com.management.picture.model.result.ResultModel;
import com.management.picture.service.TopicService;
import com.management.picture.util.FastdfsUtils;
import com.management.picture.util.JWTUtil;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Created on 2020/6/3.
 *
 * @author Yue Wu
 */
@RestController
@Api(tags = "话题相关接口")
public class TopicController {

    @Autowired
    private ResultModel resultModel;

    @Autowired
    private FastdfsUtils fastdfsUtils;

    @Autowired
    private ResultListModel resultListModel;

    @Autowired
    private TopicService topicService;

    /**
     * 话题添加接口
     *
     * @param token 凭证
     * @param tittle 标题
     * @param file 图片
     * @param tagList 分类标签 示例： [{"id":1,"tag_name":"涩图1"},{"id":3,"tag_name":"涩图5"}]
     * @param text 内容
     * @return
     * @throws IOException
     */
    @PostMapping("/releaseTopic")
    @ApiOperation("话题添加接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "tittle", value = "标题", required = true),
            @ApiImplicitParam(name = "file", value = "图片", required = true),
            @ApiImplicitParam(name = "text", value = "话题内容", required = true),
            @ApiImplicitParam(name = "tags", value = "标签集合", required = true)
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel releaseTopic(@RequestHeader String token, @RequestParam("tittle") String tittle,
                                    @RequestParam("file") MultipartFile file, @RequestParam("tags")String tagList,
                                    @RequestParam("text")String text) throws IOException {
        String pictureAddress = fastdfsUtils.upload(file);
        Topic topic = new Topic(Integer.valueOf(JWTUtil.getUserID(token)),tittle,pictureAddress,text);
        if (topicService.releaseTopic(topic) <= 0) {
            resultModel.setValue(ResultModel.FAIL,400,"发布失败");
        } else {
            addTags(tagList,topic.getId());
            resultModel.setValue(ResultModel.SUCCESS,200,"发布成功");
        }
        return resultModel;
    }

    private void addTags(String tagList,int topicID) {
        List<Tag> tags = JSON.parseArray(tagList,Tag.class);
        topicService.addTopicTag(tags,topicID);
    }

    /**
     * 话题的获取接口
     *
     * @param pageNumber 页数起始点
     * @return 一轮获取的六个
     */
    @GetMapping("/getOnePageTopic/{pageNumber}")
    @ApiOperation("话题获取接口")
    @ApiImplicitParam(name = "pageNumber", value = "页数", required = true)
    @ApiResponses({
            @ApiResponse(code=40144,message="已经获取全部内容，数据库已经被翻完了"),
            @ApiResponse(code=200,message="获取成功")
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultListModel getOnePageTopic(@PathVariable int pageNumber) {
        List<Topic> topicList = topicService.getAllTopic(pageNumber - 6);
        if (topicList.size() <= 0) {
            resultListModel.setValue(ResultListModel.FAIL,40144,null);
        } else {
            resultListModel.setValue(ResultListModel.SUCCESS,200,topicList);
        }
        return resultListModel;
    }

    @GetMapping("/getMyTopic/{pageNumber}")
    @ApiOperation("用户个人话题获取接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "pageNumber", value = "页数", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=40144,message="已经获取全部内容，你也就创作了这么点啊"),
            @ApiResponse(code=200,message="获取成功")
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultListModel getMyTopic(@RequestHeader String token,@PathVariable int pageNumber) {
        List<Topic> topicList = topicService.getAllTopicByUser(JWTUtil.getUsername(token),pageNumber - 6);
        if (topicList.size() <= 0) {
            resultListModel.setValue(ResultListModel.FAIL,40144,null);
        } else {
            resultListModel.setValue(ResultListModel.SUCCESS,200,topicList);
        }
        return resultListModel;
    }

    /**
     * 用户个人图册删除接口
     *
     * @param topic_id 话题ID
     * @return
     */
    @GetMapping("/deleteTopic/{topic_id}")
    @ApiOperation("用户个人话题删除接口")
    @ApiImplicitParam(name = "pa_id", value = "图册图片的ID", required = true)
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel deleteTopic(@PathVariable int topic_id) {
        if (topicService.deleteTopic(topic_id) <= 0) {
            resultModel.setValue(ResultModel.FAIL,400,"删除失败");
        } else {
            resultModel.setValue(ResultModel.SUCCESS,200,"删除成功");
        }
        return resultModel;
    }
}
