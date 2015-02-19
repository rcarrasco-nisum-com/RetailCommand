package com.nisum.dao;

import java.util.List;

import com.nisum.model.Supplier;

public interface SupplierDao extends Dao<Supplier> {
	
	public void save(List<Supplier> list);

}
