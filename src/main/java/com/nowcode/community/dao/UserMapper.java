package com.nowcode.community.dao;

import com.nowcode.community.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/19 15:46
 */
@Mapper
public interface UserMapper {
    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);
}
