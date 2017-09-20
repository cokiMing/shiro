package com.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import com.shiro.common.constant.CommonParam;
import com.shiro.common.pojo.Result;
import com.shiro.common.util.QiniuUtil;
import com.shiro.entity.BO.Man;
import com.shiro.remote.ApiUtil;
import com.shiro.service.ManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 测试Controller
 * 所有的测试接口都放在这里
 * Created by wuyiming on 2017/8/9.
 */
@RestController
@RequestMapping("/test")
public class TestController extends AbstractController{

//    @Autowired
//    private ItemPublisher itemPublisher;

    @Autowired
    private ApiUtil apiUtil;

    @Autowired
    private ManService manService;

    /**
     * 消息队列发送测试接口
     * @return
     */
//    @RequestMapping(value = "/publish",method = RequestMethod.POST)
//    public Result publishMessage(@RequestBody JSONObject jsonObject){
//        try{
//            itemPublisher.sendMessage(jsonObject);
//        }catch (Exception e){
//            log.error(e.getMessage());
//        }
//        return Result.success();
//    }

    /**
     * GoogleMap测试接口
     * @param origin
     * @param destination
     * @param mode
     * @return
     */
    @RequestMapping(value = "/googleMap/steps",method = RequestMethod.GET)
    public Result getSteps(@RequestParam("origin") String origin,
                           @RequestParam("destination") String destination,
                           @RequestParam("mode") String mode){
        String[] origins = origin.split(",");
        String[] destinations = destination.split(",");

        Double olat = Double.parseDouble(origins[0]);
        Double olng = Double.parseDouble(origins[1]);
        Double dlat = Double.parseDouble(destinations[0]);
        Double dlng = Double.parseDouble(destinations[1]);

        JSONObject result = null;
        if (mode.equals(CommonParam.MODE_BICYCLING)){
            result = apiUtil.getBicyclingStepsByGoogleMap(olat, olng, dlat, dlng);
        }
        if (mode.equals(CommonParam.MODE_DRIVING)){
            result = apiUtil.getDrivingStepsByGoogleMap(olat, olng, dlat, dlng);
        }
        if (result != null){
            return Result.success(result);
        }
        return Result.fail("获取失败");
    }

    @RequestMapping(value = "/man/new",method = RequestMethod.POST)
    public Result saveMan(@RequestBody Man man) {
        return manService.insertMan(man);
    }

    @RequestMapping(value = "/man/search",method = RequestMethod.POST)
    public Result saveMan(@RequestBody JSONObject jsonObject) {
        return manService.findByModel(jsonObject);
    }

    @RequestMapping(value = "/man/{id}",method = RequestMethod.GET)
    public Result getManById(@PathVariable("id") String id) {
        return manService.findById(id);
    }

    @RequestMapping(value = "/man/{id}",method = RequestMethod.DELETE)
    public Result deleteManById(@PathVariable("id") String id) {
        return manService.deleteById(id);
    }

    @RequestMapping(value = "/man/update",method = RequestMethod.PUT)
    public Result updateManById(@RequestBody Man man) {
        return manService.updateByPrimaryKey(man);
    }

    @RequestMapping(value = "/man/all",method = RequestMethod.GET)
    public Result findAll() {
        return manService.findAll();
    }

    /**
     * 七牛文件上传接口
     * @param file
     * @return
     */
    @RequestMapping(value = "/file/upload",method = RequestMethod.POST)
    public Result uploadPic(@RequestParam("pic") CommonsMultipartFile file)throws IOException{
        InputStream inputStream = file.getInputStream();
        String fileKey = apiUtil.uploadFileByInputstream(inputStream);
        log.debug(fileKey);
        if (fileKey == null){
            return Result.fail("上传失败");
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pic",fileKey);
        jsonObject.put("url", QiniuUtil.getFileURL(fileKey));
        return Result.success(jsonObject);
    }
}
