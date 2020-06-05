package com.management.picture.service.impl;

import com.management.picture.mapper.CommentMapper;
import com.management.picture.model.body.CommentModel;
import com.management.picture.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2020/6/2.
 *
 * @author Yue Wu
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<CommentModel> getAllAlbumComment(int pa_id,int pageNumber) {
        return commentMapper.getAllAlbumComment(pa_id,pageNumber);
    }

    @Override
    public int addAlbumComment(int pa_id, int pm_id, String content) {
        return commentMapper.addAlbumComment(pa_id,pm_id,content);
    }

    @Override
    public List<CommentModel> getAllTopicComment(int topic_id,int pageNumber) {
        return commentMapper.getAllTopicComment(topic_id,pageNumber);
    }

    @Override
    public int addTopicComment(int topic_id, int pm_id, String content) {
        return commentMapper.addTopicComment(topic_id,pm_id,content);
    }
}
