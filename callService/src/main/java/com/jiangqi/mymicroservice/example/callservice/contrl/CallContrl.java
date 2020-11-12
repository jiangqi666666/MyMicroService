package com.jiangqi.mymicroservice.example.callservice.contrl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiangqi.mymicroservice.example.callservice.pojo.User;
import com.jiangqi.mymicroservice.example.callservice.service.UserServiceImpl;
import com.jiangqi.mymicroservice.example.util.def.MyDef;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(tags = "测试springboot微服务,消费者restful接口")
@RestController
@RequestMapping(value = "/api/calltest/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class CallContrl {

	@Autowired	private UserServiceImpl userService;
	@Autowired	private StringRedisTemplate redisClient;

	@ApiOperation(value = "获取用户列表", notes = "获取用户列表", response = List.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, value = "交易头信息", defaultValue = MyDef.TRANS_HEAD, required = true, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "Access-Control-Allow-Origin", value = "交易头信息", defaultValue = "*", required = false, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "Access-Control-Allow-Headers", value = "交易头信息", defaultValue = "X-Custom-Header", required = false, dataType = "string", paramType = "header"),
			@ApiImplicitParam(name = "Content-Type", value = "交易头信息", defaultValue = "application/json;charset=utf-8", required = false, dataType = "string", paramType = "header"),
			})
	@GetMapping(value = "/users")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<User> getUserList() throws JsonProcessingException {

		// MediaType.APPLICATION_JSON_UTF8_VALUE;
		// url = http://127.0.0.1:8080/api/servicetwo/v1/users
		return userService.getUserList();
	}

	@ApiOperation(value = "通过参数获取用户列表", notes = "获取用户列表,根据name和old")
	@ApiImplicitParams({
			@ApiImplicitParam(name = MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, value = "交易头信息", required = false, dataType = "string", paramType = "header", defaultValue = MyDef.TRANS_HEAD),
			@ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "departmentId", value = "部门id", required = true, dataType = "Integer", paramType = "query") })
	@GetMapping(value = "/users/findByPar", params = { "name", "departmentId" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody List<User> getUserList(@RequestParam(value = "name") String name,
			@RequestParam(value = "departmentId") Integer departmentId) throws JsonProcessingException {

		// url =
		// http://127.0.0.1:8080/api/servicetwo/v1/users/findByPar?name=aaa&&old=111
		return userService.getUserList(name, departmentId);
	}

	@ApiOperation(value = "获取用户信息", notes = "根据id获取用户信息")
	@ApiImplicitParams({
			@ApiImplicitParam(name = MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, value = "交易头信息", required = false, dataType = "string", paramType = "header", defaultValue = MyDef.TRANS_HEAD)})
	@GetMapping(value = "/users/{id}")
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody User getUser(@PathVariable("id") String id) throws JsonProcessingException {

		// url = http://127.0.0.1:8080/api/servicetwo/v1/users/1
		return userService.getUser(id);
	}

	@ApiOperation(value = "添加用户", notes = "添加用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, value = "交易头信息", required = false, dataType = "string", paramType = "header", defaultValue = MyDef.TRANS_HEAD) })
	@PostMapping("/users")
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody User addUser(@RequestBody User user) throws JsonProcessingException {

		// url = http://127.0.0.1:8080/api/servicetwo/v1/users
		return userService.addUser(user);
	}

	@ApiOperation(value = "删除用户", notes = "根据id删除用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, value = "交易头信息", required = false, dataType = "string", paramType = "header", defaultValue = MyDef.TRANS_HEAD) })
	@DeleteMapping(value = "/users/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("id") String id) throws JsonProcessingException {

		// url = http://127.0.0.1:8080/api/servicetwo/v1/users/1
		userService.deleteUser(id);
	}

	@ApiOperation(value = "删除用户", notes = "根据name和pwd删除用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, value = "交易头信息", required = false, dataType = "string", paramType = "header", defaultValue = MyDef.TRANS_HEAD),
			@ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "string", paramType = "query"),
			@ApiImplicitParam(name = "pwd", value = "密码", required = true, dataType = "string", paramType = "query") })
	@DeleteMapping(value = "/users/delByPar", params = { "name", "pwd" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@RequestParam(value = "name") String name, @RequestParam(value = "pwd") String pwd)
			throws JsonProcessingException {

		// url = http://127.0.0.1:8080/api/servicetwo/v1/users?name=aa&&pwd=111
		userService.deleteUser(name, pwd);
	}

	@ApiOperation(value = "更新用户", notes = "通过id更新用户")
	@ApiImplicitParams({
			@ApiImplicitParam(name = MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, value = "交易头信息", required = false, dataType = "string", paramType = "header", defaultValue = MyDef.TRANS_HEAD) })
	@PatchMapping(value = "/users/{id}")
	@ResponseStatus(HttpStatus.OK)
	public User updateUser(@PathVariable("id") String id, @RequestBody User user) throws JsonProcessingException {

		return userService.updateUser(id, user);
	}

	@ApiOperation(value = "测试全部", notes = "测试全部restful调用")
	@ApiImplicitParams({
			@ApiImplicitParam(name = MyDef.KEY_TRANSINFO_IN_HTTP_HEADER, value = "交易头信息", required = false, dataType = "string", paramType = "header", defaultValue = MyDef.TRANS_HEAD) })
	@GetMapping(value = "/testAll")
	@ResponseStatus(HttpStatus.OK)
	public void testAll() throws JsonProcessingException {
		User user = new User();
		user.setId(1);
		user.setName("aa");
		user.setCreateTime(new Date());
		user.setPwd("pwd");

		userService.getUserList();
		userService.getUserList("aa", 1);
		userService.getUser("aa");
		userService.addUser(user);
		userService.deleteUser("AA");
		userService.deleteUser("aa", "pwd");
		userService.updateUser("a", user);
	}

	@ApiOperation(value = "测试Exception", notes = "测试Exception日志打印堆栈的时候是否能成为一行")
	@GetMapping(value = "/testException")
	@ResponseStatus(HttpStatus.OK)
	public String testException() throws JsonProcessingException {

		//Exception e = new Exception("AAAAAAAAAAAAA");
		//myLogger.error(logger, e);
		
		redisClient.opsForValue().set ("testenv","11111111111") ;
		String str = redisClient.opsForValue().get("testenv");
		return str ;
	}

}
