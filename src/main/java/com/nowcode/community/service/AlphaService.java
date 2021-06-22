package com.nowcode.community.service;

import com.nowcode.community.dao.AlphaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/16 19:53
 */
@Service
public class AlphaService {
    @Autowired
    private AlphaDao alphaDao;

//    public AlphaService(){
//        System.out.println("实例化AlphaService");
//    }
//
//    @PostConstruct
//    public void init(){
//        System.out.println("初始化AlphaService");
//    }
//
//    @PreDestroy
//    public void destory(){
//        System.out.println("销毁AlphaService");
//    }

    public String find(){
        return alphaDao.select();
    }
}
