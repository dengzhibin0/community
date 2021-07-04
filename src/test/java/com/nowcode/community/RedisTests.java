package com.nowcode.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/7/3 9:06
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisTests {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testStrings(){
        String redisKey="test:count";  // 定义key
        redisTemplate.opsForValue().set(redisKey,1);   // 给某个键赋值

        System.out.println(redisTemplate.opsForValue().get(redisKey));  // 获取
        System.out.println(redisTemplate.opsForValue().increment(redisKey));  // 增加
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));  // 减少

    }

    @Test
    public void testHashs(){
        String redisKey="test:user";  // 定义key
        redisTemplate.opsForHash().put(redisKey,"id",1);   // 给某个键赋值
        redisTemplate.opsForHash().put(redisKey,"username","zhangsan");   // 给某个键赋值

        System.out.println(redisTemplate.opsForHash().get(redisKey,"id"));  // 获取
        System.out.println(redisTemplate.opsForHash().get(redisKey,"username"));  // 获取
    }

    @Test
    public void testLists(){
        String redisKey="test:ids";  // 定义key
        redisTemplate.opsForList().leftPush(redisKey,101);   // 给某个键赋值
        redisTemplate.opsForList().leftPush(redisKey,102);   // 给某个键赋值
        redisTemplate.opsForList().leftPush(redisKey,103);   // 给某个键赋值

        System.out.println(redisTemplate.opsForList().size(redisKey));  //
        System.out.println(redisTemplate.opsForList().index(redisKey,0));  //
        System.out.println(redisTemplate.opsForList().range(redisKey,0,2));
        System.out.println(redisTemplate.opsForList().rightPop(redisKey));
        System.out.println(redisTemplate.opsForList().rightPop(redisKey));
        System.out.println(redisTemplate.opsForList().rightPop(redisKey));
    }

    @Test
    public void testSets(){
        String redisKey="test:teachers";  // 定义key
        redisTemplate.opsForSet().add(redisKey,"刘备","张三","关羽","诸葛亮");   // 给某个键赋值

        System.out.println(redisTemplate.opsForSet().size(redisKey));  //
        System.out.println(redisTemplate.opsForSet().pop(redisKey));  // 获取
        System.out.println(redisTemplate.opsForSet().members(redisKey));  // 获取
    }

    @Test
    public void testSortedSets(){
        String redisKey="test:students";  // 定义key
        redisTemplate.opsForZSet().add(redisKey,"刘备",10);   // 给某个键赋值
        redisTemplate.opsForZSet().add(redisKey,"关羽",20);   // 给某个键赋值
        redisTemplate.opsForZSet().add(redisKey,"张飞",30);   // 给某个键赋值
        redisTemplate.opsForZSet().add(redisKey,"诸葛亮",40);   // 给某个键赋值


        System.out.println(redisTemplate.opsForZSet().zCard(redisKey));  //
        System.out.println(redisTemplate.opsForZSet().score(redisKey,"关羽"));
        System.out.println(redisTemplate.opsForZSet().reverseRank(redisKey,"关羽"));
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey,0,2));
    }

    @Test
    public void testKeys(){
        redisTemplate.delete("test:user");
        System.out.println(redisTemplate.hasKey("test:user"));

        // TimeUnit.SECONDS表示10是秒为单位
        redisTemplate.expire("test:students",10, TimeUnit.SECONDS);
    }

    // 多次访问同一个key
    @Test
    public void testBoundOperations(){
        String redisKey="test:count";

        //
        BoundValueOperations operations=redisTemplate.boundValueOps(redisKey);

        operations.increment();
        operations.increment();
        System.out.println(operations.get());
    }

    // 编程式事务管理
    @Test
    public void testTransactional(){
        Object object=redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String redisKey="test:tx";
                redisOperations.multi(); // 启用事务
                redisOperations.opsForSet().add(redisKey,"zhangsan");
                redisOperations.opsForSet().add(redisKey,"lisi");
                redisOperations.opsForSet().add(redisKey,"wangwu");

                System.out.println(redisOperations.opsForSet().members(redisKey));
                return redisOperations.exec();  // 关闭事务
            }
        });

        System.out.println(object);
    }
}
