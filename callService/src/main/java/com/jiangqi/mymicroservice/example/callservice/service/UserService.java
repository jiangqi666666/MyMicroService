package com.jiangqi.mymicroservice.example.callservice.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiangqi.mymicroservice.example.callservice.pojo.User;

public interface UserService {

	public List<User> getUserList()throws JsonProcessingException;
	public List<User> getUserList(String name,Integer departmentId)throws JsonProcessingException;
	public User getUser(String id)throws JsonProcessingException;
	public User addUser(User user)throws JsonProcessingException;
	public void deleteUser(String id)throws JsonProcessingException;
	public void deleteUser(String name,String pwd)throws JsonProcessingException;
	public User updateUser(String id,User user)throws JsonProcessingException;
}
