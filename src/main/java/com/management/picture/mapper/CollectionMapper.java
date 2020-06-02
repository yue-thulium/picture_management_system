package com.management.picture.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * Created on 2020/6/2.
 *
 * @author Yue Wu
 */
@Mapper
public interface CollectionMapper {

    /**
     * 图册添加收藏功能
     */
    int collectionAlbum(int pm_id,int pa_id);

    /**
     * 图册删除收藏功能
     */
    int enCollectionAlbum(int pm_id,int pa_id);

    /**
     * 图册获取当前用户是否已经收藏当前显示的这个图片
     */
    int ifCollectionAlbum(int pm_id,int pa_id);

    /**
     * 话题添加收藏功能
     */
    int collectionTopic(int pm_id,int topic_id);

    /**
     * 话题删除收藏功能
     */
    int enCollectionTopic(int pm_id,int topic_id);

    /**
     * 话题获取当前用户是否已经收藏当前显示的这个话题
     */
    int ifCollectionTopic(int pm_id,int topic_id);
}
