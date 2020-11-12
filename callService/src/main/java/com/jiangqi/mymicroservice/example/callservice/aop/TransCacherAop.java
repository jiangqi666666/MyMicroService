package com.jiangqi.mymicroservice.example.callservice.aop;

import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiangqi.mymicroservice.example.util.def.MyDef;
import com.jiangqi.mymicroservice.example.util.pojo.TransHeadInfo;

@Configuration
@Aspect
public class TransCacherAop {
	
	@Autowired	private Environment env;
	@Autowired	private ObjectMapper mapper;
	@Autowired	private StringRedisTemplate redisClient;

	
	@Pointcut("execution(public * com.jiangqi.mymicroservice.example.callservice.contrl..*.*(..))")
	public void transCacher() {
	}
	

	/**
	 * 在调用服务时，先尽量使用缓存
	 * 交易处理成功结束后也使用缓存
	 * 
	 * @param joinPoint
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	@Around("transCacher()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		
		return pjp.proceed();

		/*
		 * ServletRequestAttributes attributes = (ServletRequestAttributes)
		 * RequestContextHolder.getRequestAttributes(); HttpServletRequest request =
		 * attributes.getRequest();
		 * 
		 * String tmp1=request.getHeader(MyDef.KEY_TRANSINFO_IN_HTTP_HEADER);
		 * 
		 * TransHeadInfo trans; if(tmp1!=null) { trans = mapper.readValue(tmp1,
		 * TransHeadInfo.class); } else { trans=new TransHeadInfo();
		 * trans.setType("AA"); trans.setId("AAA"); trans.setCall("aaaaaaa"); }
		 * 
		 * @SuppressWarnings("rawtypes") Class returnType = ((MethodSignature)
		 * pjp.getSignature()).getReturnType();
		 * 
		 * String key = String.format("%s:%s", trans.getType(), trans.getId());
		 * 
		 * String tmp = this.redisClient.opsForValue().get(key); if (tmp == null) {
		 * Object obj = pjp.proceed();
		 * this.redisClient.opsForValue().set(key,mapper.writeValueAsString(obj),Integer
		 * .valueOf(env.getProperty("trans.cacher.timeout")),TimeUnit.SECONDS); return
		 * obj; } else { if(returnType.getName().equals("void")==true) return null; else
		 * return mapper.readValue(tmp,returnType); }
		 */
	}
}
