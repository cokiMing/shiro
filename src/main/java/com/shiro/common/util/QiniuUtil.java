package com.shiro.common.util;

import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.shiro.common.constant.AuthParam;

/**
 * Created by wuyiming on 2017/8/25.
 */
public class QiniuUtil {

    public static String getUploadToken(String bucket){
        Auth auth = Auth.create(AuthParam.ACCESS_KEY,AuthParam.SECRET_KEY);
        return auth.uploadToken(bucket);
    }

    public static UploadManager createUploadManager(){
        Configuration cfg = new Configuration(Zone.zone0());
        return new UploadManager(cfg);
    }

    public static String getFileURL(String fileKey){
        return AuthParam.HOST_TEST + fileKey;
    }
}
