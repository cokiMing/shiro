package com.shiro.entity.BO;

import javax.validation.constraints.NotNull;

/**
 * Created by wuyiming on 2017/8/2.
 */
public class BaseUser {

    @NotNull
    private String account;

    @NotNull
    private String name;

    @NotNull
    private String tel;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "BaseUser{" +
                "account='" + account + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
