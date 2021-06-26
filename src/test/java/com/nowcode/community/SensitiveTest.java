package com.nowcode.community;

import com.nowcode.community.util.SensitiveFilter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.Socket;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/26 9:54
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class SensitiveTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    public void testSensitiveFilter(){
        String text="这里可以赌博，可以吸毒，可以开票，哈哈哈！";
        text=sensitiveFilter.filter(text);
        System.out.println(text);

    }
}
