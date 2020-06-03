package com.management.picture.service.impl;

import com.management.picture.mapper.PictureAlbumMapper;
import com.management.picture.model.body.PictureAlbum;
import com.management.picture.service.PictureAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2020/5/29.
 *
 * @author Yue Wu
 */
@Service
public class PictureAlbumServiceImpl implements PictureAlbumService {

    @Autowired
    private PictureAlbumMapper pictureAlbumMapper;

    @Override
    public List<PictureAlbum> getOnePageAlbum(int pageNumber) {
        return pictureAlbumMapper.getOnePageAlbum(pageNumber);
    }

    @Override
    public List<PictureAlbum> getAlbumByUser(String username,int pageNumber) {
        return pictureAlbumMapper.getAlbumByUser(username,pageNumber);
    }

    @Override
    public int releaseAlbum(PictureAlbum pictureAlbum) {
        return pictureAlbumMapper.releaseAlbum(pictureAlbum);
    }

    @Override
    public int addAlbumTag(int tag_id,int album_id) {
        return pictureAlbumMapper.addAlbumTag(tag_id,album_id);
    }

    @Override
    public int deleteAlbum(int pa_id) {
        return pictureAlbumMapper.deleteAlbum(pa_id);
    }
}
