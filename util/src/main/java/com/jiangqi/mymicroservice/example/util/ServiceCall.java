package com.jiangqi.mymicroservice.example.util;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiangqi.mymicroservice.example.util.def.MyDef;
import com.jiangqi.mymicroservice.example.util.pojo.TransHeadInfo;
import com.jiangqi.mymicroservice.example.util.pojo.TransHeadInfoContext;

/**
 * 负责调用微服务<br>
 * 
 * @author Administrator
 *
 */
@Component
public class ServiceCall {

	private final static Logger logger = LoggerFactory.getLogger(ServiceCall.class);

	@Autowired	private ObjectMapper mapper;

	@Autowired	private RestTemplate restTemplate;

	@Autowired	private MyLogger myLogger;

	/**
	 * 准备http头
	 * @param apiVersion
	 * @return
	 * @throws JsonProcessingException
	 */
	private HttpHeaders preHttpHeaders() throws JsonProcessingException {
		HttpHeaders requestHeaders = new HttpHeaders();
		TransHeadInfo head = TransHeadInfoContext.getTransHeadInfo();

		//添加交易头和版本号
		requestHeaders.add(MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, mapper.writeValueAsString(head));
	
		// 消息头中必须添加如下内容，否则不能跨域传递消息头
		requestHeaders.add("Access-Control-Allow-Origin", "*");
		requestHeaders.add("Access-Control-Allow-Headers", "X-Custom-Header");
		requestHeaders.add("Content-Type", "application/json;charset=utf-8");
		
		return requestHeaders;
	}
	
	/**
	 * 按照http的Get方式调用微服务<br>
	 * 参数以url后name=value方式
	 * @param url 微服务url
	 * @param param 需要传递的参数
	 * @param var 应答返回的类型
	 * @param apiVersion api版本
	 * @return 
	 * @throws JsonProcessingException
	 */
	public <T> T callHttpGetByKV(String url, @Nullable Map<String,Object> param, Class<T> var) throws JsonProcessingException {
		
		HttpEntity<Map<String,Object>> requestEntity= new HttpEntity<Map<String,Object>>(null, preHttpHeaders());
		if (param!=null) {
			myLogger.info(logger, String.format("准备调用服务:[%s] ,param=[%s]",url,this.mapper.writeValueAsString(param)));
			return restTemplate.exchange(url, HttpMethod.GET, requestEntity, var,param).getBody();
		}
		else { 
			myLogger.info(logger, String.format("准备调用服务:[%s] ,param=null",url));
			return restTemplate.exchange(url, HttpMethod.GET, requestEntity, var).getBody();
		}
	}
	
	/**
	 * 按照http的Get方式调用微服务<br>
	 * 参数以json方式传递
	 * @param url 微服务url
	 * @param param 需要传递的参数
	 * @param var 应答返回的类型
	 * @param apiVersion api版本
	 * @return 
	 * @throws JsonProcessingException
	 */
	public <T,I> T callHttpGetByJson(String url,@Nullable I param, Class<T> var) throws JsonProcessingException {
		HttpEntity<String> requestEntity;
		if (param!=null) {
			myLogger.info(logger, String.format("准备调用服务:[%s] ,param=[%s]",url,this.mapper.writeValueAsString(param)));
			requestEntity= new HttpEntity<String>(this.mapper.writeValueAsString(param), preHttpHeaders());
		}
		else{
			myLogger.info(logger, String.format("准备调用服务:[%s] ,param=null",url));
			requestEntity= new HttpEntity<String>(null, preHttpHeaders());
		}
		
		return restTemplate.exchange(url,HttpMethod.GET,requestEntity,var).getBody();
	}

	/**
	 * 按照http的Post方式调用微服务<br>
	 * 参数以json方式传递
	 * @param url 微服务url
	 * @param param 需要传递的参数
	 * @param var 应答返回的类型
	 * @param apiVersion api版本
	 * @return 
	 * @throws JsonProcessingException
	 */
	public <T> T callHttpPost(String url, T param, Class<T> var) throws JsonProcessingException {
		
		myLogger.info(logger, String.format("准备调用服务:[%s] ,param=[%s]",url,this.mapper.writeValueAsString(param)));
		
		HttpEntity<String> requestEntity= new HttpEntity<String>(this.mapper.writeValueAsString(param), preHttpHeaders());
		return restTemplate.exchange(url,HttpMethod.POST,requestEntity,var).getBody();
	}
	
