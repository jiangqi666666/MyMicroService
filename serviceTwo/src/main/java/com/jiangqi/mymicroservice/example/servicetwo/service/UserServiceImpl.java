package com.jiangqi.mymicroservice.example.servicetwo.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.jiangqi.mymicroservice.example.servicetwo.dao.UserRepository;
import com.jiangqi.mymicroservice.example.servicetwo.entity.User;


@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userDao;

	@Autowired
	EntityManager em;

	@Override
	public List<User> getUserList() {
		// TODO Auto-generated method stub
		return userDao.findAll();
	}

	@Override
	public List<User> getUserList(int page, int size) {
		// TODO Auto-generated method stub
		PageRequest pageable = PageRequest.of(page, size);
		Page<User> pageObject = userDao.findAll(pageable);
		int totalPage = pageObject.getTotalPages();
		int realSize = pageObject.getSize();
		long count = pageObject.getTotalElements();

		return pageObject.getContent();
	}

	@Override
	public User getUser(Integer id) {
		// TODO Auto-generated method stub
		Optional<User> user = userDao.findById(id);
		return user.orElse(null);
	}

	@Override
	public List<User> getUserList(String name) {
		// TODO Auto-generated method stub
		return userDao.findByName(name);
	}

	@Override
	public List<User> getUserList(String name, Integer departmentId) {
		// TODO Auto-generated method stub
		return userDao.nativeQuery2(name, departmentId);
	}

	@Override
	public Page<User> queryUser(Integer departmentId, Pageable page) {
		// TODO Auto-generated method stub
		return userDao.queryUsers(departmentId, page);
	}

	@Override
	public Page<User> queryUser2(Integer departmentId, Pageable page) {
		// TODO Auto-generated method stub
		// 构造JPQL和实际的参数
		StringBuilder baseJpql = new StringBuilder("from User u where 1=1 ");
		Map<String, Object> paras = new HashMap<String, Object>();
		if (departmentId != null) {
			baseJpql.append("and  u.department.id=:deptId");
			paras.put("deptId", departmentId);
		}
		// 查询满足条件的总数
		long count = getQueryCount(baseJpql, paras);
		if (count == 0) {
			return new PageImpl(Collections.emptyList(), page, 0);
		}
		// 查询满足条件结果集
		List list = getQueryResult(baseJpql, paras, page);

		// 返回结果
		Page ret = new PageImpl(list, page, count);
		return ret;
	}

	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return userDao.save(user);
	}

	@Override
	public void deleteUser(Integer id) {
		// TODO Auto-generated method stub
		userDao.deleteById(id);
	}

	@Override
	public void deleteUser(String name, String pwd) {
		// TODO Auto-generated method stub
		userDao.delUser(name, pwd);
	}

	@Override
	public void updateUser(User user) {
		// TODO Auto-generated method stub
		userDao.save(user);
	}

	private List getQueryResult(StringBuilder baseJpql, Map<String, Object> paras, Pageable page) {
		Query query = em.createQuery("select u " + baseJpql.toString());
		setQueryParameter(query, paras);
		query.setFirstResult((int) page.getOffset());
		query.setMaxResults(page.getPageNumber());
		List list = query.getResultList();
		return list;
	}

	private Long getQueryCount(StringBuilder baseJpql, Map<String, Object> paras) {
		Query query = em.createQuery("select count(1) " + baseJpql.toString());
		setQueryParameter(query, paras);
		Number number = (Number) query.getSingleResult();
		return number.longValue();
	}

	private void setQueryParameter(Query query, Map<String, Object> paras) {
		for (Entry<String, Object> entry : paras.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void removeAll() {
		// TODO Auto-generated method stub
		userDao.deleteAll();
	}

}
