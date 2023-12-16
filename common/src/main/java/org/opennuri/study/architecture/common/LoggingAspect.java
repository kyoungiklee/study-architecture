package org.opennuri.study.architecture.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LoggingAspect {
    private final LoggingProducer loggingProducer;

    public LoggingAspect(LoggingProducer loggingProducer) {
        this.loggingProducer = loggingProducer;
    }

    @Before("execution(* org.opennuri.study.architecture.*.adapter.in.web.*.*(..))") // Pointcut
    public void logBeforeMethodExecution(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Before Method Execution: " + methodName);
        loggingProducer.sendLog("logging", "Before Method Execution: " + methodName);

    }

}
