package com.jiangqi.mymicroservice.example.callservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiangqi.mymicroservice.example.callservice.CallService;
import com.jiangqi.mymicroservice.example.callservice.pojo.User;
import com.jiangqi.mymicroservice.example.util.ServiceCall;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CallService.class)
public class UserServiceImplTest {

	private User user;
	private List<User> list = new ArrayList<User>();

	@Autowired
	private UserServiceImpl userService;

	@MockBean
	private ServiceCall serviceCalla;

	@Before
	public void setUp() throws JsonProcessingException {
		this.user = new User();
		this.user.setId(1);
		this.user.setName("aa");
		this.user.setCreateTime(new Date());
		this.user.setPwd("pwd");
		

		this.list.add(this.user);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetUserList() throws JsonProcessingException {
		Mockito.when(this.serviceCalla.callHttpGetByKV(Mockito.anyString(), Mockito.nullable(Map.class), Mockito.any(Class.class)))
				.thenReturn(this.list);
		List<User> aaaa = this.userService.getUserList();
		Assert.assertEquals(this.list, aaaa);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetUser() throws JsonProcessingException {

		Mockito.when(this.serviceCalla.callHttpGetByKV(Mockito.anyString(), Mockito.nullable(Map.class), Mockito.any(Class.class)))
				.thenReturn(this.user);
		User aaaa = this.userService.getUser("aa");
		Assert.assertEquals(this.user, aaaa);
	}

}
