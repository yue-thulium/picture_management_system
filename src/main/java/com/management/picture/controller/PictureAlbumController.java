package com.management.picture.controller;

import com.alibaba.fastjson.JSON;
import com.management.picture.model.Tag;
import com.management.picture.model.picture.PictureAlbum;
import com.management.picture.model.result.ResultListModel;
import com.management.picture.model.result.ResultModel;
import com.management.picture.service.PictureAlbumService;
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
 * Created on 2020/5/29.
 *
 * @author Yue Wu
 */
@RestController
@Api(tags = "图册相关接口")
public class PictureAlbumController {

    @Autowired
    private PictureAlbumService pictureAlbumService;

    @Autowired
    private FastdfsUtils fastdfsUtils;

    @Autowired
    private ResultModel resultModel;

    @Autowired
    private ResultListModel resultListModel;

    /**
     *  图册添加接口
     *
     * @param token 凭证
     * @param tittle 标题
     * @param file 图片
     * @param tagList 标签集合
     * @return 提示信息
     * @throws IOException
     */
    @PostMapping("/releaseAtlas")
    @ApiOperation("图册添加接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "tittle", value = "标题", required = true),
            @ApiImplicitParam(name = "file", value = "图片", required = true),
            @ApiImplicitParam(name = "tags", value = "标签集合", required = true)
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultModel releaseAtlas(@RequestHeader String token, @RequestParam("tittle") String tittle,
                                    @RequestParam("file") MultipartFile file, @RequestParam("tags")String tagList) throws IOException {
        String pictureAddress = fastdfsUtils.upload(file);
        PictureAlbum pictureAlbum = new PictureAlbum();
        pictureAlbum.setPm_id(Integer.valueOf(JWTUtil.getUserID(token)));
        pictureAlbum.setTittle(tittle);
        pictureAlbum.setPicture(pictureAddress);
        if (pictureAlbumService.releaseAlbum(pictureAlbum) <= 0){
            resultModel.setValue(ResultModel.FAIL,400,"发布失败");
        } else {
            addTags(tagList,pictureAlbum.getId());
            resultModel.setValue(ResultModel.SUCCESS,200,"发布成功");
        }
        addTags(tagList,4);
        return  resultModel;
    }

    private void addTags(String tagList,int albumID) {
        List<Tag> tags = JSON.parseArray(tagList,Tag.class);

        for (Tag tag: tags) {
            pictureAlbumService.addAlbumTag(tag.getId(),albumID);
        }
    }

    /**
     * 随便看看的图册获取接口
     *
     * @param pageNumber 页数起始点
     * @return 一轮获取的六个
     */
    @GetMapping("/getOnePageAlbum/{pageNumber}")
    @ApiOperation("图册获取接口")
    @ApiImplicitParam(name = "pageNumber", value = "页数", required = true)
    @ApiResponses({
            @ApiResponse(code=40144,message="已经获取全部内容，数据库已经被翻完了"),
            @ApiResponse(code=200,message="获取成功")
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultListModel getOnePageAlbum(@PathVariable int pageNumber) {
        List<PictureAlbum> pictureAlbumList = pictureAlbumService.getOnePageAlbum(pageNumber - 6);
        if (pictureAlbumList.size() <= 0) {
            resultListModel.setValue(ResultListModel.FAIL,40144);
        } else {
            resultListModel.setValue(ResultListModel.SUCCESS,200,pictureAlbumList);
        }
        return resultListModel;
    }

    /**
     * 个人创作图册获取接口
     *
     * @param token 凭证
     * @return 返回一轮六个
     */
    @GetMapping("/getMyAlbum/{pageNumber}")
    @ApiOperation("用户个人图册获取接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "token", value = "凭证", required = true),
            @ApiImplicitParam(name = "pageNumber", value = "页数", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=40144,message="已经获取全部内容，你也就创作了这么点啊"),
            @ApiResponse(code=200,message="获取成功")
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public ResultListModel getMyAlbum(@RequestHeader String token,@PathVariable int pageNumber) {
        List<PictureAlbum> pictureAlbumList = pictureAlbumService.getAlbumByUser(JWTUtil.getUsername(token),pageNumber);
        if (pictureAlbumList.size() <= 0) {
            resultListModel.setValue(ResultListModel.FAIL,40144);
        } else {
            resultListModel.setValue(ResultListModel.SUCCESS,200,pictureAlbumList);
        }
        return resultListModel;
    }
}
