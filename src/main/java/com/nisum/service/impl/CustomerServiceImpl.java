package com.nisum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.nisum.dao.CustomerDao;
import com.nisum.model.Customer;
import com.nisum.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired 
	@Qualifier("customerCsvDaoImpl")
	CustomerDao dao;
	
	@Override
	public void save(Customer customer) {
		dao.save(customer);
	}

	@Override
	public List<Customer> getAll() {
		return dao.getAll();
	}

	@Override
	public Customer get(String name) {
		return dao.get(name);
	}

	@Override
	public void update(Customer customer) {
		dao.update(customer);
	}

	@Override
	public void delete(Customer customer) {
		dao.delete(customer);
	}

	@Override
	public void save(List<Customer> list) {
		dao.save(list);
	}

}
