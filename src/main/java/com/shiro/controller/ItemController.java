package com.shiro.controller;

import com.shiro.common.pojo.Result;
import com.shiro.entity.DTO.ItemDTO;
import com.shiro.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by wuyiming on 2017/8/4.
 */
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(value = "/new",method = RequestMethod.POST)
    public Result saveItem(@RequestBody ItemDTO itemDTO){
        if (itemDTO == null){
            return Result.fail("参数为空");
        }
       return itemService.insertItem(itemDTO);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result getItemById(@PathVariable long id){
        if (id <= 0){
            return Result.fail("参数异常");
        }
        return itemService.getItemById(id);
    }
}
