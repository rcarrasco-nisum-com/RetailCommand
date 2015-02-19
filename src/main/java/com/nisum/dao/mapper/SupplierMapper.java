package com.nisum.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.nisum.model.Supplier;

public interface SupplierMapper {

	@Insert("INSERT INTO supplier(name, email, phone) VALUES (#{name}, #{email}, #{phone})")
	void save(Supplier supplier);
	
	void save(List<Supplier> list);
	
	@Select("SELECT * FROM supplier")
	List<Supplier> getAll();
	
	@Select("SELECT * FROM supplier WHERE name = #{name}")
	Supplier get(String name);
	
	@Update("UPDATE supplier SET email = #{email}, phone = #{phone} WHERE name = #{name}")
	void update(Supplier supplier);
	
	@Delete("DELETE FROM supplier WHERE name = #{name}")
	void delete(Supplier supplier);
}
