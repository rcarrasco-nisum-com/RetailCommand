package com.nisum.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.nisum.model.Supplier;

public interface SupplierMapper {

	@Select("SELECT * FROM supplier WHERE name = #{name}")
	Supplier getSupplier(@Param("name") String name);
	
}
