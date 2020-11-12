package com.jiangqi.mymicroservice.example.servicetwo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

// @ComponentScan(basePackages = "com.boot.demo.xxx.*.*")
// 用于扫描@Controller @Service

//@EnableJpaRepositories(basePackages = "com.boot.demo.xxx.*.dao") 
//用于扫描Dao @Repository

//@ServletComponentScan(com.boot.demo.xxx)
//用于扫描Filter

//用于扫描JPA实体类 @Entity
//@EntityScan("com.jiangqi.mymicroservice.example.util.entity")

//@SpringBootApplication,含有： @ComponentScan，@Configuration ，@EnableAutoConfiguration 
//不添加basePackages，springboot只会从ServiceTwo所在目录下进行扫描
@SpringBootApplication(scanBasePackages="com.jiangqi.mymicroservice.example")
@ServletComponentScan
public class ServiceTwo 
{
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(ServiceTwo.class, args);
	}
}
