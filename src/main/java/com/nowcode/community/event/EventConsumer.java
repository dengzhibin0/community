package com.nowcode.community.event;

import com.alibaba.fastjson.JSONObject;
import com.nowcode.community.entity.DiscussPost;
import com.nowcode.community.entity.Event;
import com.nowcode.community.entity.Message;
import com.nowcode.community.service.DiscussPostService;
import com.nowcode.community.service.ElasticSearchService;
import com.nowcode.community.service.MessageService;
import com.nowcode.community.util.CommunityConstant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/7/6 19:54
 */

@Component
public class EventConsumer implements CommunityConstant {

    private static final Logger logger= LoggerFactory.getLogger(EventConsumer.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private ElasticSearchService elasticSearchService;

    // 一个方法处理三个主题
    @KafkaListener(topics = {TOPIC_COMMENT,TOPIC_LIKE,TOPIC_FOLLOW})
    public void handleCommentMessage(ConsumerRecord record){
        if(record==null||record.value()==null){
            logger.error("消息的内容为空！");
        }

        // 将JSON格式的事件解析
        Event event= JSONObject.parseObject(record.value().toString(),Event.class);
        if(event==null){
            logger.error("消息格式错误！");
            return;
        }

        // 发送站内通知，构造一个message对象
        Message message=new Message();
        message.setFromId(SYSTEM_USER_ID);   // 这里的SYSTEM_USER_ID是服务器
        message.setToId(event.getEntityUserId());   // 给谁发通知
        message.setConversationId(event.getTopic());  // 会话id，我们这里存放消息的类型
//        message.setStatus(0);   // 数据库默认为0，这里不用设置
        message.setCreateTime(new Date());  // 创建时间
        Map<String,Object> content=new HashMap<>();
        content.put("userId",event.getUserId());   // 触发事件的用户的id，比如我评论了你，这个id就是我的id
        content.put("entityType",event.getEntityType());  // 实体的类型，该实体是帖子还是评论
        content.put("entityId",event.getEntityId());  // 实体的id，用于展示详情

        if(!event.getData().isEmpty()){
            for (Map.Entry<String, Object> entry : event.getData().entrySet()) {
                content.put(entry.getKey(),entry.getValue());
            }
        }

        // 存放内容
        message.setContent(JSONObject.toJSONString(content));

        // 将消息存入数据库
        messageService.addMessage(message);

    }

    // 消费发帖事件
    @KafkaListener(topics = {TOPIC_PUBLISH})
    public void handlePublishMessage(ConsumerRecord record){
        if (record == null || record.value() == null) {
            logger.error("消息的内容为空!");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(), Event.class);
        if (event == null) {
            logger.error("消息格式错误!");
            return;
        }

        DiscussPost post = discussPostService.findDiscussPostById(event.getEntityId());
        elasticSearchService.saveDiscussPost(post);
    }
}
