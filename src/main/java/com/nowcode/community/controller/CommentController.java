package com.nowcode.community.controller;

import com.nowcode.community.entity.Comment;
import com.nowcode.community.entity.DiscussPost;
import com.nowcode.community.entity.Event;
import com.nowcode.community.event.EventProducer;
import com.nowcode.community.service.CommentService;
import com.nowcode.community.service.DiscussPostService;
import com.nowcode.community.util.CommunityConstant;
import com.nowcode.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/27 19:13
 */
@Controller
@RequestMapping("/comment")
public class CommentController implements CommunityConstant {
    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;

    @Autowired
    private DiscussPostService discussPostService;


    // 增加评论（回帖）
    @RequestMapping(path = "/add/{discussPostId}",method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment){

        // 补充实体数据
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());

        // 添加评论
        commentService.addComment(comment);

        // 触发评论事件
        Event event=new Event()
                .setTopic(TOPIC_COMMENT)   // 设置话题
                .setUserId(hostHolder.getUser().getId())   // 谁评论的
                .setEntityType(comment.getEntityType())   // 这个评论的类型，是对评论的回帖，还是对回帖的回帖
                .setEntityId(comment.getEntityId())  // 实体的id
                .setData("postId",discussPostId);   // 当前帖子的id

        // 给帖子评论
        if(comment.getEntityType()==ENTITY_TYPE_POST){
            DiscussPost target=discussPostService.findDiscussPostById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }else if(comment.getEntityType()==ENTITY_TYPE_COMMENT){  // 给评论的评论
            Comment target=commentService.findCommentById(comment.getEntityId());
            event.setEntityUserId(target.getUserId());
        }

        eventProducer.fireEvent(event);

        if(comment.getEntityType()==ENTITY_TYPE_POST){
            // 触发发帖事件
            event=new Event()
                    .setTopic(TOPIC_PUBLISH)
                    .setUserId(comment.getUserId())
                    .setEntityType(ENTITY_TYPE_POST)
                    .setEntityId(discussPostId);

            eventProducer.fireEvent(event);
        }


        // 重定向到当前帖子详情界面
        return "redirect:/discuss/detail/"+discussPostId;
    }
}
