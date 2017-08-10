package com.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import com.shiro.common.pojo.Result;
import com.shiro.entity.DTO.UserDTO;
import com.shiro.service.AuthService;
import com.shiro.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by wuyiming on 2017/8/3.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    /**
     * 新建用户
     * @param userDTO
     * @return
     */
    @RequestMapping(value="/new",method= RequestMethod.POST)
    public JSONObject saveUserInfo(@Valid @RequestBody UserDTO userDTO){
        if (userDTO == null){
            return Result.fail("用户信息为空");
        }
        return userService.saveUserInfo(userDTO);
    }

    /**
     * 查看用户信息
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    public JSONObject getUserInfo(@PathVariable String id){
        if (StringUtils.isBlank(id)){
            return Result.fail("id为空");
        }
        return userService.getUserById(id);
    }

    /**
     * 查看用户信息
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}/detail",method=RequestMethod.GET)
    public JSONObject getUserInfoDetail(@PathVariable String id){
        if (StringUtils.isBlank(id)){
            return Result.fail("id为空");
        }
        return userService.getUserDetailById(id);
    }

    /**
     * 更新用户信息
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}",method=RequestMethod.PUT)
    public JSONObject updateUserInfo(@PathVariable String id){
        if (StringUtils.isBlank(id)){
            return Result.fail("id为空");
        }
        return null;
    }
}
