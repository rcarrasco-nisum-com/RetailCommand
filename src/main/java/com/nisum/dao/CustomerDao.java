package com.nisum.dao;

import java.util.List;

import com.nisum.model.Customer;

public interface CustomerDao extends Dao<Customer> {
	
	public void save(List<Customer> list);
	
}
