package com.management.picture.service.impl;

import com.management.picture.mapper.PictureAlbumMapper;
import com.management.picture.model.Tag;
import com.management.picture.model.body.PictureAlbum;
import com.management.picture.service.PictureAlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Created on 2020/5/29.
 *
 * @author Yue Wu
 */
@Service
public class PictureAlbumServiceImpl implements PictureAlbumService {

    @Autowired
    private PictureAlbumMapper pictureAlbumMapper;


    @Resource(name = "createThreadPool")
    private ExecutorService threadPool;

    @Override
    public List<PictureAlbum> getOnePageAlbum(int pageNumber) {

        Future future = threadPool.submit(new Callable() {
            @Override
            public Object call() throws Exception {
                return pictureAlbumMapper.getOnePageAlbum(pageNumber);
            }
        });

        try {
            return (List<PictureAlbum>) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return  null;
    }

    @Override
    public List<PictureAlbum> getAlbumByUser(String username,int pageNumber) {
        return pictureAlbumMapper.getAlbumByUser(username,pageNumber);
    }

    @Override
    public List<PictureAlbum> getPageById(int pa_id) {
        return pictureAlbumMapper.getPageById(pa_id);
    }

    @Override
    public int releaseAlbum(PictureAlbum pictureAlbum) {
        return pictureAlbumMapper.releaseAlbum(pictureAlbum);
    }

    @Override
    public int addAlbumTag(List<Tag> tags, int album_id) {
        return pictureAlbumMapper.addAlbumTag(tags,album_id);
    }

    @Override
    public int deleteAlbum(int pa_id) {
        return pictureAlbumMapper.deleteAlbum(pa_id);
    }
}
