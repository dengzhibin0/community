package com.nowcode.community.controller.interceptor;

import com.nowcode.community.anoation.LoginRequire;
import com.nowcode.community.service.UserService;
import com.nowcode.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/25 21:01
 */
@Component
public class LoginRequireInterceptor implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    // 在Controller之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if(handler instanceof HandlerMethod){
            HandlerMethod handlerMethod=(HandlerMethod) handler;
            Method method=handlerMethod.getMethod();
            LoginRequire loginRequire=method.getAnnotation(LoginRequire.class);
            // 如果该方法有这个注解，但用户又没登录，说明非法访问，重定向到登录界面
            if(loginRequire!=null&&hostHolder.getUser()==null){
                // 重定向
                response.sendRedirect(request.getContextPath() + "/login");
                return false;
            }
        }
        return true;
    }
}
