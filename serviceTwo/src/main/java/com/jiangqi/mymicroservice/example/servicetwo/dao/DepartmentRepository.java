package com.jiangqi.mymicroservice.example.servicetwo.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jiangqi.mymicroservice.example.servicetwo.entity.Department;


public interface DepartmentRepository extends JpaRepository<Department, Integer>{
	
	@Query(value="select d from Department d ")
	public List<Department> queryAllDepartment();

}
