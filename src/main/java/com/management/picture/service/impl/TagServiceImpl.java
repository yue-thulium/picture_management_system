package com.management.picture.service.impl;

import com.management.picture.mapper.TagMapper;
import com.management.picture.model.Tag;
import com.management.picture.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2020/5/29.
 *
 * @author Yue Wu
 */
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public List<Tag> getAllPictureTag() {
        return tagMapper.getAllPictureTag();
    }

    @Override
    public int addPictureTag(String tagName) {
        return tagMapper.addPictureTag(tagName);
    }

    @Override
    public int deletePictureTagByID(int id) {
        return tagMapper.deletePictureTagByID(id);
    }

    @Override
    public int deletePictureTagByTagName(String tagName) {
        return tagMapper.deletePictureTagByTagName(tagName);
    }

    @Override
    public int modifyPictureTag(int id, String newTagName) {
        return tagMapper.modifyPictureTag(id,newTagName);
    }
}
