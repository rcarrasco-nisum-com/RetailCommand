package com.nisum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nisum.dao.EmployeeDao;
import com.nisum.model.Employee;
import com.nisum.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired @Qualifier("employeeMyBatisDaoImpl")
	private EmployeeDao dao;
	
	@Override
	public void save(Employee employee) {
		// ...
		dao.save(employee);
	}

	@Override
	public List<Employee> getAll() {
		// ...
		return dao.getAll();
	}

	@Override
	public Employee get(String name) {
		// ...
		return dao.get(name);
	}

	@Override
	public void update(Employee employee) {
		// ...
		dao.update(employee);
	}

	@Override
	public void delete(Employee employee) {
		// ...
		dao.delete(employee);
	}

//	@Override
//	public void save(List<Employee> list) {
//		// ...
//		if (list != null)
//			for (Employee employee : list) {
//				dao.save(employee);
//			}
//	}

}
