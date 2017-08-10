package com.shiro.service;

import com.shiro.common.pojo.Result;
import com.shiro.common.util.EntityUtil;
import com.shiro.dao.mongo.item.ItemDao;
import com.shiro.entity.DO.ItemDO;
import com.shiro.entity.DTO.ItemDTO;
import com.shiro.entity.VO.ItemVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wuyiming on 2017/8/4.
 */
@Service
public class ItemService {

    private static Log log = LogFactory.getLog(ItemService.class);

    @Autowired
    private ItemDao itemDao;

    public Result insertItem(ItemDTO itemDTO){
        try{
            itemDao.insertItem(itemDTO);
        }catch (Exception e){
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }

        return Result.success();
    }

    public Result getItemById(long itemId){
        ItemDO itemDO = itemDao.getItemById(itemId);
        if (itemDO == null){
            return Result.fail("没有找到相关道具");
        }
        ItemVO itemVO = EntityUtil.castToObject(itemDO,ItemVO.class);
        return Result.success(itemVO);
    }
}
