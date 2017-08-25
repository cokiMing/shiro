package com.shiro.service;

import com.alibaba.fastjson.JSONObject;
import com.shiro.common.constant.CommonParam;
import com.shiro.common.pojo.Result;
import com.shiro.dao.db.commodity.CommodityMapper;
import com.shiro.dao.db.type.TypeMapper;
import com.shiro.entity.DO.Commodity;
import com.shiro.entity.DO.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wuyiming on 2017/8/24.
 */
@Service
public class TypeService {

    @Autowired
    private TypeMapper typeMapper;
    @Autowired
    private CommodityMapper commodityMapper;

    /**
     * 保存种类
     * @param type
     * @return
     */
    @Transactional
    public Result saveType(Type type){
        Date currentTime = new Date();
        type.setCreateTime(currentTime);
        type.setUpdateTime(currentTime);

        int result = typeMapper.insertType(type);
        if (result < 1){
            return Result.error("insert fail type: "+result);
        }

        return Result.success();
    }

    /**
     * 根据id获取种类
     * @param typeId
     * @return
     */
    public Result getTypeById(String typeId){
        Type type = typeMapper.getTypeById(typeId);
        if (type == null){
            return Result.fail("未找到种类");
        }

        return Result.success(packageTypeVO(type));
    }

    /**
     * 根据品类获取种类
     * @param categoryId
     * @return
     */
    public Result getTypeByCategory(String categoryId){
        List<Type> typeList = typeMapper.getTypeByCategory(categoryId);
        return Result.success(packageTypeVOList(typeList));
    }

    /**
     * 获取所有种类
     * @return
     */
    public Result getAllType(){
        List<Type> typeList = typeMapper.getAllType();
        return Result.success(packageTypeVOList(typeList));
    }

    /**
     * 更新种类信息
     * @param type
     * @return
     */
    @Transactional
    public Result updateTypeById(Type type){
        type.setUpdateTime(new Date());
        int result = typeMapper.updateTypeById(type);
        if (result < 1){
            return Result.error("该种类不存在或已删除");
        }
        return Result.success();
    }

    /**
     * 删除种类(不删除种类下的商品)
     * @param typeId
     * @return
     */
    @Transactional
    public Result deleteTypeOnly(String typeId){
        Type type = new Type();
        type.setUpdateTime(new Date());
        type.setTypeId(typeId);
        type.setStatus(CommonParam.COMMON_DELETE);

        int result = typeMapper.updateTypeById(type);
        if (result < 1){
            return Result.error("该种类不存在或已删除");
        }

        return Result.success();
    }

    /**
     * 删除种类(删除种类下的商品)
     * @param typeId
     * @return
     */
    @Transactional
    public Result deleteTypeWithCommodities(String typeId){
        Result result = deleteTypeOnly(typeId);
        if (!result.isSuccess()){
            return result;
        }
        Commodity commodity = new Commodity();
        commodity.setTypeId(typeId);
        commodity.setStatus(CommonParam.COMMODITY_DELETE);
        commodityMapper.updateCommodityByType(commodity);

        return Result.success();
    }

    /**
     * 组装type页面model
     * @param type
     * @return
     */
    public JSONObject packageTypeVO(Type type){
        String name = type.getName();
        String categoryId = type.getCategoryId();
        String status = type.getStatus();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",name);
        jsonObject.put("categoryId",categoryId);
        jsonObject.put("status",status);
        return jsonObject;
    }

    /**
     * 批量组装type页面model
     * @param typeList
     * @return
     */
    public List<JSONObject> packageTypeVOList(List<Type> typeList){
        List<JSONObject> list = new ArrayList<>();
        for (Type type : typeList){
            JSONObject jsonObject = packageTypeVO(type);
            list.add(jsonObject);
        }
        return list;
    }
}
