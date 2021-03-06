package com.shiro.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Controller公用组件放到这里
 * Created by wuyiming on 2017/8/10.
 */
public abstract class AbstractController {
    protected Log log = LogFactory.getLog(this.getClass());
}
