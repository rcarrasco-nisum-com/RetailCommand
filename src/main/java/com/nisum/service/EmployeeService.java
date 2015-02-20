package com.nisum.service;

import java.util.List;

import com.nisum.model.Employee;

public interface EmployeeService extends Service<Employee> {

	public void save(List<Employee> list);
	
}
