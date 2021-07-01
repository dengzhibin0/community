package com.nowcode.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/7/1 21:32
 * AOP Demo
 */

//@Component
//@Aspect
public class AlphaAspect {

    // 切入点表达式
    // 定义切入点
    @Pointcut("execution(* com.nowcode.community.service.*.*(..))")
    public void pointCut(){}

    // 前置通知
    @Before("pointCut()")
    public void myBefore(JoinPoint joinPoint){
        System.out.print("前置通知：模拟执行权限检查...,");
        System.out.print("目标类是："+joinPoint.getTarget());
        System.out.println("，被植入增强处理的目标方法为："+joinPoint.getSignature().getName());
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
