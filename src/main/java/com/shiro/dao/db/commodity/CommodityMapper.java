package com.shiro.dao.db.commodity;

import com.shiro.entity.DO.Commodity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wuyiming on 2017/8/24.
 */
@Repository
public interface CommodityMapper {
    Commodity getCommodityById(String commodityId);

    List<Commodity> getCommodityByType(String typeId);

    List<Commodity> getAllCommodity();

    int insertCommodity(Commodity commodity);

    int updateCommodityById(Commodity commodity);

    int addCommodityNum(Map<String,Object> addMap);

    int updateCommodityByType(Commodity commodity);
}
