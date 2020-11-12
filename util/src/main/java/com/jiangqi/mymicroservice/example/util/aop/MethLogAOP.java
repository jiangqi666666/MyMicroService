package com.jiangqi.mymicroservice.example.util.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import com.jiangqi.mymicroservice.example.util.MyLogger;

@Configuration
@Aspect
public class MethLogAOP {
	private Logger logger = LoggerFactory.getLogger(MethLogAOP.class);

	@Autowired	private MyLogger myLogger;

	/**
	 * 定义切入点，切入点为com.example.aop下的所有函数
	 */
	@Pointcut("execution(public * com.jiangqi.mymicroservice.example..*.*(..)) && "
			+ "!execution(public * com.jiangqi.mymicroservice.example.*.contrl.MySwagger.*(..)) && "
			+ "!execution(public * com.jiangqi.mymicroservice.example.util.MyLogger.*(..)) && "
			+ "!execution(public String com.jiangqi.mymicroservice.example.*.contrl..*.*(..))")
	public void methLog() {
	}

	/**
	 * 前置通知：在连接点之前执行的通知
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	@Before("methLog()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
	
		myLogger.info(logger, String.format("调用方法[%s.%s]",joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName()));
	}
}
