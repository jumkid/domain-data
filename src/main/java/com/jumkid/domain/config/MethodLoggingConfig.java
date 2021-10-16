package com.jumkid.domain.config;

import com.jumkid.share.config.AbstractMethodLoggingConfig;
import com.jumkid.share.config.custom.CustomPerformanceMonitorInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@Configuration
@EnableAspectJAutoProxy
@Aspect
public class MethodLoggingConfig extends AbstractMethodLoggingConfig {

    @Pointcut("execution(* com.jumkid.domain.service.DomainDataService.*(..))")
    public void monitor() { }

    @Before("execution(* com.jumkid.domain.controller.*Controller.*(..))")
    public void log4AllControllers(JoinPoint joinPoint) {
        super.log(joinPoint);
    }

    @Bean
    public Advisor performanceMonitorAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("com.jumkid.domain.config.MethodLoggingConfig.monitor()");
        return new DefaultPointcutAdvisor(pointcut, new CustomPerformanceMonitorInterceptor(false));
    }

}
