package com.management.picture.mapper;

import com.management.picture.model.body.MessageModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created on 2020/6/5.
 *
 * @author Yue Wu
 */
@Mapper
public interface MessageMapper {

    /**
     * 获取当前用户的未读群发消息
     */
    List<MessageModel> getNoReadAllMess(int pm_id);

    /**
     * 获取当前用户已读的群发消息
     */
    List<MessageModel> getReadAllMess(int pm_id);

    /**
     * 获取当前用户的未读私信
     */

    List<MessageModel> getNoReadPriMess(int pm_id);

    /**
     * 获取当前用户的已读私信
     */
    List<MessageModel> getReadPriMess(int pm_id);

    /**
     * 查看自己发过的全部信息
     */
    List<MessageModel> getMyMess(int pm_id);

    /**
     * 获取当前用户隐藏的消息
     */
    List<MessageModel> getHiddenMess(int pm_id);

    /**
     * 发布一条消息
     */
    int sendMess(int pm_id,String message_content,int message_to);

    /**
     * 非发布用户删除（隐藏）一条消息
     */
    int hiddenMess(int pm_id,int message_id);

    /**
     * 将消息标为已读
     */
    int makeMessRead(int pm_id,int message_id);

    /**
     * 发布者删除自己发布过的消息
     */
    int deleteSelfMess(int pm_id,int message_id);

    /**
     * 获取被发送用户的用户名
     */
    String getUsername(int pm_id);

    /**
     * 获取用户未读信息条数
     */
    int getCountMessNeedRead(int id);

    /**
     * 批量已读
     */
    int batchReadMessage(@Param("messages") List<MessageModel> messages,@Param("id") String id);

    /**
     * 批量非发布用户删除
     */
    int batchHiddenMessage(@Param("messages") List<MessageModel> messages,@Param("id") String id);

    /**
     * 批量发布用户删除自己发布的消息
     */
    int batchDeleteMessage(@Param("messages") List<MessageModel> messages,@Param("id") String id);
}
