package com.td.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MyAspect {

    // 切入点表达式：表示拦截 TestController 中的所有方法
    @Pointcut("execution(* com.td.controller.TestController.*(..))")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("MyAspect#around-pre");
        Object result = proceedingJoinPoint.proceed();
        System.out.println("MyAspect#around-post");
        return result;
    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        System.out.println("MyAspect#before");
    }

    @Before("pointcut()")
    public void before2(JoinPoint joinPoint) {
        System.out.println("MyAspect#before2");
    }

    @After("pointcut()")
    public void after(JoinPoint joinPoint) {
        System.out.println("MyAspect#after");
    }


}
