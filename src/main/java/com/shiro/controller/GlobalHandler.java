package com.shiro.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shiro.common.exception.InvalidParamException;
import com.shiro.common.pojo.Result;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.reflection.ReflectionException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
import java.util.concurrent.TimeoutException;

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
        errorLog(ex,request);
        response.setStatus(500);
        String msg = "系统异常";
        if (ex instanceof HttpRequestMethodNotSupportedException){
            response.setStatus(400);
            msg = "请求方式错误:" + request.getMethod();
        }
        return Result.error(msg);
    }

    /**
     * 请求参数异常处理
     * @param ex
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, InvalidParamException.class})
    @ResponseBody
    public JSONObject invalidArgHandler(Exception ex, HttpServletRequest request, HttpServletResponse response)throws IOException{
        errorLog(ex,request);
        response.setStatus(400);
        return Result.fail("参数错误");
    }

    /**
     * 数据库异常的处理
     * @param ex
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler({MyBatisSystemException.class})
    @ResponseBody
    public JSONObject mybatisExceptionHandler(Exception ex, HttpServletRequest request, HttpServletResponse response)throws IOException{
        Throwable cause = ex.getCause();
        String msg = "数据库异常";
        response.setStatus(500);
        if (cause instanceof ReflectionException){
            response.setStatus(400);
            msg = "参数不正确";
        }

        return Result.error(msg);
    }

    /**
     * 请求超时异常处理
     * @param ex
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ExceptionHandler(TimeoutException.class)
    @ResponseBody
    public JSONObject timeOutHandler(Exception ex, HttpServletRequest request, HttpServletResponse response)throws IOException{
        errorLog(ex,request);
        response.setStatus(502);
        return Result.fail("请求超时");
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

    /**
     * 记录异常
     * @param ex
     * @param request
     */
    private void errorLog(Exception ex, HttpServletRequest request){
        String msg = "请求路径：" + request.getRequestURI()
                + (request.getQueryString() == null ? "" : request.getQueryString()) + "，参数:"
                + JSON.toJSONString(request.getParameterMap());
        log.error("系统异常："+ex.getMessage());
        log.error(msg);
    }
}
