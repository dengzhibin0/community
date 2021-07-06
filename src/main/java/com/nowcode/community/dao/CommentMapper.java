package com.nowcode.community.dao;

import com.nowcode.community.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/26 15:30
 */
@Mapper
public interface CommentMapper {
    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    // 通过评论类型统计数量
    int selectCountByEntity(int entityType, int entityId);

    // 增加评论
    int insertComment(Comment comment);

    // 根据id查Comment
    Comment selectCommentById(int id);
}
