package com.nowcode.community;

import com.nowcode.community.dao.DiscussPostMapper;
import com.nowcode.community.dao.UserMapper;
import com.nowcode.community.entity.DiscussPost;
import com.nowcode.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/19 20:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class MapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void testSelectUser(){
        User user=userMapper.selectById(101);
        System.out.println(user);
    }

    @Test
    public void testSelectPosts(){
        List<DiscussPost> list=discussPostMapper.selectDiscussPosts(149,0,10);
        for (DiscussPost discussPost : list) {
            System.out.println(discussPost);
        }

        int rows=discussPostMapper.selectDiscussPostRows(149);
        System.out.println(rows);
    }
}
