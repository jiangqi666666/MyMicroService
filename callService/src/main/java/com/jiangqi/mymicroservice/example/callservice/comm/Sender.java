package com.jiangqi.mymicroservice.example.callservice.comm;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Sender {
	
	@Autowired
	private AmqpTemplate rabbit;
	
	public void send() {
		String context="hi"+new Date();
		System.out.println("sender msg: "+context);
		this.rabbit.convertAndSend("hello",context);
	}
}
