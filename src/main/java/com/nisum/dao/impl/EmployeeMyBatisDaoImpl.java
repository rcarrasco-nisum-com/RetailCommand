package com.nisum.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.nisum.dao.EmployeeDao;
import com.nisum.dao.mapper.EmployeeMapper;
import com.nisum.model.Employee;

@Repository
public class EmployeeMyBatisDaoImpl implements EmployeeDao {

	@Autowired @Qualifier("employeeMapper")
	private EmployeeMapper mapper;
	
	@Override
	public void save(Employee employee) {
		// ...
		mapper.save(employee);
	}

	@Override
	public List<Employee> getAll() {
		// ...
		return mapper.getAll();
	}

	@Override
	public Employee get(String name) {
		// ...
		return mapper.get(name);
	}

	@Override
	public void update(Employee employee) {
		// ...
		mapper.update(employee);
	}

	@Override
	public void delete(Employee employee) {
		// ...
		mapper.delete(employee);
	}

	@Override
	public void save(List<Employee> list) {
		// ...
		if (list != null)
			for (Employee employee : list) {
				mapper.save(employee);
			}
	}

}
