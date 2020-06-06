package com.management.picture.service;

import com.management.picture.model.Tag;
import com.management.picture.model.body.PictureAlbum;

import java.util.List;

/**
 * Created on 2020/6/5.
 *
 * @author Yue Wu
 */
public interface SearchService {

    /**
     * 按标签进行图册查询
     */
    List<PictureAlbum> getAlbumByTags(List<Tag> tags,int pageNumber);
}
