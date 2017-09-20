package com.shiro.service;

import com.alibaba.fastjson.JSONObject;
import com.shiro.common.pojo.Result;
import com.shiro.dao.db.man.ManMapper;
import com.shiro.entity.BO.Man;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by wuyiming on 2017/9/18.
 */
@Service
public class ManService {
    @Autowired
    private ManMapper manMapper;

    public Result insertMan(Man man) {
        Date current = new Date();
        man.setCreateTime(current);
        man.setUpdateTime(current);
        man.setAlive(true);

        manMapper.insert(man);
        return Result.success();
    }

    public Result findAll() {
        List<Man> all = manMapper.findAll();
        return Result.success(all);
    }

    public Result findById(String id) {
        Man man = manMapper.selectByPrimaryKey(id);
        return Result.success(man);
    }

    public Result updateByPrimaryKey(Man man) {
        int i = manMapper.updateByPrimaryKeySelective(man);
        return Result.success();
    }

    public Result deleteById(String id) {
        int i = manMapper.deleteByPrimaryKey(id);
        return Result.success();
    }

    public Result findByModel(JSONObject jsonObject) {
        List<Man> men = manMapper.selectByModel(jsonObject);
        return Result.success(men);
    }
}
