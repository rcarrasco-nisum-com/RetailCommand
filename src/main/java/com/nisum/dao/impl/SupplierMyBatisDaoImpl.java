package com.nisum.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.nisum.dao.SupplierDao;
import com.nisum.dao.mapper.SupplierMapper;
import com.nisum.model.Supplier;

@Repository
public class SupplierMyBatisDaoImpl implements SupplierDao {

	@Autowired @Qualifier("supplierMapper")
	private SupplierMapper mapper;
	
	@Override
	public void save(Supplier supplier) {
		// ...
		mapper.save(supplier);
	}

	@Override
	public void save(List<Supplier> list) {
		// ...
		mapper.save(list);
	}

	@Override
	public List<Supplier> getAll() {
		// ...
		return mapper.getAll();
	}

	@Override
	public Supplier get(String name) {
		// ...
		return mapper.get(name);
	}

	@Override
	public void update(Supplier supplier) {
		// ...
		mapper.update(supplier);
	}

	@Override
	public void delete(Supplier supplier) {
		// ...
		mapper.delete(supplier);
	}

}
