package com.shiro.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wuyiming on 2017/8/2.
 */
public class DateUtil {

    private final static String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(Date date, String pattern){
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(date);
    }

    public static String format(Date date){
        DateFormat dateFormat = new SimpleDateFormat(DEFAULT_PATTERN);
        return dateFormat.format(date);
    }
}
