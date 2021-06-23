package com.nowcode.community.config;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/23 19:00
 * 生存验证码的配置信息
 */
@Configuration
public class KaptchaConfig {
    @Bean
    public Producer kaptchaProducer() {
        //进行验证码的配置
        Properties properties = new Properties();
        //宽度
        properties.setProperty("kaptcha.image.width","100");
        //高度
        properties.setProperty("kaptcha.image.height","40");
        //里面元素的大小
        properties.setProperty("kaptcha.textproducer.font.size","32");
        //颜色
        properties.setProperty("kaptcha.textproducer.font.color","0,0,0");
        //会在哪些字符串选取验证码
        properties.setProperty("kaptcha.textproducer.char.string","0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        //验证码字符个数
        properties.setProperty("kaptcha.textproducer.char.length","4");
        //验证码的干扰项
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");
        //声明配置
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;

    }
}
