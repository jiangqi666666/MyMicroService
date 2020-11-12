package com.jiangqi.mymicroservice.example.callservice.contrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
import com.jiangqi.mymicroservice.example.callservice.CallService;
import com.jiangqi.mymicroservice.example.callservice.pojo.User;
import com.jiangqi.mymicroservice.example.util.ServiceCall;
import com.jiangqi.mymicroservice.example.util.def.MyDef;
import com.jiangqi.mymicroservice.example.util.pojo.TransHeadInfo;

//import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CallService.class)
public class CallContrlTest {

	private MockMvc mockMvc;
	private User user;
	private List<User> list = new ArrayList<User>();
	
	@MockBean
	private ServiceCall serviceCall;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	ObjectMapper mapper;
	
	//@Autowired
	//TransHeadInfoFilter transHeadInfoFilter;

	@Before
	public void before() throws JsonProcessingException {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		this.user = new User();
		this.user.setId(1);
		this.user.setName("aa");
		this.user.setCreateTime(new Date());
		this.user.setPwd("pwd");
		this.user.setDepartmentId(1);

		this.list.add(this.user);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetUserList() throws Exception {
		
		// Mock模拟serviceCall.callHttpGetByKV，返回User
		Mockito.when(this.serviceCall.callHttpGetByKV(Mockito.anyString(), Mockito.nullable(Map.class),  Mockito.any(Class.class))).thenReturn(this.list);

		HttpHeaders head = new HttpHeaders();
		TransHeadInfo transHead = new TransHeadInfo();
		transHead.setType("CallService.getUserList");
		transHead.setId("1");
		transHead.setCall("callSvc");

		head.add(MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, mapper.writeValueAsString(transHead));
		
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/api/calltest/v1/users").accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON_UTF8).headers(head))
				.andExpect(MockMvcResultMatchers.status().isOk()) // 验证状态码是否为200
				// .andExpect(MockMvcResultMatchers.model().attributeExists("user"))
				// .andExpect(MockMvcResultMatchers.content().string("hello lvgang"))
				.andDo(MockMvcResultHandlers.print()) // 输出MvcResult到控制台
				.andReturn(); // 请求结束后处理的内容
		
	}

	@SuppressWarnings({ "unchecked" })
	@Test
	public void testGetUserListStringInteger() throws Exception {

		Mockito.when(this.serviceCall.callHttpGetByKV(Mockito.anyString(), Mockito.nullable(Map.class), Mockito.any(Class.class))).thenReturn(this.list);

		MultiValueMap<String,String> paras=new LinkedMultiValueMap<String,String>();
		paras.add("name", "name");
		paras.add("departmentId", "1");
		
		HttpHeaders head = new HttpHeaders();
		TransHeadInfo transHead = new TransHeadInfo();
		transHead.setType("CallService.getUserListStringInteger");
		transHead.setId("2");
		transHead.setCall("callSvc");
		head.add(MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, mapper.writeValueAsString(transHead));

		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/api/calltest/v1/users").accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON_UTF8).headers(head).params(paras))
				.andExpect(MockMvcResultMatchers.status().isOk()) // 验证状态码是否为200
				.andDo(MockMvcResultHandlers.print()) // 输出MvcResult到控制台
				.andReturn(); // 请求结束后处理的内容
	}

	@SuppressWarnings({  "unchecked" })
	@Test
	public void testGetUser() throws Exception {

		// Mock模拟serviceCall.callHttpGetByKV，返回User
		Mockito.when(serviceCall.callHttpGetByKV(Mockito.anyString(),Mockito.nullable(Map.class), Mockito.any(Class.class))).thenReturn(this.user);

		HttpHeaders head = new HttpHeaders();
		TransHeadInfo transHead = new TransHeadInfo();
		transHead.setType("CallService.getUser");
		transHead.setId("3");
		transHead.setCall("callSvc");
		head.add(MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, mapper.writeValueAsString(transHead));
		
		// MvcResult mvcResult = mockMvc
		this.mockMvc
				.perform(MockMvcRequestBuilders.get("/api/calltest/v1/users/1").accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON_UTF8).headers(head))
				.andExpect(MockMvcResultMatchers.status().isOk()) // 验证状态码是否为200
				.andDo(MockMvcResultHandlers.print()) // 输出MvcResult到控制台
				.andReturn(); // 请求结束后处理的内容

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testAddUser() throws Exception {

		Mockito.when(this.serviceCall.callHttpPost(Mockito.anyString(),Mockito.any(User.class), Mockito.any(Class.class))).thenReturn(this.user);

		HttpHeaders head = new HttpHeaders();
		TransHeadInfo transHead = new TransHeadInfo();
		transHead.setType("CallService.addUser");
		transHead.setId("4");
		transHead.setCall("callSvc");
		head.add(MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, mapper.writeValueAsString(transHead));
		
		// MvcResult mvcResult = mockMvc
		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/api/calltest/v1/users").accept(MediaType.APPLICATION_JSON_UTF8)
						.contentType(MediaType.APPLICATION_JSON_UTF8).headers(head)
						.content(mapper.writeValueAsString(this.user)))
				.andExpect(MockMvcResultMatchers.status().isCreated()) // 验证状态码是否为200
				.andDo(MockMvcResultHandlers.print()) // 输出MvcResult到控制台
				.andReturn(); // 请求结束后处理的内容
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteUserString() throws Exception {

		Mockito.when(this.serviceCall.callHttpDeleteByKV(Mockito.anyString(), Mockito.nullable(MultiValueMap.class), Mockito.any(Class.class))).thenReturn(this.user);

		HttpHeaders head = new HttpHeaders();
		TransHeadInfo transHead = new TransHeadInfo();
		transHead.setType("CallService.deleteUserString");
		transHead.setId("5");
		transHead.setCall("callSvc");
		head.add(MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, mapper.writeValueAsString(transHead));
		
		// MvcResult mvcResult = mockMvc
		this.mockMvc
				.perform(MockMvcRequestBuilders.delete("/api/calltest/v1/users/1")
						.accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8)
						.headers(head))
				.andExpect(MockMvcResultMatchers.status().isNoContent()) // 验证状态码是否为200
				.andDo(MockMvcResultHandlers.print()) // 输出MvcResult到控制台
				.andReturn(); // 请求结束后处理的内容
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteUserStringString() throws Exception {

		Mockito.when(this.serviceCall.callHttpDeleteByKV(Mockito.anyString(), Mockito.nullable(MultiValueMap.class), Mockito.any(Class.class))).thenReturn(this.user);

		MultiValueMap<String,String> paras=new LinkedMultiValueMap<String,String>();
		paras.add("name", "name");
		paras.add("pwd", "pwd");
		
		HttpHeaders head = new HttpHeaders();
		TransHeadInfo transHead = new TransHeadInfo();
		transHead.setType("CallService.deleteUserStringString");
		transHead.setId("5");
		transHead.setCall("callSvc");
		head.add(MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, mapper.writeValueAsString(transHead));
		
		// MvcResult mvcResult = mockMvc
		this.mockMvc
				.perform(MockMvcRequestBuilders.delete("/api/calltest/v1/users/delByPar")
						.accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8)
						.headers(head).params(paras))
				.andExpect(MockMvcResultMatchers.status().isNoContent()) // 验证状态码是否为200
				.andDo(MockMvcResultHandlers.print()) // 输出MvcResult到控制台
				.andReturn(); // 请求结束后处理的内容
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testUpdateUser() throws Exception {

		Mockito.when(this.serviceCall.callHttpPatchByJson(Mockito.anyString(), Mockito.any(User.class), Mockito.any(Class.class))).thenReturn(this.user);

		HttpHeaders head = new HttpHeaders();
		TransHeadInfo transHead = new TransHeadInfo();
		transHead.setType("CallService.updateUser");
		transHead.setId("6");
		transHead.setCall("callSvc");
		head.add(MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, mapper.writeValueAsString(transHead));
		
		// MvcResult mvcResult = mockMvc
		this.mockMvc
				.perform(MockMvcRequestBuilders.patch("/api/calltest/v1/users/1")
						.accept(MediaType.APPLICATION_JSON_UTF8).contentType(MediaType.APPLICATION_JSON_UTF8)
						.headers(head).content(mapper.writeValueAsString(this.user)))
				.andExpect(MockMvcResultMatchers.status().isOk()) // 验证状态码是否为200
				.andDo(MockMvcResultHandlers.print()) // 输出MvcResult到控制台
				.andReturn(); // 请求结束后处理的内容
	}

}
