package com.nowcode.community.dao;

import com.nowcode.community.entity.LoginTicket;
import org.apache.ibatis.annotations.*;

/**
 * @author 邓志斌
 * @version 1.0
 * @date 2021/6/23 19:48
 */
@Mapper
@Deprecated  // Deprecated表示这个主键不推荐使用
public interface LoginTicketMapper {
    @Insert({
            "insert into login_ticket(user_id,ticket,status,expired) ",
            "values(#{userId},#{ticket},#{status},#{expired})"
    })
    @Options(useGeneratedKeys = true,keyProperty = "id")  // 设置自动生成主键
    int insertLoginTicket(LoginTicket loginTicket);

    @Select({
            "select id,user_id,ticket,status,expired ",
            "from login_ticket where ticket=#{ticket} "
    })
    LoginTicket selectByTicket(String ticket);

    // 演示if的用法
    @Update({
            "<script>",
            "update login_ticket set status=#{status} where ticket=#{ticket} ",
            "<if test=\"ticket!=null\"> ",
            "and 1=1 ",
            "</if> ",
            "</script>"
    })
    int updateStatus(String ticket,int status);
}
