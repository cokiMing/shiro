package com.shiro.common.exception;

/**
 * 参数非法时抛出的异常
 * Created by wuyiming on 2017/8/18.
 */
public class InvalidParamException extends RuntimeException {

    public InvalidParamException(String message) {
        super(message);
    }
}
