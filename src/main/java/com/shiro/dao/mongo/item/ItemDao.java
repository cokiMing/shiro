package com.shiro.dao.mongo.item;

import com.shiro.common.util.EntityUtil;
import com.shiro.dao.mongo.AbstractMongoTemplate;
import com.shiro.dao.redis.RedisDao;
import com.shiro.entity.DO.ItemDO;
import com.shiro.entity.DTO.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by wuyiming on 2017/8/4.
 */
@Repository
public class ItemDao extends AbstractMongoTemplate {

    @Autowired
    private RedisDao redisDao;

    private static final String COLLECTION_NAME = "item";

    public void insertItem(ItemDTO itemDTO){
        long itemId = redisDao.incr("item");
        Date date = new Date();
        ItemDO itemDO = EntityUtil.castToObject(itemDTO,ItemDO.class);
        itemDO.setUpdateTime(date);
        itemDO.setCreateTime(date);
        itemDO.setItemId(itemId);
        mongoTemplate.insert(itemDO,COLLECTION_NAME);
    }

    public ItemDO getItemById(Long itemId){
        Query query = new Query();
        Criteria criteria = Criteria.where("itemId").is(itemId);
        query.addCriteria(criteria);
        return mongoTemplate.findOne(query,ItemDO.class,COLLECTION_NAME);
    }
}
