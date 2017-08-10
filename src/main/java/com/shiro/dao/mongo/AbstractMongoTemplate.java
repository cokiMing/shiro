package com.shiro.dao.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by wuyiming on 2017/8/4.
 */
public class AbstractMongoTemplate {
    @Autowired
    protected MongoTemplate mongoTemplate;
}
