package com.jiangqi.mymicroservice.example.servicetwo.contrl;

import java.util.Date;
import java.util.Iterator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiangqi.mymicroservice.example.servicetwo.ServiceTwo;
import com.jiangqi.mymicroservice.example.servicetwo.entity.Department;
import com.jiangqi.mymicroservice.example.servicetwo.entity.User;
import com.jiangqi.mymicroservice.example.servicetwo.filter.TransHeadInfoFilter;
import com.jiangqi.mymicroservice.example.servicetwo.service.DepartmentServiceImpl;
import com.jiangqi.mymicroservice.example.servicetwo.service.UserServiceImpl;
import com.jiangqi.mymicroservice.example.util.def.MyDef;
import com.jiangqi.mymicroservice.example.util.pojo.TransHeadInfo;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceTwo.class)
public class UserControllerTest {
	
	private MockMvc mockMvc;
	private User user,user1;
	private Department dep;
	private TransHeadInfo transHead;
	private HttpHeaders head = new HttpHeaders();

	@Autowired	private WebApplicationContext webApplicationContext;
	@Autowired	private ObjectMapper mapper;
	//@Autowired	private TransHeadInfoFilter transHeadInfoFilter;
	@Autowired	private UserServiceImpl userService;
	@Autowired 	private DepartmentServiceImpl depService;

	@Before
	public void before() throws JsonProcessingException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();

		this.transHead = new TransHeadInfo();
		this.transHead.setType("transType");
		this.transHead.setId("1234567");
		this.transHead.setCall("callSvc");

		head.add(MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, mapper.writeValueAsString(this.transHead));
		
		this.user = new User();
		this.dep=new Department();
		this.dep.setName("测试部门");
		this.dep=this.depService.addDepartment(this.dep);
		
		this.user.setName("test");
		this.user.setPwd("pwd");
		this.user.setCreateTime(new Date());
		this.user.setDepartmentId(this.dep.getId());
		
		this.user1=this.userService.addUser(this.user);
		for(int i=0;i<10;i++) {
			this.user.setName("test"+i);
			this.user.setPwd("pwd"+i);
			this.user=this.userService.addUser(this.user);
		}
	}
	
	@After
	public void after() {
		this.userService.removeAll();
		this.depService.removeAll();
	}

	@Test
	public void testGetUserList() throws Exception {
		this.mockMvc
		.perform(MockMvcRequestBuilders.get("/api/servicetwo/v1/users").accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8).headers(this.head))
		.andExpect(MockMvcResultMatchers.status().isOk()) // 验证状态码是否为200
		.andDo(MockMvcResultHandlers.print()) // 输出MvcResult到控制台
		.andReturn(); // 请求结束后处理的内容
	}

	@Test
	public void testGetUserListStringInteger() throws Exception {
		
		Iterator<User> ite=this.userService.getUserList().iterator();
		
		MultiValueMap<String, String> params=new LinkedMultiValueMap<String, String>();
		
		User tmp=ite.next();
		
		params.add("name", tmp.getName());
		params.add("departmentId", tmp.getDepartmentId().toString());
		
		this.mockMvc
		.perform(MockMvcRequestBuilders.get("/api/servicetwo/v1/users/findByPar").accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).headers(this.head).params(params))
		.andExpect(MockMvcResultMatchers.status().isOk()) // 验证状态码是否为200
		.andDo(MockMvcResultHandlers.print()) // 输出MvcResult到控制台
		.andReturn(); // 请求结束后处理的内容
	}

	@Test
	public void testGetUser() throws Exception {
		
		this.mockMvc
		.perform(MockMvcRequestBuilders.get("/api/servicetwo/v1/users/1").accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8).headers(this.head))
		.andExpect(MockMvcResultMatchers.status().isOk()) // 验证状态码是否为200
		.andDo(MockMvcResultHandlers.print()) // 输出MvcResult到控制台
		.andReturn(); // 请求结束后处理的内容
	}

	@Test
	public void testAddUser() throws Exception {
				
		this.mockMvc
		.perform(MockMvcRequestBuilders.post("/api/servicetwo/v1/users").accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8).headers(this.head).content(mapper.writeValueAsString(this.user)))
		.andExpect(MockMvcResultMatchers.status().isCreated()) // 验证状态码是否为200
		.andDo(MockMvcResultHandlers.print()) // 输出MvcResult到控制台
		.andReturn(); // 请求结束后处理的内容
	}

	@Test
	public void testDeleteUserStringString() throws Exception {
		MultiValueMap<String, String> params=new LinkedMultiValueMap<String, String>();
		params.add("name", "test0");
		params.add("pwd", "pwd0");
		
		this.mockMvc
		.perform(MockMvcRequestBuilders.delete("/api/servicetwo/v1//users/delByPar").accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8).headers(this.head).params(params))
		.andExpect(MockMvcResultMatchers.status().isNoContent()) // 验证状态码是否为200
		.andDo(MockMvcResultHandlers.print()) // 输出MvcResult到控制台
		.andReturn(); // 请求结束后处理的内容
	}

	@Test
	public void testDeleteUserInteger() throws Exception {
		
		this.mockMvc
		.perform(MockMvcRequestBuilders.delete(String.format("/api/servicetwo/v1/users/%d",this.user.getId())).accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8).headers(this.head))
		.andExpect(MockMvcResultMatchers.status().isNoContent()) // 验证状态码是否为200
		.andDo(MockMvcResultHandlers.print()) // 输出MvcResult到控制台
		.andReturn(); // 请求结束后处理的内容
	}

	@Test
	public void testUpdateUser() throws JsonProcessingException, Exception {
		this.mockMvc
		.perform(MockMvcRequestBuilders.patch(String.format("/api/servicetwo/v1/users/%d",this.user1.getId())).accept(MediaType.APPLICATION_JSON_UTF8)
				.contentType(MediaType.APPLICATION_JSON_UTF8).headers(this.head).content(mapper.writeValueAsString(this.user)))
		.andExpect(MockMvcResultMatchers.status().isCreated()) // 验证状态码是否为200
		.andDo(MockMvcResultHandlers.print()) // 输出MvcResult到控制台
		.andReturn(); // 请求结束后处理的内容
	}

}
