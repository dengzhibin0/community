package com.nowcode.community.service;

import com.nowcode.community.dao.MessageMapper;
import com.nowcode.community.entity.Message;
import com.nowcode.community.util.SensitiveFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/29 19:44
 */
@Service
public class MessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    public List<Message> findConversations(int userId, int offset, int limit) {
        return messageMapper.selectConversations(userId, offset, limit);
    }

    public int findConversationCount(int userId) {
        return messageMapper.selectConversationCount(userId);
    }

    public List<Message> findLetters(String conversationId, int offset, int limit) {
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    public int findLetterCount(String conversationId) {
        return messageMapper.selectLetterCount(conversationId);
    }

    public int findLetterUnreadCount(int userId, String conversationId) {
        return messageMapper.selectLetterUnreadCount(userId, conversationId);
    }

    // 添加私信
    public int addMessage(Message message) {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
        message.setContent(sensitiveFilter.filter(message.getContent()));
        return messageMapper.insertMessage(message);
    }

    // 设置已读
    public int readMessage(List<Integer> ids) {
        return messageMapper.updateStatus(ids, 1);
    }

    // 查询某个主题下最新的通知
    public Message findLatestNotice(int userId,String topic){
        return messageMapper.selectLatestNotice(userId,topic);
    }

    // 查询某个主题所包含的通知的数量
    public int findNoticeCount(int userId,String topic){
        return messageMapper.selectNoticeCount(userId,topic);
    }

    // 未读通知数量
    public int findNoticeUnreadCount(int userId,String topic){
        return messageMapper.selectNoticeUnreadCount(userId,topic);
    }

    // 查询某类通知的列表
    public List<Message> findNotices(int userId, String topic, int offset, int limit) {
        return messageMapper.selectNotices(userId, topic, offset, limit);
    }
}
