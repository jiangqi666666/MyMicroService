package com.jiangqi.mymicroservice.example.servicetwo.entity;

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
public class Department {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id ;
	
	@Column
	private String name ;
	
	/*
	 * @OneToMany(mappedBy="department") private Set<User> users = new
	 * HashSet<User>();
	 */
}
