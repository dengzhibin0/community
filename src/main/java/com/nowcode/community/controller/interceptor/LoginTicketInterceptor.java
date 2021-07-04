package com.nowcode.community.controller.interceptor;

import com.nowcode.community.entity.LoginTicket;
import com.nowcode.community.entity.User;
import com.nowcode.community.service.UserService;
import com.nowcode.community.util.CookieUtil;
import com.nowcode.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/24 20:58
 */
@Component
public class LoginTicketInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    // 在Controller之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        // 从Cookie中获取凭证
        String ticket= CookieUtil.getValue(request,"ticket");

        if(ticket!=null){
            // 查询凭证
            LoginTicket loginTicket =userService.findLoginTicket(ticket);
            // 检查凭证是否有效
            if(loginTicket !=null&& loginTicket.getStatus()==0&& loginTicket.getExpired().after(new Date())){
                // 根据凭证查询用户
                User user=userService.findUserById(loginTicket.getUserId());

                // 在本次请求持有用户，要满足多线程
                hostHolder.setUser(user);
            }
        }
        return true;
    }

    // 在Controller之后执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
        User user=hostHolder.getUser();
        if(user!=null&&modelAndView!=null){
            modelAndView.addObject("loginUser",user);
        }
    }

    // 在模板引擎之后执行
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, @Nullable Exception ex) throws Exception {
        hostHolder.clear();
    }
}
