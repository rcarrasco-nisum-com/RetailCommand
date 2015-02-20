package com.nisum.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nisum.model.Employee;

public interface EmployeeMapper {

	@Insert("INSERT INTO employee(name, email, phone) VALUES (#{name}, #{email}, #{phone})")
	void save(Employee employee);
	
	@Select("SELECT * FROM employee")
	List<Employee> getAll();
	
	@Select("SELECT * FROM employee WHERE name = #{name}")
	Employee get(String name);
	
	@Update("UPDATE employee SET email = #{email}, phone = #{phone} WHERE name = #{name}")
	void update(Employee employee);
	
	@Delete("DELETE FROM employee WHERE name = #{name}")
	void delete(Employee employee);
	
}
