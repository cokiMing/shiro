package com.shiro.common.timeJob;

import com.shiro.common.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * Created by wuyiming on 2017/8/9.
 */
public class SpringQuartzTest extends QuartzJobBean {

    private static Log log = LogFactory.getLog(SpringQuartzTest.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try{
            log.info("执行定时任务: " + DateUtil.format(new Date()));
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }
}
