package com.nisum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.dao.CustomerDao;
import com.nisum.model.Customer;
import com.nisum.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerDao dao;
	
	@Override
	public void save(List<Customer> list) {
		dao.write(list);
	}

}
