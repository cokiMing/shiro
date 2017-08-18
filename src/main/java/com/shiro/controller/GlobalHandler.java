package com.shiro.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shiro.common.pojo.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 全局处理
 * Created by wuyiming on 2017/8/3.
 */
@ControllerAdvice
public class GlobalHandler {

    private static Log log = LogFactory.getLog(GlobalHandler.class);

    /**
     * 全局异常处理
     * @param ex
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JSONObject processMethod(Exception ex, HttpServletRequest request, HttpServletResponse response)throws IOException{
        String msg = "请求路径：" + request.getRequestURI()
                + (request.getQueryString() == null ? "" : request.getQueryString()) + "，参数:"
                + JSON.toJSONString(request.getParameterMap());
        log.error("系统异常："+ex.getMessage());
        log.error(msg);

        response.setStatus(500);
        return Result.error(ex.getMessage());
    }

    /**
     * 请求参数错误处理
     * @param ex
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public JSONObject invalidArgHandler(Exception ex, HttpServletRequest request, HttpServletResponse response)throws IOException{
        String msg = "请求路径：" + request.getRequestURI()
                + (request.getQueryString() == null ? "" : request.getQueryString()) + "，参数:"
                + JSON.toJSONString(request.getParameterMap());
        log.error("参数丢失："+ex.getMessage());
        log.error(msg);

        response.setStatus(400);
        return Result.fail("参数错误");
    }

    /**
     * 输入日期格式处理
     * @param binder
     */
    @InitBinder
    public void initDate(WebDataBinder binder){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        simpleDateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class,new CustomDateEditor(simpleDateFormat,false));
    }
}
