package com.nisum.service;

import java.util.List;

import com.nisum.model.Supplier;

public interface SupplierService extends Service<Supplier> {
	
	public void save(List<Supplier> list);
	
}
