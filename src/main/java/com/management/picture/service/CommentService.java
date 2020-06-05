package com.management.picture.service;

import com.management.picture.model.body.CommentModel;

import java.util.List;

/**
 * Created on 2020/6/2.
 *
 * @author Yue Wu
 */
public interface CommentService {

    /**
     * 获取所有当前图册图片评论
     */
    List<CommentModel> getAllAlbumComment(int pa_id,int pageNumber);

    /**
     * 添加一条图册图片的评论
     */
    int addAlbumComment(int pa_id,int pm_id,String content);

    /**
     * 获取所有当前话题评论
     */
    List<CommentModel> getAllTopicComment(int topic_id,int pageNumber);

    /**
     * 添加一条话题的评论
     */
    int addTopicComment(int topic_id,int pm_id,String content);
}
