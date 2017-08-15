package com.shiro.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 存放Common包中的公共组件
 * Created by wuyiming on 2017/8/15.
 */
public abstract class AbstractCommonComponent {
    protected Log log = LogFactory.getLog(this.getClass());
}
