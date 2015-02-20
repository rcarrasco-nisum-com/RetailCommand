package com.nisum.service.test;

import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.nisum.dao.EmployeeDao;
import com.nisum.model.Employee;
import com.nisum.service.EmployeeService;
import com.nisum.service.impl.EmployeeServiceImpl;

public class EmployeeServiceTest {

	private EmployeeService service;
	
	@Mock
	private EmployeeDao dao;
	
	private static Employee peter = new Employee("Peter", "peter@store.com", "23459999");
	private static Employee john = new Employee("John", "john@store.com", "23458888");
	
	private static List<Employee> list = Arrays.asList(peter, john);
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		service = new EmployeeServiceImpl();
		
		ReflectionTestUtils.setField(service, "dao", dao);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSaveEmployee() {
		
		service.save(peter);
		
		Mockito.verify(dao).save(peter);
		
	}

	@Test
	public void testGetAll() {
		
		Mockito.when(dao.getAll()).thenReturn(list);
		
		List<Employee> employees = service.getAll();
		
		Assert.assertEquals("size of the list must be equals", list.size(), employees.size());
		
		Assert.assertEquals("names of the 1st employee must be equals", list.get(0).getName(), employees.get(0).getName() );
		Assert.assertEquals("emails of the 2nd employee must be equals", list.get(1).getName(), employees.get(1).getName() );
		
		Mockito.verify(dao).getAll();
		
	}

	@Test
	public void testGet() {

		Mockito.when(dao.get(peter.getName())).thenReturn(peter);
		
		Employee response = service.get(peter.getName());
		
		Assert.assertEquals("name", peter.getName(), response.getName());
		Assert.assertEquals("email", peter.getEmail(), response.getEmail());
		Assert.assertEquals("phone", peter.getPhone(), response.getPhone());
		
		Mockito.verify(dao).get(peter.getName());
		
	}

	@Test
	public void testUpdate() {

		service.update(john);
		
		Mockito.verify(dao).update(john);
		
	}

	@Test
	public void testDelete() {
		
		service.delete(john);
		
		Mockito.verify(dao).delete(john);
		
	}

//	@Test
//	public void testSaveListOfEmployee() {
//		
//		service.save(list);
//		
//		Mockito.verify(dao).save(peter);
//		Mockito.verify(dao).save(john);
//		
//	}

}
