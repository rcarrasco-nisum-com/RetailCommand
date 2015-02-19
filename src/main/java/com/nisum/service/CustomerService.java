package com.nisum.service;

import java.util.List;

import com.nisum.model.Customer;

public interface CustomerService extends Service<Customer> {
	
	public void save(List<Customer> list);
	
}
