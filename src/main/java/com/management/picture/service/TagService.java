package com.management.picture.service;

import com.management.picture.model.Tag;

import java.util.List;

/**
 * Created on 2020/5/29.
 *
 * @author Yue Wu
 */
public interface TagService {
    /**
     * 获取所有标签
     */
    List<Tag> getAllPictureTag();

    /**
     * 添加图片相关的标签
     */
    int addPictureTag(String tagName);

    /**
     * 删除图片相关标签 —— 通过id
     */
    int deletePictureTagByID(int id);

    /**
     * 删除图片相关标签 —— 通过tagName
     */
    int deletePictureTagByTagName(String tagName);

    /**
     * 修改图片相关标签
     */
    int modifyPictureTag(int id,String newTagName);
}
