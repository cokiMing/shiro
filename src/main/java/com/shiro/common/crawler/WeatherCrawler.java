package com.shiro.common.crawler;

import com.shiro.dao.redis.RedisDao;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wuyiming on 2017/8/9.
 */
public class WeatherCrawler {
    @Autowired
    private RedisDao redisDao;

    private static Log log = LogFactory.getLog(WeatherCrawler.class);

    /**
     * 抓取百度天气信息
     * @param city
     * @return
     */
    public static String getWeathInfo(String city){
        String engine = "http://www.baidu.com/s?ie=utf-8&tn=baidu";
        String content;
        InputStream in = null;

        try{
            URL url = new URL(engine+ "&wd=" + city + "天气");
            URLConnection urlConnection = url.openConnection();
            in = urlConnection.getInputStream();
            byte[] buff = new byte[1024];
            StringBuilder stringBuilder = new StringBuilder();
            int len;
            while ((len = in.read(buff)) != -1){
                String str = new String(buff,0,len);
                stringBuilder.append(str);
            }
            content = stringBuilder.toString();
        }catch (Exception e){
            log.error(e.getMessage());
            return null;
        }finally {
            try{
                in.close();
            }catch (Exception e){
                log.error(e.getMessage());
            }
        }
        return content.substring(content.indexOf("\"op_weather4_twoicon_shishi_sub\"")+33,content.indexOf("(实时)"));
    }

    public void saveWeathInHangzhou(){
        String weathInfo = getWeathInfo("杭州");
        if (weathInfo != null){
            log.info("redis记录weather: " + weathInfo);
            redisDao.set("weather",weathInfo);
        }
    }

    public static void main(String[] args){
        long millis = System.currentTimeMillis();
        String weathInfo = getWeathInfo("杭州");
        System.out.println(weathInfo);
        System.out.println("耗时："+(System.currentTimeMillis() - millis));
    }
}
