package com.shiro.remote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shiro.common.constant.Constant;
import com.shiro.common.util.NumUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * 这里用来调用
 * 第三方提供的接口
 * Created by wuyiming on 2017/8/9.
 */
@Component
public class ApiUtil {

    private final static String GOOGLEMAP_API_KEY = "AIzaSyCB64p-Gctfzvp1TRb4lMQCebu1FQPHhNI";

    private static ApiUtil apiUtil = new ApiUtil();

    public synchronized static ApiUtil getInstant(){
        return apiUtil;
    }

    public String ApiUtilTest(){
        return "test ok!";
    }

    /**
     * 驾车路径查询
     * @param originLat
     * @param originLng
     * @param desLat
     * @param desLng
     * @return
     */
    public JSONObject getDrivingStepsByGoogleMap(Double originLat,Double originLng,Double desLat,Double desLng){
        return getStepsByGoogleMap(originLat,originLng,desLat,desLng, Constant.MODE_DRIVING);
    }

    /**
     * 骑行路径查询
     * @param originLat
     * @param originLng
     * @param desLat
     * @param desLng
     * @return
     */
    public JSONObject getBicyclingStepsByGoogleMap(Double originLat,Double originLng,Double desLat,Double desLng){
        return getStepsByGoogleMap(originLat,originLng,desLat,desLng, Constant.MODE_BICYCLING);
    }

    /**
     * 通过GoogleMap api获取路径信息
     * @param originLat
     * @param originLng
     * @param desLat
     * @param desLng
     * @return
     */
    private JSONObject getStepsByGoogleMap(Double originLat,Double originLng,Double desLat,Double desLng,String mode){
        String origin = originLat + "," + originLng;
        String destination = desLat + "," + desLng;
        String doublePattern = "0.0000";

        try{
            Client client = Client.create();
            WebResource webResource = client.resource("https://maps.googleapis.com/maps/api/directions/json?" +
                    "origin=" + origin +
                    "&destination=" + destination +
                    "&key=" + GOOGLEMAP_API_KEY +
                    "&mode=" + mode);
            ClientResponse clientResponse = webResource.type(MediaType.APPLICATION_FORM_URLENCODED)
                    .get(ClientResponse.class);
            if (clientResponse.getStatus() == 200) {
                String textEntity = clientResponse.getEntity(String.class);
                if (textEntity != null) {
                    String status = JSON.parseObject(textEntity).getString("status");
                    if (status.equals("OK")){
                        JSONObject route = JSON.parseObject(textEntity).getJSONArray("routes").getJSONObject(0);
                        JSONObject leg = route.getJSONArray("legs").getJSONObject(0);
                        JSONObject distance = leg.getJSONObject("distance");
                        String miles = distance.getString("text");
                        Double kms = distance.getInteger("value")/1000.0;
                        JSONArray steps = leg.getJSONArray("steps");
                        JSONObject result = new JSONObject();
                        List<String> points = new ArrayList<>();
                        points.add(NumUtil.formatDouble(originLat,doublePattern)
                                + "," + NumUtil.formatDouble(originLng,doublePattern));
                        for (Object o : steps){
                            JSONObject object = (JSONObject)o;
                            JSONObject end_location = object.getJSONObject("end_location");
                            String point = NumUtil.formatDouble(end_location.getDouble("lat"),doublePattern) + "," +
                                    NumUtil.formatDouble(end_location.getDouble("lng"),doublePattern);
                            points.add(point);
                        }
                        result.put("miles",miles);
                        result.put("distance",kms);
                        result.put("points",points);
                        return result;
                    }
                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String args[]){
        long millis = System.currentTimeMillis();
        JSONObject steps = getInstant().getBicyclingStepsByGoogleMap(40.8171321, -73.996854, 40.7516627, -75.0725247);
        System.out.println("耗时：" + (System.currentTimeMillis() - millis));
        System.out.println(steps);
    }
}
