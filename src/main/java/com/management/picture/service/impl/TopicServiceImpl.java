package com.management.picture.service.impl;

import com.management.picture.mapper.TopicMapper;
import com.management.picture.model.Tag;
import com.management.picture.model.body.Topic;
import com.management.picture.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2020/6/3.
 *
 * @author Yue Wu
 */
@Service
public class TopicServiceImpl implements TopicService {

    @Autowired
    TopicMapper topicMapper;

    @Override
    public List<Topic> getAllTopic(int pageNumber) {
        return topicMapper.getAllTopic(pageNumber);
    }

    @Override
    public List<Topic> getAllTopicByUser(String username,int pageNumber) {
        return topicMapper.getAllTopicByUser(username,pageNumber);
    }

    @Override
    public int releaseTopic(Topic topic) {
        return topicMapper.releaseTopic(topic);
    }

    @Override
    public int addTopicTag(List<Tag> tags, int topic_id) {
        return topicMapper.addTopicTag(tags,topic_id);
    }

    @Override
    public int deleteTopic(int topic_id) {
        return topicMapper.deleteTopic(topic_id);
    }

}
