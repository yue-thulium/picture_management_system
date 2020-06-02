package com.management.picture.service.impl;

import com.management.picture.mapper.CollectionMapper;
import com.management.picture.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2020/6/2.
 *
 * @author Yue Wu
 */
@Service
public class CollectionServiceImpl implements CollectionService {

    @Autowired
    CollectionMapper collectionMapper;

    @Override
    public int collectionAlbum(int pm_id, int pa_id) {
        return collectionMapper.collectionAlbum(pm_id,pa_id);
    }

    @Override
    public int enCollectionAlbum(int pm_id, int pa_id) {
        return collectionMapper.enCollectionAlbum(pm_id,pa_id);
    }

    @Override
    public int ifCollectionAlbum(int pm_id, int pa_id) {
        return collectionMapper.ifCollectionAlbum(pm_id,pa_id);
    }

    @Override
    public int collectionTopic(int pm_id, int topic_id) {
        return collectionMapper.collectionTopic(pm_id,topic_id);
    }

    @Override
    public int enCollectionTopic(int pm_id, int topic_id) {
        return collectionMapper.enCollectionTopic(pm_id, topic_id);
    }

    @Override
    public int ifCollectionTopic(int pm_id, int topic_id) {
        return collectionMapper.ifCollectionTopic(pm_id, topic_id);
    }
}
