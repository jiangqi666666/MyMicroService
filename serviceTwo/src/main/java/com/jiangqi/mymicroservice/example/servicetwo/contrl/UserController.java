package com.jiangqi.mymicroservice.example.servicetwo.contrl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiangqi.mymicroservice.example.servicetwo.entity.User;
import com.jiangqi.mymicroservice.example.servicetwo.service.UserServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;

@Api(tags = "测试springboot微服务，生产者restful接口")
@RestController
@RequestMapping("/api/servicetwo/v1")
public class UserController {
	@Autowired
	private UserServiceImpl userService;

	@Autowired
	ObjectMapper mapper;

	@ApiOperation(value = "获取用户列表", notes = "获取全部用户列表",response = List.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name="apiVersion",allowableValues="1,2,3",value="版本号",required=true,dataType="string",paramType="header")
		})
	@GetMapping(value = "/users")
	@ResponseStatus(HttpStatus.OK)
	public List<User> getUserList() {
		
		//url = http://127.0.0.1:8080/api/servicetwo/v1/users
		return userService.getUserList();
	}
	
	@ApiOperation(value = "获取用户列表", notes = "通过name和old", response = List.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "apiVersion", allowableValues = "1,2,3", value = "版本号", required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "departmentId", value = "部门id", required = true, dataType = "Integer", paramType = "query") })
	@GetMapping(value = "/users/findByPar", params = { "name", "departmentId" })
	@ResponseStatus(HttpStatus.OK)
	public List<User> getUserList(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "departmentId") Integer departmentId) {
		
		//url = http://127.0.0.1:8080/api/servicetwo/v1/users/findByPar?name=aaa&&old=111
		return userService.getUserList(name, departmentId);
	}

	@ApiOperation(value = "获取用户信息", notes = "根据id获取用户信息", response = User.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "apiVersion", allowableValues = "1,2,3", value = "版本号", required = true, dataType = "string", paramType = "header"),
		})
	@GetMapping(value = "/users/{id}")
	@ResponseStatus(HttpStatus.OK)
	public User getUser(@PathVariable("id") Integer id) {
		
		//url = http://127.0.0.1:8080/api/servicetwo/v1/users/1
		return userService.getUser(id);
	}

	@ApiOperation(value = "添加用户", notes = "添加用户", response = User.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "apiVersion", allowableValues = "1,2,3", value = "版本号", required = true, dataType = "string", paramType = "header"),
		})
	@PostMapping(value = "/users")
	@ResponseStatus(HttpStatus.CREATED)
	public User addUser(@RequestBody User user) throws JsonProcessingException {
		
		//url = http://127.0.0.1:8080/api/servicetwo/v1/users
		return userService.addUser(user);
	}

	@ApiOperation(value = "删除用户", notes = "根据name和pwd删除用户")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "apiVersion", allowableValues = "1,2,3", value = "版本号", required = true, dataType = "string", paramType = "header"),
		@ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "string", paramType = "query"),
		@ApiImplicitParam(name = "pwd", value = "密码", required = true, dataType = "string", paramType = "query") })
	@DeleteMapping(value = "/users/delByPar",params = { "name", "pwd" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@RequestParam(value = "name") String name,
			@RequestParam(value = "pwd") String pwd) {
		
		//url = http://127.0.0.1:8080/api/servicetwo/v1/users/1
		userService.deleteUser(name,pwd);
	}
	
	@ApiOperation(value = "删除用户", notes = "根据id删除")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "apiVersion", allowableValues = "1,2,3", value = "版本号", required = true, dataType = "string", paramType = "header"),
		})
	@DeleteMapping(value = "/users/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("id") Integer id) {
		
		//url = http://127.0.0.1:8080/api/servicetwo/v1/users?name=aa&&pwd=111
		userService.deleteUser(id);
	}

	@ApiOperation(value = "更新用户", notes = "更新用户", response = User.class)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "apiVersion", allowableValues = "1,2,3", value = "版本号", required = true, dataType = "string", paramType = "header"),
		})
	@PatchMapping(value = "/users/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void updateUser(@PathVariable("id") Integer id, @RequestBody User user) throws JsonProcessingException {
		
		//url = http://127.0.0.1:8080/api/servicetwo/v1/users/1
		user.setId(id);
		userService.updateUser(user);
	}
}