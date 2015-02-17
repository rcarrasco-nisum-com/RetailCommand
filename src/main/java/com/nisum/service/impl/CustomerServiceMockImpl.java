package com.nisum.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import com.nisum.model.Customer;
import com.nisum.service.CustomerService;

public class CustomerServiceMockImpl implements CustomerService {

	private List<Customer> dao;
	
	public CustomerServiceMockImpl() {
		
		super();
		dao = new ArrayList<Customer>();
	}
	
	@Override
	public void save(Customer customer) {
		
		// save one
		dao.add(customer);
	}

	@Override
	public void save(List<Customer> list) {
		
		// save list
		dao.clear();
		dao.addAll(list);
	}

	@Override
	public List<Customer> getAll() {
		
		// get all
		return dao;
	}

	@Override
	public Customer get(String name) {
		
		if (name == null)
			return null;
		
		// get by name
		for (Customer customer : dao) {
			if (customer.getName().equalsIgnoreCase(name)) {
				return customer;
			}
		}
		
		return null;
	}

	@Override
	public void update(Customer customer) {

		// update
		ListIterator<Customer> it = dao.listIterator();
		while (it.hasNext()) {
			if (customer.equals(it.next())) {
				it.remove();
				it.add(customer);
				break;
			}
		}
	}

	@Override
	public void delete(Customer customer) {

		// delete
		ListIterator<Customer> it = dao.listIterator();
		while (it.hasNext()) {
			if (customer.equals(it.next())) {
				it.remove();
				break;
			}
		}
	}

}
