package com.jiangqi.mymicroservice.example.util.pojo;

import lombok.Getter;
import lombok.Setter;

/**
 * 交易投的信息<br>
 * 每个微服务交互时由交易发起者填写，便于在日志中通过此信息串联交易路径
 * @author Administrator
 *
 */
@Setter
@Getter
public class TransHeadInfo {
	/**
	 * 交易类型
	 */
	private String type;
	
	/**
	 * 交易流水号
	 */
	private String id;
	
	/**
	 * 交易消费者类型
	 */
	private String call;
}
