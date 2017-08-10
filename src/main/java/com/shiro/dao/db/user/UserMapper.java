package com.shiro.dao.db.user;

import com.shiro.entity.DO.UserDO;
import org.springframework.stereotype.Repository;

/**
 * Created by wuyiming on 2017/8/3.
 */
@Repository
public interface UserMapper {
    UserDO getUserById(String id);

    int insertUser(UserDO userDO);

    UserDO getUserByAccount(String account);
}
