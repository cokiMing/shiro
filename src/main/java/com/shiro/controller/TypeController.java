package com.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import com.shiro.common.constant.RemoteParam;
import com.shiro.common.pojo.Result;
import com.shiro.entity.DO.Type;
import com.shiro.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wuyiming on 2017/8/24.
 */
@RestController
@RequestMapping("/type")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 根据id查找种类
     * @param typeId
     * @return
     */
    @RequestMapping(value = "/{typeId}",method = RequestMethod.GET)
    public Result getTypeById(@PathVariable String typeId){
        return typeService.getTypeById(typeId);
    }

    /**
     * 根据品类id查找
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "/categoryId/{categoryId}",method = RequestMethod.GET)
    public Result getTypeByCategory(@PathVariable String categoryId){
        return typeService.getTypeByCategory(categoryId);
    }

    /**
     * 查找所有种类
     * @return
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public Result getAllType(){
        System.out.println(RemoteParam.API_HOST);
        return typeService.getAllType();
    }

    /**
     * 新建种类
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public Result saveType(@RequestBody JSONObject jsonObject){
        Type type = packageType(jsonObject);
        return typeService.saveType(type);
    }

    /**
     * 根据id更新种类
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/updateById",method = RequestMethod.PUT)
    public Result updateTypeById(@RequestBody JSONObject jsonObject){
        Type type = packageType(jsonObject);
        return typeService.updateTypeById(type);
    }

    /**
     * 根据typeId删除种类
     * @param typeId
     * @return
     */
    @RequestMapping(value = "/{typeId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String typeId){
        return typeService.deleteTypeOnly(typeId);
    }

    private Type packageType(JSONObject jsonObject){
        String typeId = jsonObject.getString("typeId");
        String name = jsonObject.getString("name");
        String categoryId = jsonObject.getString("categoryId");
        Type type = new Type();
        type.setTypeId(typeId);
        type.setName(name);
        type.setCategoryId(categoryId);

        return type;
    }
}
