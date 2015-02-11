package com.nisum.dao.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.nisum.dao.CustomerDao;
import com.nisum.dao.impl.CustomerDaoImpl;
import com.nisum.model.Customer;

public class CustomerDaoTest {

	public static Logger logger = LogManager.getLogger();
	
	public CustomerDao dao;
	
	public Customer pablo;
	public Customer rafael;
	public Customer roman;
	public Customer claudio;
	
	public List<Customer> list;
	
	@Before
	public void setUp() throws Exception {
		
		logger.debug("setUp");
		
		dao = new CustomerDaoImpl();
		
		list = new ArrayList<Customer>();
		
		pablo = new Customer("Pablo");
		pablo.setEmail("pdamaso@nisum.com");
		pablo.setPhone("+56 9 94600161");
		
		rafael = new Customer("Rafael");
		rafael.setEmail("rcarrasco@nisum.com");
		rafael.setPhone("+56 9 87654321");
		
		roman = new Customer("Roman");
		roman.setEmail("rsteiner@nisum.com");
		roman.setPhone("+56 9 97654321");
		
		claudio = new Customer("Claudio");
		claudio.setEmail("cgonzalez@nisum.com");
		claudio.setPhone("+56 9 88887777");
		
		list.add(pablo);
		list.add(rafael);
		list.add(roman);
		
	}

	@After
	public void tearDown() throws Exception {
		
		logger.debug("tearDown");
		
		// dao = null;
		
		// pablo = null;
		// rafael = null;
		// roman = null;
		
		// list.clear();
		// list = null;
		
	}

	@Test
	public void testSaveListOfCustomer() {
		
		logger.info("testSaveListOfCustomer");
		
		// call to method
		dao.save(list);
		
		// get the full list
		List<Customer> check = dao.getAll();

		logger.debug("list " + list);
		logger.debug("check " + check);
		
		// check the size of the list
		Assert.assertEquals("checking the size of the list", list.size(), check.size());
		
		Assert.assertEquals("checking first element: pablo.name", list.get(0).getName(), check.get(0).getName());
		Assert.assertEquals("checking second element: rafael.email", list.get(1).getEmail(), check.get(1).getEmail());
		Assert.assertEquals("checking third element: roman.phone", list.get(2).getPhone(), check.get(2).getPhone());
		
	}

	@Test
	public void testSaveCustomer() {
		
		logger.info("testSaveListOfCustomer");
		
		// call to method
		dao.save(list);
		
		// call the method
		dao.save(claudio);
		
		// get a customer by name
		Customer test = dao.get(claudio.getName());
		
		Assert.assertEquals("the customer.name must be equals to the test.name", claudio.getName(), test.getName());
		Assert.assertEquals("the customer.email must be equals to the test.email", claudio.getEmail(), test.getEmail());
		Assert.assertEquals("the customer.phone must be equals to the test.phone", claudio.getPhone(), test.getPhone());
		
		// get the full list
		List<Customer> check = dao.getAll();
		
		logger.debug("list " + list);
		logger.debug("check " + check);
		
		// check the size of the list
		Assert.assertEquals(list.size()+1, check.size());

	}

	@Test
	public void testGetAll() {

		logger.info("testGetAll");

		// saving the list
		dao.save(list);

		// call the method for the first time
		List<Customer> check = dao.getAll();

		// check the size of the list
		Assert.assertEquals(list.size(), check.size());

		Assert.assertEquals("checking first element: pablo.name", list.get(0)
				.getName(), check.get(0).getName());
		Assert.assertEquals("checking second element: rafael.email", list
				.get(1).getEmail(), check.get(1).getEmail());
		Assert.assertEquals("checking third element: roman.phone", list.get(2)
				.getPhone(), check.get(2).getPhone());

		// call to method to save pablo
		// dao.save(pablo);
		// call the method
		// check = dao.getAll();
		// check the size of the list
		// Assert.assertEquals("one element in the list", 1, check.size());

		// call to method to save rafael
		// dao.save(rafael);
		// call the method
		// check = dao.getAll();
		// check the size of the list
		// Assert.assertEquals("two elements in the list", 2, check.size());

		// call to method to save roman
		// dao.save(roman);
		// call the method
		// check = dao.getAll();
		// check the size of the list
		// Assert.assertEquals("three elements in the list", 3, check.size());


		dao.save(claudio);

		// call the method for the second time
		check = dao.getAll();

		Assert.assertEquals("list.size must be equals to check.size",
				list.size()+1, check.size());

	}

	@Test
	public void testGet() {
		
		logger.info("testGet");
		
		dao.save(list);
		
		// call the method
		Customer customer = dao.get(claudio.getName());
		
		Assert.assertEquals("getting a null customer", null, customer);
		
		dao.save(claudio);
		
		customer = dao.get(claudio.getName());
		
		Assert.assertEquals("the customer.name must be claudio", claudio.getName(), customer.getName());
		Assert.assertEquals("the customer.email must be from claudio", claudio.getEmail(), customer.getEmail());
		Assert.assertEquals("the customer.phone must be from claudio", claudio.getPhone(), customer.getPhone());
		
		customer = dao.get(rafael.getName());
		
		Assert.assertEquals("getting the rafael.name", rafael.getName(), customer.getName());
		Assert.assertEquals("getting the rafael.email", rafael.getEmail(), customer.getEmail());
		Assert.assertEquals("getting the rafael.phone", rafael.getPhone(), customer.getPhone());
		
	}

	@Test
	public void testUpdate() {
		
		logger.info("testUpdate");
		
		dao.save(list);
		
		Customer customer = dao.get(pablo.getName());
		
		Assert.assertEquals("name must be the same", pablo.getName(), customer.getName());
		Assert.assertEquals("email must be the same", pablo.getEmail(), customer.getEmail());
		Assert.assertEquals("phone must be the same", pablo.getPhone(), customer.getPhone());
		
		customer.setEmail("pablo.damaso@gmail.com");
		customer.setPhone("+56 9 61408024");
		
		dao.update(customer);
		
		Customer newer = dao.get(pablo.getName());
		
		Assert.assertEquals(customer.getName(), newer.getName());
		Assert.assertEquals(customer.getEmail(), newer.getEmail());
		Assert.assertEquals(customer.getPhone(), newer.getPhone());
		
	}

	@Test
	public void testDelete() {
		
		logger.info("testDelete");
		
		Customer customer = null;
		List<Customer> check = null;
		
		dao.save(list);
		
		customer = dao.get(rafael.getName());
		
		check = dao.getAll();
		Assert.assertEquals(list.size(), check.size());
		
		dao.delete(rafael);
		
		check = dao.getAll();
		Assert.assertEquals(list.size()-1, check.size());
		
		customer = dao.get(rafael.getName());
		Assert.assertEquals(null, customer);
		
		dao.delete(roman);
		
		check = dao.getAll();
		Assert.assertEquals(list.size()-2, check.size());
		
		customer = dao.get(rafael.getName());
		Assert.assertEquals(null, customer);
		
		customer = dao.get(pablo.getName());
		Assert.assertEquals(pablo.getEmail(), customer.getEmail());
		
		dao.delete(pablo);
		
		check = dao.getAll();
		Assert.assertEquals(list.size()-3, check.size());
		
		dao.delete(claudio);
		
		check = dao.getAll();
		Assert.assertEquals(0, check.size());
		
	}

}
