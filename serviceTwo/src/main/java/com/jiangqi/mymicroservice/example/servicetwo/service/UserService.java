package com.jiangqi.mymicroservice.example.servicetwo.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jiangqi.mymicroservice.example.servicetwo.entity.User;


public interface UserService {
	
	/**
	 * 获得全部用户
	 * @return
	 */
	public List<User> getUserList();
	
	/**
	 * 获得从第几页开始，多少行数据
	 * @param page 页数
	 * @param size 行数
	 * @return
	 */
	public List<User> getUserList(int page,int size);
	
	/**
	 * 通过ID获得用户
	 * @param id
	 * @return
	 */
	public User getUser(Integer id);
	
	/**
	 * 通过用户名获得用户
	 * @param name
	 * @return
	 */
	public List<User> getUserList(String name);
	
	/**
	 * 根据用户名和部门联合查询
	 * @param name
	 * @param departmentId
	 * @return
	 */
	public List<User> getUserList(String name,Integer departmentId);
	
	/**
	 * 按部门查询用户，返回分组
	 * @param departmentId
	 * @param page
	 * @return
	 */
	public Page<User> queryUser(Integer departmentId,Pageable page);
	
	/**
	 * 按部门分组统计，每个部门多少用户
	 * @param departmentId
	 * @param page
	 * @return
	 */
	public Page<User> queryUser2(Integer departmentId,Pageable page);
	
	/**
	 * 新增用户
	 * @param user
	 * @return
	 */
	public User addUser(User user);
	
	/**
	 * 删除用户
	 * @param id
	 */
	public void deleteUser(Integer id);
	
	/**
	 * 删除用户，通过名称+密码
	 * @param name
	 * @param pwd
	 */
	public void deleteUser(String name,String pwd);
	
	/**
	 * 修改用户信息
	 * @param user
	 */
	public void updateUser(User user);
	
	/**
	 *  清空数据
	 * @param user
	 */
	public void removeAll();
	
}
