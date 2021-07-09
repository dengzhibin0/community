package com.nowcode.community.controller.interceptor;

import com.nowcode.community.entity.User;
import com.nowcode.community.service.MessageService;
import com.nowcode.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/7/9 19:13
 */
@Component
public class MessageInterceptor implements HandlerInterceptor {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private MessageService messageService;

    // 在Controller之后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if(user!=null&&modelAndView!=null){
            int letterUnreadCount=messageService.findLetterUnreadCount(user.getId(),null);
            int noticeUnreadCount=messageService.findNoticeUnreadCount(user.getId(),null);
            modelAndView.addObject("allUnreadCount",letterUnreadCount+noticeUnreadCount);
        }

    }
}
