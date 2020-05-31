package com.management.picture.mapper;

import com.management.picture.model.Tag;
import com.management.picture.model.picture.PictureAlbum;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created on 2020/5/29.
 *
 * @author Yue Wu
 */
@Mapper
public interface PictureAlbumMapper {


    /**
     * 查询图册（分页查询，实现无限加载）
     */
    List<PictureAlbum> getOnePageAlbum(int pageNumber);

    /**
     * 查询图册（用作作者对自己图册的查询）
     */
    List<PictureAlbum> getAlbumByUser(String username,int pageNumber);

    /**
     * 创建图册
     */
    int releaseAlbum(PictureAlbum pictureAlbum);

    /**
     * 添加标签
     */
    int addAlbumTag(int tag_id,int album_id);
}
