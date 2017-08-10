package com.shiro.entity.DO;

import com.shiro.entity.BO.BaseUser;
import com.shiro.entity.DTO.UserDTO;

import java.util.Date;

/**
 * Created by wuyiming on 2017/8/2.
 */
public class UserDO extends UserDTO{
    //id
    private String id;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
