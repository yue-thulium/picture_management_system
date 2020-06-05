package com.management.picture.service.impl;

import com.management.picture.mapper.MessageMapper;
import com.management.picture.model.body.MessageModel;
import com.management.picture.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created on 2020/6/5.
 *
 * @author Yue Wu
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Override
    public List<MessageModel> getNoReadAllMess(int pm_id) {
        return messageMapper.getNoReadAllMess(pm_id);
    }

    @Override
    public List<MessageModel> getReadAllMess(int pm_id) {
        return messageMapper.getReadAllMess(pm_id);
    }

    @Override
    public List<MessageModel> getNoReadPriMess(int pm_id) {
        return messageMapper.getNoReadPriMess(pm_id);
    }

    @Override
    public List<MessageModel> getReadPriMess(int pm_id) {
        return messageMapper.getReadPriMess(pm_id);
    }

    @Override
    public List<MessageModel> getMyMess(int pm_id) {
        return messageMapper.getMyMess(pm_id);
    }

    @Override
    public List<MessageModel> getHiddenMess(int pm_id) {
        return messageMapper.getHiddenMess(pm_id);
    }

    @Override
    public int sendMess(int pm_id, String message_content, int message_to) {
        return messageMapper.sendMess(pm_id,message_content,message_to);
    }

    @Override
    public int hiddenMess(int pm_id, int message_id) {
        return messageMapper.hiddenMess(pm_id,message_id);
    }

    @Override
    public int makeMessRead(int pm_id, int message_id) {
        return messageMapper.makeMessRead(pm_id,message_id);
    }

    @Override
    public int deleteSelfMess(int pm_id, int message_id) {
        return messageMapper.deleteSelfMess(pm_id,message_id);
    }

    @Override
    public String getUsername(int pm_id) {
        return messageMapper.getUsername(pm_id);
    }

    @Override
    public int getCountMessNeedRead(int id) {
        return messageMapper.getCountMessNeedRead(id);
    }
}
