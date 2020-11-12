package com.jiangqi.mymicroservice.example.servicetwo.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import com.jiangqi.mymicroservice.example.util.TransHeadInfoFilterUtil;

//@Component//无需添加此注解，@WebFilter会自动将此filter加入容器，千万不能加这些注解，包括@Config，否则拦截器会失效，所有方法都拦截
//注册器名称为TransHeadInfoFilter,拦截的url为所有/api，用来获取消息头
@WebFilter(filterName = "TransHeadInfoFilter", urlPatterns = { "/api/servicetwo/*" })
@Order(1)//指定过滤器的执行顺序,值越大越靠后执行
public class TransHeadInfoFilter implements Filter{
	
	@Autowired private TransHeadInfoFilterUtil filterUtil;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		filterUtil.doFilter(arg0,arg1,arg2);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
