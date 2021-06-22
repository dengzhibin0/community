package com.nowcode.community.dao.impl;

import com.nowcode.community.dao.AlphaDao;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/16 19:45
 */
@Repository
@Primary
public class AlphaDaoMyBatisImpl implements AlphaDao {

    @Override
    public String select() {
        return "MyBatis";
    }
}
