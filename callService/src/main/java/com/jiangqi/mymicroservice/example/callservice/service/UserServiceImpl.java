package com.jiangqi.mymicroservice.example.callservice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.jiangqi.mymicroservice.example.callservice.pojo.User;
import com.jiangqi.mymicroservice.example.util.ServiceCall;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired	private Environment env;

	@Autowired	private ServiceCall serviceCall;

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserList() throws JsonProcessingException {
		// TODO Auto-generated method stub
		// url = http://127.0.0.1:8080/api/servicetwo/v1/users
		String url = String.format("%s/users", env.getProperty("trans.serviceTwo.users"));
		return serviceCall.callHttpGetByKV(url, null, List.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUserList(String name, Integer departmentId) throws JsonProcessingException {
		// TODO Auto-generated method stub
		// url =
		// http://127.0.0.1:8080/api/servicetwo/v1/users/findByPar?name=aaa&&old=111
		String url = String.format("%s/users/findByPar?name={name}&&old={old}",
				env.getProperty("trans.serviceTwo.users"));
		Map<String, Object> par = new HashMap<String, Object>();
		par.put("name", name);
		par.put("departmentId", departmentId);

		return serviceCall.callHttpGetByKV(url, par, List.class);
	}

	@Override
	public User getUser(String id) throws JsonProcessingException {
		// TODO Auto-generated method stub
		// url = http://127.0.0.1:8080/api/servicetwo/v1/users/1
		String url = String.format("%s/users/%s", env.getProperty("trans.serviceTwo.users"), id);
		return serviceCall.callHttpGetByKV(url, null, User.class);
	}

	@Override
	public User addUser(User user) throws JsonProcessingException {
		// TODO Auto-generated method stub
		// url = http://127.0.0.1:8080/api/servicetwo/v1/users
		String url = String.format("%s/users", env.getProperty("trans.serviceTwo.users"));
		return serviceCall.callHttpPost(url, user, User.class);
	}

	@Override
	public void deleteUser(String id) throws JsonProcessingException {
		// TODO Auto-generated method stub
		// url = http://127.0.0.1:8080/api/servicetwo/v1/users/1
		String url = String.format("%s/users/%s", env.getProperty("trans.serviceTwo.users"), id);
		serviceCall.callHttpDeleteByKV(url, null, User.class);
	}

	@Override
	public void deleteUser(String name, String pwd) throws JsonProcessingException {
		// TODO Auto-generated method stub
		String url = String.format("%s/users/delByPar?name={name}&&pwd={pwd}",
				env.getProperty("trans.serviceTwo.users"), name, pwd);

		MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
		param.add("name", name);
		param.add("pwd", pwd);

		serviceCall.callHttpDeleteByKV(url, param, String.class);
	}

	@Override
	public User updateUser(String id,User user) throws JsonProcessingException {
		// TODO Auto-generated method stub
		// url = http://127.0.0.1:8080/api/servicetwo/v1/users/1
		String url = String.format("%s/users/%s", env.getProperty("trans.serviceTwo.users"), id);
		return serviceCall.callHttpPatchByJson(url, user, User.class);
	}

}
