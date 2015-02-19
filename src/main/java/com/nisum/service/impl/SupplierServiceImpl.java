package com.nisum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nisum.dao.SupplierDao;
import com.nisum.model.Supplier;
import com.nisum.service.SupplierService;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired @Qualifier("supplierMyBatisDaoImpl")
	private SupplierDao dao;
	
	@Override
	public void save(Supplier supplier) {
		// ...
		dao.save(supplier);
	}

	@Override
	public List<Supplier> getAll() {
		// ...
		return dao.getAll();
	}

	@Override
	public Supplier get(String name) {
		// ...
		return dao.get(name);
	}

	@Override
	public void update(Supplier supplier) {
		// ...
		dao.update(supplier);
	}

	@Override
	public void delete(Supplier supplier) {
		// ...
		dao.delete(supplier);
	}

	@Override
	public void save(List<Supplier> list) {
		// ...
		dao.save(list);
	}

}
