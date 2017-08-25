package com.shiro.service;

import com.alibaba.fastjson.JSONObject;
import com.shiro.common.constant.CommonParam;
import com.shiro.common.pojo.Result;
import com.shiro.common.util.QiniuUtil;
import com.shiro.dao.db.commodity.CommodityMapper;
import com.shiro.entity.DO.Commodity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by wuyiming on 2017/8/24.
 */
@Service
public class CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;

    /**
     * 保存商品
     * @param commodity
     * @return
     */
    @Transactional
    public Result saveCommodity(Commodity commodity){
        Date currentTime = new Date();
        commodity.setCreateTime(currentTime);
        commodity.setUpdateTime(currentTime);
        int result = commodityMapper.insertCommodity(commodity);
        if (result < 1){
            return Result.error("insert fail type: "+result);
        }

        return Result.success();
    }

    /**
     * 根据id获取商品
     * @param commodityId
     * @return
     */
    public Result getCommodityById(String commodityId){
        Commodity commodity = commodityMapper.getCommodityById(commodityId);
        if (commodity == null){
            return Result.fail("没有找到相关商品");
        }
        return Result.success(packageCommodityVO(commodity));
    }

    /**
     * 根据种类获取商品
     * @param typeId
     * @return
     */
    public Result getCommodityByType(String typeId){
        List<Commodity> list = commodityMapper.getCommodityByType(typeId);
        return Result.success(packageCommodityVOList(list));
    }

    /**
     * 获取所有商品
     * @return
     */
    public Result getAllCommodity(){
        List<Commodity> list = commodityMapper.getAllCommodity();
        return Result.success(packageCommodityVOList(list));
    }

    /**
     * 根据id更新商品信息
     * @param commodity
     * @return
     */
    @Transactional
    public Result updateCommodityById(Commodity commodity){
        Date currentTime = new Date();
        commodity.setUpdateTime(currentTime);
        commodity.setNum(null);
        int result = commodityMapper.updateCommodityById(commodity);
        if (result < 1){
            return Result.fail("需要更新的商品不存在或已删除");
        }

        return Result.success();
    }

    /**
     * 根据id删除商品
     * @param commodityId
     * @return
     */
    @Transactional
    public Result deleteCommodityById(String commodityId){
        Commodity commodity = new Commodity();
        commodity.setCommodityId(commodityId);
        commodity.setUpdateTime(new Date());
        commodity.setStatus(CommonParam.COMMODITY_DELETE);

        int result = commodityMapper.updateCommodityById(commodity);
        if (result < 1){
            return Result.fail("需要删除的商品不存在或已删除");
        }

        return Result.success();
    }

    /**
     * 商品入库/出库
     * @param num
     * @param commodityId
     * @return
     */
    @Transactional
    public Result addCommodityByNum(int num,String commodityId){
        Map<String,Object> addMap = new HashMap<>();
        addMap.put("num",num);
        addMap.put("commodityId",commodityId);
        addMap.put("updateTime",new Date());
        int currentNum;

        synchronized (this){
            Commodity commodityById = commodityMapper.getCommodityById(commodityId);
            Integer restNum = commodityById.getNum();
            currentNum = num + restNum;
            if (currentNum < 0){
                return Result.fail("出库数量大于库存量:"+ restNum);
            }
            int result = commodityMapper.addCommodityNum(addMap);
            if (result < 1){
                return Result.fail("商品不存在或已删除");
            }
        }

        return Result.success(currentNum);
    }

    private JSONObject packageCommodityVO(Commodity commodity){
        JSONObject jsonObject = new JSONObject();
        String commodityId = commodity.getCommodityId();
        String name = commodity.getName();
        String categoryId = commodity.getCategoryId();
        String typeId = commodity.getTypeId();
        Integer num = commodity.getNum();
        String[] pics = commodity.getPics().split(",");
        List<String> picUrls = new ArrayList<>();
        for (String pic : pics){
            pic = QiniuUtil.getFileURL(pic);
            picUrls.add(pic);
        }
        Double prize = commodity.getPrize();
        String status = commodity.getStatus();
        String unit = commodity.getUnit();

        jsonObject.put("commodityId",commodityId);
        jsonObject.put("name",name);
        jsonObject.put("categoryId",categoryId);
        jsonObject.put("typeId",typeId);
        jsonObject.put("unit",unit);
        jsonObject.put("num",num);
        jsonObject.put("pics",picUrls);
        jsonObject.put("prize",prize);
        jsonObject.put("status",status);
        return jsonObject;
    }

    private List<JSONObject> packageCommodityVOList(List<Commodity> commodityList){
        List<JSONObject> resultList = new ArrayList<>();
        for (Commodity commodity : commodityList){
            JSONObject jsonObject = packageCommodityVO(commodity);
            resultList.add(jsonObject);
        }
        return resultList;
    }
}
