package com.shiro.common.aspect;

import com.shiro.common.AbstractCommonComponent;
import com.shiro.common.annotation.MyAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeoutException;

/**
 * Created by wuyiming on 2017/8/15.
 */
@Component
@Aspect
public class RemoteAspect extends AbstractCommonComponent{

    @Pointcut("execution(public * com.shiro.remote.ApiUtil.*(..))")
    public void Remote(){
        //此方法仅仅是一个标记,不会被执行
    }

    @Pointcut("@annotation(com.shiro.common.annotation.MyAnnotation)")
    public void myAnnotation() {
    }

    @Before("Remote()")
    public void printDemoBefore(){
        log.info( "aspectDemo test before");
    }

    @After("Remote()")
    public void printDemoAfter(){
        log.info("aspectDemo test after");
    }

    @Around("Remote()")
    public Object aroundMethod(ProceedingJoinPoint point)throws Exception{
        Signature sig = point.getSignature();
        MethodSignature msig;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        Object result;
        try{
            Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
            result = point.proceed();
            log.info("method " + currentMethod.getName() + " executed");
        }catch (Throwable e){
            log.error(e.getMessage());
            throw new Exception(e);
        }

        return result;
    }

    @Around("myAnnotation()")
    public Object aroundAnnotation(ProceedingJoinPoint point)throws Exception{
        Method method = getMethod(point);
        if (method != null) {
            MyAnnotation annotation = method.getAnnotation(MyAnnotation.class);
            String message = annotation.message();
            log.info("i got a message:" + message);
        }
        try{
            return point.proceed();
        } catch (Throwable t) {
            return null;
        }
    }

    private Method getMethod(ProceedingJoinPoint point) {
        Signature sig = point.getSignature();
        MethodSignature msig;
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("method invalid");
        }
        msig = (MethodSignature) sig;
        Object target = point.getTarget();
        try{
            Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
            return currentMethod;
        }catch (Throwable e){
            return null;
        }
    }
}
