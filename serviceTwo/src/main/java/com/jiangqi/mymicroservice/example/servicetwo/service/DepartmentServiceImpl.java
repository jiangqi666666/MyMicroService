package com.jiangqi.mymicroservice.example.servicetwo.service;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jiangqi.mymicroservice.example.servicetwo.dao.DepartmentRepository;
import com.jiangqi.mymicroservice.example.servicetwo.entity.Department;


@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
	
	@Autowired
	private DepartmentRepository dao;

	@Override
	public List<Department> getDepartment() {
		// TODO Auto-generated method stub
		return dao.findAll();
	}

	@Override
	public Department addDepartment(Department dep) {
		// TODO Auto-generated method stub
		return dao.save(dep);
	}

	@Override
	public void removeAll() {
		// TODO Auto-generated method stub
		dao.deleteAll();
	}

}
