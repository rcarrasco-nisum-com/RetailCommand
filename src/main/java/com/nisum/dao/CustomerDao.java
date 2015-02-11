package com.nisum.dao;

import java.util.List;

import com.nisum.model.Customer;

public interface CustomerDao {
	
	public void save(Customer customer);
	public void save(List<Customer> list);
	public List<Customer> getAll();
	public Customer get(String name);
	public void update(Customer customer);
	public void delete(Customer customer);
	
}
