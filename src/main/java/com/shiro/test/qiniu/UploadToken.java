package com.shiro.test.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.shiro.common.constant.AuthParam;

/**
 * Created by wuyiming on 2017/8/25.
 */
public class UploadToken {

    public static String getUploadToken(String bucket){
        Auth auth = Auth.create(AuthParam.ACCESS_KEY,AuthParam.SECRET_KEY);
        return auth.uploadToken(bucket);
    }

    public static void uploadFile(){
        Configuration cfg = new Configuration(Zone.zone0());
        UploadManager uploadManager = new UploadManager(cfg);
        String accessKey = "lINxwpyyL6U_YeEh2jU_jHNSFm_xJynJJrf0I9Es";
        String secretKey = "mH8Q0VwkSyMeyWAdo6tWE5ElgNWB1etsZiaL9QEF";
        String bucket = "test";
        String localFilePath = "/Users/chen/Desktop/test.png";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    public static void main(String[] args){
        uploadFile();
    }

}
