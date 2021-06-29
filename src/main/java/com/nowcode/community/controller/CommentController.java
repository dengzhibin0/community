package com.nowcode.community.controller;

import com.nowcode.community.entity.Comment;
import com.nowcode.community.service.CommentService;
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
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;


    // 增加评论（回帖）
    @RequestMapping(path = "/add/{discussPostId}",method = RequestMethod.POST)
    public String addComment(@PathVariable("discussPostId") int discussPostId, Comment comment){

        // 补充实体数据
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());

        // 添加评论
        commentService.addComment(comment);

        // 重定向到当前帖子详情界面
        return "redirect:/discuss/detail/"+discussPostId;
    }
}
