package com.nisum.dao.test;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.nisum.dao.SupplierDao;
import com.nisum.model.Supplier;
import com.nisum.service.SupplierService;
import com.nisum.service.impl.SupplierServiceImpl;

public class SupplierServiceTest {

	private static Logger logger = LogManager.getLogger();
	
	private SupplierService service;
	
	@Mock
	private SupplierDao dao;
	
	private static Supplier sodimac = new Supplier("Sodimac", "mail@sodimac.com", "22334400");
	private static Supplier easy = new Supplier("Easy", "mail@easy.com", "22446600");
	private static Supplier nisum = new Supplier("Nisum", "mail@nisum.com", "22112200");
	
	private static List<Supplier> list = Arrays.asList(sodimac, easy);
	
	@Before
	public void setUp() throws Exception {
		
		logger.debug("setUp");
		
		MockitoAnnotations.initMocks(this);
		
		service = new SupplierServiceImpl();
		
		ReflectionTestUtils.setField(service, "dao", dao);
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testSaveListOfSupplier() {

		logger.debug("testSaveListOfSupplier");
		
		service.save(list);
		
		Mockito.verify(dao).save(list);
		
	}

	@Test
	public void testSave() {
		
		logger.debug("testSave");
		
		service.save(easy);
		
		Mockito.verify(dao).save(easy);
		
	}

	@Test
	public void testGetAllWithData() {

		logger.debug("testGetAllWithData");
		
		Mockito.when(dao.getAll()).thenReturn(list);
		
		List<Supplier> suppliers = service.getAll();
		
		Assert.assertEquals("list size don't match", list.size(), suppliers.size());
		
		Assert.assertEquals("comparing 1st element", list.get(0).getName(), suppliers.get(0).getName() );
		Assert.assertEquals("comparing 2nd element", list.get(1).getEmail(), suppliers.get(1).getEmail() );
		
		Mockito.verify(dao).getAll();
		
	}

	@Test
	public void testGetAllWithoutData() {

		logger.debug("testGetAllWithoutData");
		
		Mockito.when(dao.getAll()).thenReturn(null);
		
		List<Supplier> suppliers = service.getAll();
		
		Assert.assertNull("list must be null", suppliers);
		
		Mockito.verify(dao).getAll();
		
	}
	
	@Test
	public void testGetExisting() {
		
		logger.debug("testGetExisting");
		
		Mockito.when(dao.get(sodimac.getName())).thenReturn(sodimac);
		
		Supplier response = service.get(sodimac.getName());
		
		Assert.assertEquals("names must be equals", sodimac.getName(), response.getName());
		Assert.assertEquals("emails must be equals", sodimac.getEmail(), response.getEmail());
		Assert.assertEquals("phones must be equals", sodimac.getPhone(), response.getPhone());
		
		Mockito.verify(dao).get(sodimac.getName());
		
	}

	@Test
	public void testGetNotExisting() {
		
		logger.debug("testGetNotExisting");
		
		Mockito.when(dao.get(nisum.getName())).thenReturn(null);
		
		Supplier response = service.get(nisum.getName());
		
		Assert.assertNull("supplier must be null", response);
		
		Mockito.verify(dao).get(nisum.getName());
		
	}

	@Test
	public void testUpdate() {
		
		logger.debug("testUpdate");
		
		service.update(easy);
		
		Mockito.verify(dao).update(easy);
		
	}

	@Test
	public void testDelete() {
		
		logger.debug("testDelete");
		
		service.delete(easy);
		
		Mockito.verify(dao).delete(easy);
		
	}

}
