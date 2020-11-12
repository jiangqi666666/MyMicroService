package com.jiangqi.mymicroservice.example.callservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

	@Bean
	public Queue helloQueue() {
		return new Queue("hello");
	}
}
