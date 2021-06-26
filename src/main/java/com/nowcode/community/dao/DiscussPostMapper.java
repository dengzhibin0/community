package com.nowcode.community.dao;

import com.nowcode.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/20 19:26
 */
@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    // @Param注解用于给参数取别名,
    // 如果只有一个参数,并且在<if>里使用,则必须加别名.
    int selectDiscussPostRows(@Param("userId") int userId);

    // 插入帖子
    void insertDiscussPost(DiscussPost discussPost);

    // 查看帖子
    DiscussPost selectDiscussPostById(int id);
}
