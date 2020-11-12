package com.jiangqi.mymicroservice.example.util.aop;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiangqi.mymicroservice.example.util.MyLogger;

@Configuration
@Aspect
public class ContrlLogAOP {
	
	private Logger logger = LoggerFactory.getLogger(ContrlLogAOP.class);
	
	@Autowired	private MyLogger myLogger;
	
	@Autowired	private ObjectMapper mapper;
	
	 /**
	  * 定义切入点，切入点为com.example.aop下的所有函数
     */
	//@Pointcut("@within(org.springframework.web.bind.annotation.RestController) && "
	@Pointcut("!execution(public String com.jiangqi.mymicroservice.example.util.contrl..*.*(..)) && "
			+ "!execution(public * com.jiangqi.mymicroservice.example.*.contrl.MySwagger.*(..)) && "
			+ "execution(public * com.jiangqi.mymicroservice.example.*.contrl..*.*(..))")
    public void apiLog(){}
    
    /**
     * 前置通知：在连接点之前执行的通知
     * @param joinPoint
     * @throws Throwable
     */
    @Before("apiLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 记录下请求内容
        myLogger.info(logger, String.format("外部调用服务[%s.%s]",joinPoint.getTarget().getClass().getName(), joinPoint.getSignature().getName()));
        myLogger.info(logger, String.format("外部调用服务URL=[%s]", request.getRequestURL().toString()));
        myLogger.info(logger, String.format("外部调用服务HTTP_METHOD=[%s]", request.getMethod()));
        myLogger.info(logger, String.format("外部调用服务HTTP_HEAD=[%s]", getRequestHead(request)));
        myLogger.info(logger, String.format("ARGS=[%s]", Arrays.toString(joinPoint.getArgs())));
    }
    
    @AfterReturning(returning = "ret",pointcut = "apiLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
    	myLogger.info(logger,String.format("RESPONSE=[%s]", mapper.writeValueAsString(ret)));
    }
    
    private String getRequestHead(HttpServletRequest request) throws JsonProcessingException {
    	Map<String, String> map = new HashMap<String, String>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
          String key = (String) headerNames.nextElement();
          String value = request.getHeader(key);
          map.put(key, value);
        }
        
        return mapper.writeValueAsString(map);
    }

}
