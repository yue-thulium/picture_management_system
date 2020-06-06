package com.management.picture.service.impl;

import com.management.picture.mapper.SearchMapper;
import com.management.picture.model.Tag;
import com.management.picture.model.body.PictureAlbum;
import com.management.picture.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2020/6/5.
 *
 * @author Yue Wu
 */
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchMapper searchMapper;

    @Override
    public List<PictureAlbum> getAlbumByTags(List<Tag> tags,int pageNumber) {
        return searchMapper.getAlbumByTags(tags,pageNumber);
    }
}
