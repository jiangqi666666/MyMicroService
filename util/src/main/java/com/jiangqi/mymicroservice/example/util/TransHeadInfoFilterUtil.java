package com.jiangqi.mymicroservice.example.util;

import java.io.IOException;
import java.net.URLDecoder;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiangqi.mymicroservice.example.util.def.MyDef;
import com.jiangqi.mymicroservice.example.util.pojo.TransHeadInfo;
import com.jiangqi.mymicroservice.example.util.pojo.TransHeadInfoContext;

@Component
public class TransHeadInfoFilterUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(TransHeadInfoFilterUtil.class);
	
	@Autowired	ObjectMapper mapper;
	@Autowired 	TransHead transHead;
	@Autowired	private MyLogger myLogger;

	/**
	 * 通过过滤器获得http头中的交易信息，如果不存在则默认赋值
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub

		 /* 从http请求head中获取交易头信息
		 * 如果不存在交易头，则添加一个默认的
		 */
		String infoJson = ((HttpServletRequest) request).getHeader(MyDef.KEY_TRANSINFO_IN_HTTP_HEADER);
		myLogger.info(logger, String.format("xxxx=[%s]", infoJson));
		if (StringUtils.isEmpty(infoJson)==false) {
			infoJson = URLDecoder.decode(infoJson, "UTF-8");
			TransHeadInfoContext.setTransHeadInfo(mapper.readValue(infoJson, TransHeadInfo.class));
			myLogger.warn(logger,String.format("获取交易头,[%s]", infoJson));
		}
		else {
			TransHeadInfoContext.setTransHeadInfo(transHead.createTransHead());
			myLogger.warn(logger,"无交易头，设置默认交易头" );
		}

		// 链路 直接传给下一个过滤器
		chain.doFilter(request, response);
	}

}
