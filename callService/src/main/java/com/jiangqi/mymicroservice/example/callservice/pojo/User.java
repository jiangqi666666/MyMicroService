package com.jiangqi.mymicroservice.example.callservice.pojo;

import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {

	private Integer id;
	private String pwd;
	private String name;
	private Date createTime;
	private Integer departmentId;
}
