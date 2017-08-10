package com.shiro.entity.DTO;

import com.shiro.entity.BO.BaseUser;

import javax.validation.constraints.NotNull;

/**
 * Created by wuyiming on 2017/8/3.
 */
public class UserDTO extends BaseUser {
    @NotNull
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
