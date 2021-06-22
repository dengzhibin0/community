package com.nowcode.community;

import com.nowcode.community.util.MailClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.xml.transform.sax.TemplatesHandler;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/22 19:31
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MailTest {
    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void tetTextMail(){
        mailClient.sendMail("1830601430@qq.com","TEST","Welcome.");
    }

    @Test
    public void testHtmlMail(){
        Context context=new Context();
        context.setVariable("username","sunday");

        String content=templateEngine.process("/mail/demo",context);
        System.out.println(content);
        mailClient.sendMail("1830601430@qq.com","HTML",content);
    }



}
