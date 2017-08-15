package com.shiro.common.aspect;

import com.shiro.common.AbstractCommonComponent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by wuyiming on 2017/8/15.
 */
@Component
@Aspect
public class RemoteAspect extends AbstractCommonComponent{

    @Pointcut("execution(public * com.shiro.remote.ApiUtil.*(..))")
    public void Controller(){
        //此方法仅仅是一个标记,不会被执行
    }

    @Before("Controller()")
    public void printDemoBefore(){
        log.info( "aspectDemo test before");
    }

    @After("Controller()")
    public void printDemoAfter(){
        log.info("aspectDemo test after");
    }

    @Around("Controller()")
    public void aroundMethod(ProceedingJoinPoint point){
        Signature sig = point.getSignature();
        MethodSignature msig;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        try{
            Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
            point.proceed();
            log.info("method " + currentMethod.getName() + " execute");
        }catch (Throwable e){
            log.error("获取方法名失败");
        }
    }
}
