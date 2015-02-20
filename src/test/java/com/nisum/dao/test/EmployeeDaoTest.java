package com.nisum.dao.test;

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
import com.nisum.dao.impl.EmployeeMyBatisDaoImpl;
import com.nisum.dao.mapper.EmployeeMapper;
import com.nisum.model.Employee;

public class EmployeeDaoTest {

	private EmployeeDao dao;
	
	@Mock
	private EmployeeMapper mapper;
	
	private static Employee peter = new Employee("Peter", "peter@store.com", "23459999");
	private static Employee john = new Employee("John", "john@store.com", "23458888");
	
	private static List<Employee> list = Arrays.asList(peter, john);
	
	@Before
	public void setUp() throws Exception {
	
		MockitoAnnotations.initMocks(this);
		
		dao = new EmployeeMyBatisDaoImpl();
		
		ReflectionTestUtils.setField(dao, "mapper", mapper);
		
	}

	@After
	public void tearDown() throws Exception {
	
	}

//	@Test
//	public void testSaveListOfEmployee() {
//		
//		dao.save(list);
//		
//		Mockito.verify(mapper).save(john);
//		Mockito.verify(mapper).save(peter);
//		
//	}

	@Test
	public void testSave() {
		
		dao.save(peter);
		
		Mockito.verify(mapper).save(peter);
		
	}

	@Test
	public void testGetAll() {

		Mockito.when(mapper.getAll()).thenReturn(list);
		
		List<Employee> employees = dao.getAll();
		
		Assert.assertEquals("size", list.size(), employees.size());
		
		Assert.assertEquals("1st name", list.get(0).getName(), employees.get(0).getName());
		Assert.assertEquals("2nd email", list.get(1).getEmail(), employees.get(1).getEmail());
		
		Mockito.verify(mapper).getAll();
		
	}

	@Test
	public void testGet() {
		
		Mockito.when(mapper.get(john.getName())).thenReturn(john);
		
		Employee response = dao.get(john.getName());
		
		Assert.assertEquals("name", john.getName(), response.getName());
		Assert.assertEquals("email", john.getEmail(), response.getEmail());
		Assert.assertEquals("phone", john.getPhone(), response.getPhone());
		
		Mockito.verify(mapper).get(john.getName());
		
	}

	@Test
	public void testUpdate() {

		dao.update(peter);
		
		Mockito.verify(mapper).update(peter);
		
	}

	@Test
	public void testDelete() {
		
		dao.delete(peter);
		
		Mockito.verify(mapper).delete(peter);
		
	}

	@Test
	public void testDeleteNull() {
		
		dao.delete(null);
		
		Mockito.verify(mapper).delete(null);
		
	}
}
