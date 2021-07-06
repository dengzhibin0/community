package com.nowcode.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/7/1 22:07
 */

@Component
@Aspect
public class ServiceLogAspect {
    private static final Logger logger= LoggerFactory.getLogger(ServiceLogAspect.class);

    // 切入点表达式
    // 定义切入点
    @Pointcut("execution(* com.nowcode.community.service.*.*(..))")
    public void pointCut(){}

    // 前置通知
    @Before("pointCut()")
    public void myBefore(JoinPoint joinPoint){
        // 用户[1.2.3.4] 在[什么时间] 访问了什么功能[com.nowcode.community.service.xxx()].


        // 获取用户ip地址
        ServletRequestAttributes attributes=(ServletRequestAttributes)RequestContextHolder.getRequestAttributes();

        // 如果是消费者调用service
        if(attributes==null){
            // TODO 自行处理
            return;
        }

        HttpServletRequest request=attributes.getRequest();
        String ip=request.getRemoteHost();

        String now=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // 类名和方法名
        String target=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();

        logger.info(String.format("用户[%s]，在[%s]，访问了[%s]。",ip,now,target));
    }
}
