package com.jiangqi.mymicroservice.example.servicetwo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	// 部门
	@Column
	private String pwd;
	
	// 部门
	@Column
	private String name;
	
	// 创建时间
	@Column(name = "create_time")
	private Date createTime;
	
	// 创建时间
	@Column(name = "department_id")
	private Integer departmentId;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "department_id") Department department;
	 */
	
	
}
