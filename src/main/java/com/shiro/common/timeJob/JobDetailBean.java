package com.shiro.common.timeJob;

import com.shiro.common.framework.spring.SpringContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.lang.reflect.Method;

/**
 * Created by wuyiming on 2017/8/9.
 */
public class JobDetailBean extends QuartzJobBean {

    private Log log = LogFactory.getLog(JobDetailBean.class);

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String objectName = getObjectName(jobExecutionContext);
        String methodName = getMethodName(jobExecutionContext);
        Object bean = SpringContextHolder.getBean(objectName);
        Object arguments = getArguments(jobExecutionContext);
        try{
            Method method = bean.getClass().getMethod(methodName);
            if (arguments != null){
                method.invoke(bean,arguments);
            }else{
                method.invoke(bean,null);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private String getObjectName(JobExecutionContext jobExecutionContext){
        return (String)jobExecutionContext.getJobDetail().getJobDataMap().get("targetObject");
    }

    private String getMethodName(JobExecutionContext jobExecutionContext){
        return (String)jobExecutionContext.getJobDetail().getJobDataMap().get("targetMethod");
    }

    private Object getArguments(JobExecutionContext jobExecutionContext){
        return jobExecutionContext.getJobDetail().getJobDataMap().get("arguments");
    }
}
