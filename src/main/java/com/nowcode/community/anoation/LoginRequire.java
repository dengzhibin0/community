package com.nowcode.community.anoation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/25 20:59
 * 自定义注解
 * 作用于方法
 * 运行时有效
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequire {
}
