package com.shiro.remote;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.shiro.common.AbstractCommonComponent;
import com.shiro.common.constant.AuthParam;
import com.shiro.common.constant.CommonParam;
import com.shiro.common.util.KeyUtils;
import com.shiro.common.util.NumUtil;
import com.shiro.common.util.QiniuUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这里用来调用
 * 第三方提供的接口
 * Created by wuyiming on 2017/8/9.
 */
@Component()
public class ApiUtil extends AbstractCommonComponent{

    private final static String GOOGLEMAP_API_KEY = "AIzaSyCB64p-Gctfzvp1TRb4lMQCebu1FQPHhNI";

    public String apiUtilTest(){
        return "apiUtil is ok";
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
        return getStepsByGoogleMap(originLat,originLng,desLat,desLng, CommonParam.MODE_DRIVING);
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
        return getStepsByGoogleMap(originLat,originLng,desLat,desLng, CommonParam.MODE_BICYCLING);
    }

    /**
     * 上传文件至七牛
     * @param inputStream
     * @return
     */
    public String uploadFileByInputstream(InputStream inputStream){
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            String upToken = QiniuUtil.getUploadToken(AuthParam.BUCKET);
            UploadManager uploadManager = QiniuUtil.createUploadManager();
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                log.debug(putRet.key);
                log.debug(putRet.hash);
                return putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return null;
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
            client.setConnectTimeout(5000);
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
            log.error(e.getMessage());
            return null;
        }
    }

    public static void main(String args[]){
        long millis = System.currentTimeMillis();
        ApiUtil apiUtil = new ApiUtil();
        JSONObject steps = apiUtil.getBicyclingStepsByGoogleMap(40.8171321, -73.996854, 40.7516627, -75.0725247);
        System.out.println("耗时：" + (System.currentTimeMillis() - millis));
        System.out.println(steps);
    }
}
