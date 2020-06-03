package com.management.picture.service;

import com.management.picture.model.body.Topic;

import java.util.List;

/**
 * Created on 2020/6/3.
 *
 * @author Yue Wu
 */
public interface TopicService {

    /**
     * 查询话题（分页查询，实现无限加载）
     */
    List<Topic> getAllTopic(int pageNumber);

    /**
     * 查询话题（用作作者对自己话题的查询）
     */
    List<Topic> getAllTopicByUser(String username,int pageNumber);

    /**
     * 创建话题
     */
    int releaseTopic(Topic topic);

    /**
     * 添加标签
     */
    int addTopicTag(int tag_id,int topic_id);

    /**
     * 删除话题（ID删除法）
     */
    int deleteTopic(int topic_id);
}
