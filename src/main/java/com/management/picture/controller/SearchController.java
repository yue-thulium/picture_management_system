package com.management.picture.controller;

import com.alibaba.fastjson.JSON;
import com.management.picture.model.Tag;
import com.management.picture.model.body.PictureAlbum;
import com.management.picture.service.SearchService;
import io.swagger.annotations.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created on 2020/6/5.
 *
 * @author Yue Wu
 */
@RestController
@Api(tags = "搜索查询接口")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 标签查询接口
     *
     * @param tags 标签集合 示例：[{"id":1,"tag_name":"涩图1"},{"id":6,"tag_name":"涩图5"}]
     * @param pageNumber 页数
     * @return
     */
    @PostMapping("/getAlbumByTags")
    @ApiOperation("标签查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNumber", value = "页数", required = true),
            @ApiImplicitParam(name = "tags", value = "标签集合", required = true)
    })
    @RequiresRoles(logical = Logical.OR, value = {"user","admin"})
    public List<PictureAlbum> getAlbumByTags(@RequestParam("tags") String tags,@RequestParam("pageNumber") int pageNumber) {
        return searchService.getAlbumByTags(JSON.parseArray(tags,Tag.class),pageNumber - 6);
    }
}
