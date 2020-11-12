package com.jiangqi.mymicroservice.example.servicetwo.service;

import java.util.List;

import com.jiangqi.mymicroservice.example.servicetwo.entity.Department;

public interface DepartmentService {

	public List<Department> getDepartment();
	public Department addDepartment(Department dep);
	public void removeAll();
}
