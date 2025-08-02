package com.example.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    @Pointcut("execution(* com.example..*(..))")
    public void loggingMethod() {
    }

    @Around("loggingMethod()")
    public Object logMethodExecution(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        log.info("Method {} invoked with arguments: {}", proceedingJoinPoint.getSignature().getName(), Arrays.toString(proceedingJoinPoint.getArgs()));

        Object proceedResult = proceedingJoinPoint.proceed();

        log.info("Method {} returned value: {}", proceedingJoinPoint.getSignature().getName(), proceedResult);

        return proceedResult;
    }
}
