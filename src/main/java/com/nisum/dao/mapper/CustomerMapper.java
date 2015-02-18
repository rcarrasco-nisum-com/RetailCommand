package com.nisum.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nisum.model.Customer;

public interface CustomerMapper {

	@Insert("INSERT INTO customer(name, email, phone) VALUES (#{name}, #{email}, #{phone})")
	void save(Customer customer);
	
	void save(List<Customer> list);
	
	@Select("SELECT * FROM customer")
	List<Customer> getAll();
	
	@Select("SELECT * FROM customer WHERE name = #{name}")
	Customer get(String name);
	
	@Update("UPDATE customer SET email = #{email}, phone = #{phone} WHERE name = #{name}")
	void update(Customer customer);
	
	@Delete("DELETE FROM customer WHERE name = #{name}")
	void delete(Customer customer);
}
