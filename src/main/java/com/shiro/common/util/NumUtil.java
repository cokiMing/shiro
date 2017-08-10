package com.shiro.common.util;

import java.text.DecimalFormat;

/**
 * Created by wuyiming on 2017/8/9.
 */
public class NumUtil {

    private final static String DEFAULT_DOUBLE_PATTERN = "0.0";

    public static String formatDouble(Double num,String pattern){
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        return decimalFormat.format(num);
    }

    public static String formatDouble(Double num){
        DecimalFormat decimalFormat = new DecimalFormat(DEFAULT_DOUBLE_PATTERN);
        return decimalFormat.format(num);
    }
}
