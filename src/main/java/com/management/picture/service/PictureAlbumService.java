package com.management.picture.service;

import com.management.picture.model.Tag;
import com.management.picture.model.body.PictureAlbum;

import java.util.List;

/**
 * Created on 2020/5/29.
 *
 * @author Yue Wu
 */
public interface PictureAlbumService {

    /**
     * 查询图册（分页查询，实现无限加载）
     */
    List<PictureAlbum> getOnePageAlbum(int pageNumber);

    /**
     * 查询图册（用作作者对自己图册的查询）
     */
    List<PictureAlbum> getAlbumByUser(String username,int pageNumber);

    /**
     * 查询图册单个图片，ID获取
     */
    List<PictureAlbum> getPageById(int pa_id);

    /**
     * 创建图册
     */
    int releaseAlbum(PictureAlbum pictureAlbum);

    /**
     * 添加标签
     */
    int addAlbumTag(List<Tag> tags, int album_id);

    /**
     * 删除一个图册（id删除法）
     */
    int deleteAlbum(int pa_id);
}
