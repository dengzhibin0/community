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
        HttpServletRequest request=attributes.getRequest();
        String ip=request.getRemoteHost();

        String now=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        // 类名和方法名
        String target=joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName();

        logger.info(String.format("用户[%s]，在[%s]，访问了[%s]。",ip,now,target));



    }

    // 后置通知
    @AfterReturning(value = "pointCut()")
    public void myAfterReturning(JoinPoint joinPoint){
        System.out.print("后置通知："+"模拟记录日志...，");
        System.out.println("被植入增强处理的目标方法为："+joinPoint.getSignature().getName());
    }

    /**
     * 环绕通知
     * @param proceedingJoinPoint
     * ProceedingJoinPoint是JoinPoint的子接口，表示可执行目标方法
     * 1.必须是Object类型的返回值
     * 2.必须接收一个参数，类型为 ProceedingJoinPoint
     * 3.必须throws Throwable
     * @return
     * @throws Throwable
     */
    @Around("pointCut()")
    public Object myAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        // 开始
        System.out.println("环绕开始：执行目标方法之前，模拟开启事务...,");

        // 执行当前目标方法
        Object object=proceedingJoinPoint.proceed();

        // 结束
        System.out.println("环绕结束：执行目标方法之后，模拟关闭事务...,");

        return object;
    }

    // 异常通知
    @AfterThrowing(value = "pointCut()",throwing = "e")
    public void myAfterThrowing(JoinPoint joinPoint,Throwable e){
        System.out.println("异常通知：出错了"+e.getMessage());
    }

    // 最终通知
    @After("pointCut()")
    public void myAfter(JoinPoint joinPoint){
        System.out.println("最终通知：模拟方法结束后释放资源。。");
    }
}
