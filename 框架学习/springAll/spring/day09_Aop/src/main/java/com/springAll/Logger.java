package com.springAll;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component("logger")
@Aspect
//表示当前类是一个切面类
public class Logger {

    @Pointcut("execution(* com.springAll.service.impl.*.*(..))")
    private void pt1(){}
    /**
     *   用于打印日志切入点方法之前执行
     */
    @Before("pt1()")
    public void beforePrintLog(){
        System.out.println("===============Logger类记录开始日志================");
    }
    @AfterReturning("pt1()")
    public void afterPrintLog(){
        System.out.println("===============Logger类记录结束日志================");
    }
    @AfterThrowing("pt1()")
    public void errorPrintLog(){
        System.out.println("===============Logger类记录异常日志================");
    }
    @After("pt1()")
    public void finallyPrintLog(){
        System.out.println("===============Logger类记录最终日志================");
    }

    //@Around("pt1()")
    public Object aroundPrintLog(ProceedingJoinPoint pjp){
        Object returnVal = null;
        try {
            Object obj[] = pjp.getArgs(); //得到方法执行所需的参数
            System.out.println("===============Logger类记录开始日志================ 前置");
            returnVal = pjp.proceed(); //切入点方法
            System.out.println("===============Logger类记录开始日志================ 后置");
        } catch (Throwable throwable) {
            System.out.println("===============Logger类记录开始日志================ 异常");
            throwable.printStackTrace();
        }finally {
            System.out.println("===============Logger类记录开始日志================ 最终");
        }
        return returnVal;
    }
}
