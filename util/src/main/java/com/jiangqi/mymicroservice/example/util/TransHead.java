package com.jiangqi.mymicroservice.example.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.jiangqi.mymicroservice.example.util.pojo.TransHeadInfo;


/**
 * 交易头的通用处理类
 * @author Administrator
 *
 */
@Component
public class TransHead {

	@Autowired	private Environment env;
	

	/**
	 * 创建交易头<br>
	 * 如果不存在则创建新的，存在则修改 生成交易的唯一流水号
	 * 
	 * @return 
	 */
	public TransHeadInfo createTransHead() {
		
		TransHeadInfo head = new TransHeadInfo();
		head.setType(env.getProperty("trans.type"));
		head.setId(createId());
		head.setCall(env.getProperty("trans.type"));

		return head;
	}

	/**
	 * 给交易创建唯一流水号<br>
	 * 暂时用这个，以后用redis或其他方法替换成连续号码<br>
	 * 目前格式：交易类型+创建时间（毫秒）+随机数
	 * 
	 * @return 交易流水号
	 */
	private String createId() {

		int id = (int) (1 + Math.random() * (10 - 1 + 1));

		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		
		
		return String.format("%s%s%d", env.getProperty("trans.type"),
				new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date), id);
	}
}
