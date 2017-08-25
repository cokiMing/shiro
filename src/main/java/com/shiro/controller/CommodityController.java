package com.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import com.shiro.common.pojo.Result;
import com.shiro.entity.DO.Commodity;
import com.shiro.service.CommodityService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wuyiming on 2017/8/24.
 */
@RestController
@RequestMapping("/commodity")
public class CommodityController extends AbstractController{

    @Autowired
    private CommodityService commodityService;

    /**
     * 新建一个商品
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public Result saveCommodity(@RequestBody JSONObject jsonObject){
        Commodity commodity = packageCommodity(jsonObject);
        return commodityService.saveCommodity(commodity);
    }

    /**
     * 查看所有商品
     * @return
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public Result getAllCommodity(){
        return commodityService.getAllCommodity();
    }

    /**
     * 根据id查看商品
     * @param commodityId
     * @return
     */
    @RequestMapping(value = "/{commodityId}",method = RequestMethod.GET)
    public Result getCommdityById(@PathVariable String commodityId){
        if (StringUtils.isBlank(commodityId)){
            return Result.fail("id为空");
        }
        return commodityService.getCommodityById(commodityId);
    }

    /**
     * 根据种类查看商品
     * @param commodityId
     * @return
     */
    @RequestMapping(value = "/typeId/{commodityId}",method = RequestMethod.GET)
    public Result getCommdityByType(@PathVariable String commodityId){
        if (StringUtils.isBlank(commodityId)){
            return Result.fail("id为空");
        }
        return commodityService.getCommodityByType(commodityId);
    }

    /**
     * 更新商品信息
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public Result updateCommodity(@RequestBody JSONObject jsonObject){
        Commodity commodity = packageCommodity(jsonObject);
        return commodityService.updateCommodityById(commodity);
    }

    /**
     * 根据id删除商品
     * @param commodityId
     * @return
     */
    @RequestMapping(value = "/{commodityId}",method = RequestMethod.DELETE)
    public Result deleteCommodity(@PathVariable String commodityId){
        if (StringUtils.isBlank(commodityId)){
            return Result.fail("id为空");
        }
        return commodityService.deleteCommodityById(commodityId);
    }

    /**
     * 调整商品库存数量
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/addCommodityNum",method = RequestMethod.PUT)
    public Result addCommodityNum(@RequestBody JSONObject jsonObject){
        int putNum = jsonObject.getIntValue("num");
        String commodityId = jsonObject.getString("commodityId");
        if (commodityId == null){
            return Result.fail("id为空");
        }
        return commodityService.addCommodityByNum(putNum,commodityId);
    }

    private Commodity packageCommodity(JSONObject jsonObject){
        Commodity commodity = new Commodity();
        commodity.setCommodityId(jsonObject.getString("commodityId"));
        commodity.setTypeId(jsonObject.getString("typeId"));
        commodity.setCategoryId(jsonObject.getString("categoryId"));
        commodity.setName(jsonObject.getString("name"));
        commodity.setDescription(jsonObject.getString("description"));
        commodity.setPics(jsonObject.getString("pics"));
        commodity.setUnit(jsonObject.getString("unit"));
        commodity.setPrize(jsonObject.getDouble("prize"));
        commodity.setNum(jsonObject.getInteger("num"));
        return commodity;
    }
}
