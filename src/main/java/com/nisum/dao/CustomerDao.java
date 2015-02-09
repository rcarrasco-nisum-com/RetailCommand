package com.nisum.dao;

import java.util.List;

import com.nisum.model.Customer;

public interface CustomerDao {
	
	public void write(List<Customer> list);
}