	/**
	 * 按照http的Delete方式调用微服务<br>
	 * 参数以url后name=value方式
	 * @param url 微服务url
	 * @param param 需要传递的参数
	 * @param var 应答返回的类型
	 * @param apiVersion api版本
	 * @return 
	 * @throws JsonProcessingException
	 */
	public <T> T callHttpDeleteByKV(String url,@Nullable MultiValueMap<String,?> param, Class<T> var) throws JsonProcessingException {
		
		HttpEntity<MultiValueMap<String,?>> requestEntity= new HttpEntity<MultiValueMap<String,?>>(null, preHttpHeaders());
		if (param!=null) {
			myLogger.info(logger, String.format("准备调用服务:[%s] ,param=[%s]",url,this.mapper.writeValueAsString(param)));
			return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, var,param).getBody();
		}
		else {
			myLogger.info(logger, String.format("准备调用服务:[%s] ,param=null",url));
			return restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, var).getBody();
		}
	}
	
	/** 按照http的Delete方式调用微服务<br>
	 * 参数以json方式传递
	 * @param url 微服务url
	 * @param param 需要传递的参数
	 * @param var 应答返回的类型
	 * @param apiVersion api版本
	 * @return 
	 * @throws JsonProcessingException
	 */
	public <T,I> T callHttpDeleteByJson(String url,@Nullable I param, Class<T> var) throws JsonProcessingException {
		
		HttpEntity<String> requestEntity;
		if(param!=null) {
			myLogger.info(logger, String.format("准备调用服务:[%s] ,param=[%s]",url,this.mapper.writeValueAsString(param)));
			requestEntity= new HttpEntity<String>(this.mapper.writeValueAsString(param), preHttpHeaders());
		}
		else {
			myLogger.info(logger, String.format("准备调用服务:[%s] ,param=null",url));
			requestEntity= new HttpEntity<String>(null, preHttpHeaders());
		}
		return restTemplate.exchange(url,HttpMethod.DELETE,requestEntity,var).getBody();
	}


	/**
	 * 按照http的Patch方式调用微服务<br>
	 * 参数以url后name=value方式
	 * @param url 微服务url
	 * @param param 需要传递的参数
	 * @param var 应答返回的类型
	 * @param apiVersion api版本
	 * @return 
	 * @throws JsonProcessingException
	 */
	public <T> T callHttpPatchByKV(String url,@Nullable MultiValueMap<String,?> param, Class<T> var) throws JsonProcessingException {
		
		HttpEntity<MultiValueMap<String,?>> requestEntity= new HttpEntity<MultiValueMap<String,?>>(null, preHttpHeaders());
		if (param!=null) {
			myLogger.info(logger, String.format("准备调用服务:[%s] ,param=[%s]",url,this.mapper.writeValueAsString(param)));
			return restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, var,param).getBody();
		}
		else {
			myLogger.info(logger, String.format("准备调用服务:[%s] ,param=null",url));
			return restTemplate.exchange(url, HttpMethod.PATCH, requestEntity, var).getBody();
		}
	}
	
	/** 按照http的Patch方式调用微服务<br>
	 * 参数以json方式传递
	 * @param url 微服务url
	 * @param param 需要传递的参数
	 * @param var 应答返回的类型
	 * @param apiVersion api版本
	 * @return 
	 * @throws JsonProcessingException
	 */
	public <T> T callHttpPatchByJson(String url, T param, Class<T> var) throws JsonProcessingException {
		
		myLogger.info(logger, String.format("准备调用服务:[%s] ,param=[%s]",url,this.mapper.writeValueAsString(param)));
		HttpEntity<String> requestEntity= new HttpEntity<String>(this.mapper.writeValueAsString(param), preHttpHeaders());	
		return restTemplate.exchange(url,HttpMethod.PATCH,requestEntity,var).getBody();
	}

}
