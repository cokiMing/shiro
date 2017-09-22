package com.shiro.service;

import com.shiro.common.annotation.MyAnnotation;
import com.shiro.common.util.DateUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by wuyiming on 2017/9/20.
 */
@Service
public class TaskService {

    @MyAnnotation(message = "hello")
    @Scheduled(cron = "0/15 * * * * ?")
    public void scheduleTask() {
        System.out.println("execute task ..." + DateUtil.format(new Date()));
    }
}
