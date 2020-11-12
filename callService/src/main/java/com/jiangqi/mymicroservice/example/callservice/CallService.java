package com.jiangqi.mymicroservice.example.callservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


//不添加basePackages，springboot只会从ServiceTwo所在目录下进行扫描
@SpringBootApplication(scanBasePackages="com.jiangqi.mymicroservice.example")
@ServletComponentScan
public class CallService 
{
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(CallService.class, args);
	}
}
