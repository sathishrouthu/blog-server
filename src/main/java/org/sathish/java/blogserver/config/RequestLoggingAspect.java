package org.sathish.java.blogserver.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class RequestLoggingAspect {

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {
    }

    @Around("controllerPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();

        String requestURI = request.getRequestURI();
        String method = request.getMethod();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        log.info("Request received - URI: {} | Method: {} | Class: {} | Function: {} | Args: {}",
                requestURI, method, className, methodName, Arrays.toString(args));

        long startTime = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();

            log.info("Request completed - URI: {} | Method: {} | Time taken: {}ms | Response: {}",
                    requestURI, method, (endTime - startTime),
                    result != null ? result.toString().substring(0, Math.min(result.toString().length(), 100)) : "null");

            return result;
        } catch (Exception e) {
            log.error("Request failed - URI: {} | Method: {} | Error: {}",
                    requestURI, method, e.getMessage());
            throw e;
        }
    }
}