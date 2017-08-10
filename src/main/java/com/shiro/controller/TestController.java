package com.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import com.shiro.common.pojo.Result;
import com.shiro.mq.ItemPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Controller
 * 所有的测试接口都放在这里
 * Created by wuyiming on 2017/8/9.
 */
@RestController
@RequestMapping("/test")
public class TestController extends AbstractController{

    @Autowired
    private ItemPublisher itemPublisher;

    /**
     * 消息队列发送测试接口
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    public Result publishMessage(@RequestBody JSONObject jsonObject){
        try{
            itemPublisher.sendMessage(jsonObject);
        }catch (Exception e){
            log.error(e.getMessage());
        }
        return Result.success();
    }
}
