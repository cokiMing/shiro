package com.shiro.common.pojo;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by wuyiming on 2017/8/2.
 */
public class Result extends JSONObject {

    private final static String CODE_SUCCESS = "1";
    private final static String CODE_FAIL = "0";
    private final static String CODE_SYSTEM_ERROR = "-1";

    private Object content;

    private String code;

    private String message;

    public Result(){

    }

    /**
     * 成功
     * <p>无参</p>
     * @return
     */
    public static Result success(){
        Result result = new Result();
        result.code = CODE_SUCCESS;
        result.message = "成功";
        setResult(result);

        return result;
    }

    /**
     * 成功
     * <p>带参</p>
     * @param content
     * @return
     */
    public static Result success(Object content){
        Result result = new Result();
        result.content = content;
        result.code = CODE_SUCCESS;
        result.message = "成功";
        setResult(result);

        return result;
    }

    /**
     * 由于客户端输入错误的原因导致的失败
     * @param message
     * @return
     */
    public static Result fail(String message){
        Result result = new Result();
        result.code = CODE_FAIL;
        result.message = message;
        setResult(result);

        return result;
    }

    /**
     * 由于服务端内部异常导致的错误
     * @param message
     * @return
     */
    public static Result error(String message){
        Result result = new Result();
        result.code = CODE_SYSTEM_ERROR;
        result.message = message;
        setResult(result);

        return result;
    }

    /**
     * 查看结果是否成功
     * @return
     */
    public boolean isSuccess(){
        return "1".equals(code);
    }

    private static void setResult(Result result){
        result.put("code", result.code);
        result.put("message", result.message);
        result.put("content", result.content);
    }

    @Override
    public String toString() {
        return "Result{" +
                "content=" + content +
                ", code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public Object getContent() {
        return content;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
