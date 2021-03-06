package com.shiro.service;

import com.shiro.common.pojo.Result;
import com.shiro.common.util.EntityUtil;
import com.shiro.common.util.Md5Util;
import com.shiro.common.util.KeyUtils;
import com.shiro.dao.db.user.UserMapper;
import com.shiro.entity.DO.UserDO;
import com.shiro.entity.DTO.UserDTO;
import com.shiro.entity.VO.UserVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 账户信息管理
 * Created by wuyiming on 2017/8/2.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 根据账户获取用户对象
     * @param id
     * @return
     */
    public Result getUserById(String id){
        Result result = getUserDetailById(id);
        if (result.isSuccess()){
            UserDO userDO = result.getObject("content",UserDO.class);
            UserVO userVO = EntityUtil.castToObject(userDO,UserVO.class);
            return Result.success(userVO);
        }

        return result;
    }

    /**
     * 根据账户获取用户对象详情
     * @param id
     * @return
     */
    public Result getUserDetailById(String id){
        UserDO userDO = userMapper.getUserById(id);
        if (userDO == null){
            return Result.fail("没有找到相关的账户信息");
        }
        return Result.success(userDO);
    }

    /**
     * 根据账号获取用户
     * @param account
     * @return
     */
    public Result getUserByAccount(String account){
        if (StringUtils.isBlank(account)){
            return Result.fail("参数为空");
        }

        UserDO userByAccount = userMapper.getUserByAccount(account);
        return Result.success(userByAccount);
    }

    /**
     * 根据id更新账号信息
     * @param userDTO
     * @param id
     * @return
     */
    @Transactional
    public Result updateUserInfo(UserDTO userDTO,String id){
        UserDO origin = userMapper.getUserById(id);
        if (origin == null){
            return Result.fail("没有找到对象");
        }

        String password = Md5Util.string2MD5(userDTO.getPassword());
        UserDO userDO = EntityUtil.castToObject(userDTO,UserDO.class);
        userDO.setId(id);
        userDO.setUpdateTime(new Date());
        userDO.setPassword(password);
        userDO.setAccount(null);

        try{
            userMapper.updateUser(userDO);
        }catch (Exception e){
            return Result.error(e.getMessage());
        }

        return Result.success();
    }

    /**
     * 新增用户
     * @param userDTO
     * @return
     */
    @Transactional
    public Result saveUserInfo(UserDTO userDTO){
        Date date = new Date();
        String id = KeyUtils.uuid();
        String password = Md5Util.string2MD5(userDTO.getPassword());

        UserDO userDO = EntityUtil.castToObject(userDTO,UserDO.class);
        userDO.setCreateTime(date);
        userDO.setUpdateTime(date);
        userDO.setId(id);
        userDO.setPassword(password);

        int result = 0;
        String message = "";
        try{
            result = userMapper.insertUser(userDO);
        }catch (DuplicateKeyException e){
            message = "账号已存在:" + result;
        }catch (Exception e){
            message = Integer.toString(result);
        }

        if (result < 1){
            return Result.error("system error type:" + message);
        }

        return Result.success();
    }
}
