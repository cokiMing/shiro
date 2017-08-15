package com.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import com.shiro.common.constant.Constant;
import com.shiro.common.pojo.Result;
import com.shiro.mq.ItemPublisher;
import com.shiro.remote.ApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
//        if (mode.equals(Constant.MODE_BICYCLING)){
//            result = apiUtil.getBicyclingStepsByGoogleMap(olat, olng, dlat, dlng);
//        }
//        if (mode.equals(Constant.MODE_DRIVING)){
//            result = apiUtil.getDrivingStepsByGoogleMap(olat, olng, dlat, dlng);
//        }
        String msg = apiUtil.ApiUtilTest();
        if (result != null){
            return Result.success(result);
        }
        return Result.fail(msg);
    }
}
