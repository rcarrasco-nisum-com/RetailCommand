package com.nisum.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.nisum.dao.CustomerDao;
import com.nisum.dao.mapper.CustomerMapper;
import com.nisum.model.Customer;

@Repository
public class CustomerMyBatisDaoImpl implements CustomerDao {

	@Autowired @Qualifier("customerMapper")
	private CustomerMapper mapper;

	@Override
	public void save(Customer customer) {
		// ...
		mapper.save(customer);
	}

	@Override
	public void save(List<Customer> list) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Customer> getAll() {
		// ...
		return mapper.getAll();
	}

	@Override
	public Customer get(String name) {
		// ...
		return mapper.get(name);
	}

	@Override
	public void update(Customer customer) {
		// ...
		mapper.update(customer);
	}

	@Override
	public void delete(Customer customer) {
		// ...
		mapper.delete(customer);
	}

}
