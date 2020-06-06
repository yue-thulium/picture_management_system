package com.management.picture.mapper;

import com.management.picture.model.Tag;
import com.management.picture.model.body.PictureAlbum;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created on 2020/6/5.
 *
 * @author Yue Wu
 */
@Mapper
public interface SearchMapper {

    /**
     * 按标签进行图册查询
     */
    List<PictureAlbum> getAlbumByTags(@Param("tags") List<Tag> tags,@Param("pageNumber") int pageNumber);
}
