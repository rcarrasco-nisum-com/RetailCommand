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
import com.nisum.dao.impl.SupplierMyBatisDaoImpl;
import com.nisum.dao.mapper.SupplierMapper;
import com.nisum.model.Supplier;

public class SupplierDaoTest {

	private static Logger logger = LogManager.getLogger();
	
	private SupplierDao dao;
	
	@Mock
	private SupplierMapper mapper;
	
	private static Supplier sodimac = new Supplier("Sodimac", "mail@sodimac.com", "22334400");
	private static Supplier easy = new Supplier("Easy", "mail@easy.com", "22446600");
	private static Supplier nisum = new Supplier("Nisum", "mail@nisum.com", "22112200");
	
	private static List<Supplier> list = Arrays.asList(sodimac, easy);
	
	@Before
	public void setUp() throws Exception {
		
		logger.debug("setUp");
		
		MockitoAnnotations.initMocks(this);
		
		dao = new SupplierMyBatisDaoImpl();
		
		ReflectionTestUtils.setField(dao, "mapper", mapper);
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

//	@Test
//	public void testSaveListOfSupplier() {
//
//		logger.debug("testSaveListOfSupplier");
//		logger.debug(list);
//		
//		dao.save(list);
//		
//		Mockito.verify(mapper).save(sodimac);
//		Mockito.verify(mapper).save(easy);
//		
//	}

//	@Test
//	public void testSaveNullListOfSupplier() {
//
//		logger.debug("testSaveNullListOfSupplier");
//		
//		List<Supplier> empty = null;
//		dao.save(empty);
//		
//		Mockito.verify(mapper, Mockito.never()).save(Mockito.any(Supplier.class));
//		
//	}
	
	@Test
	public void testSave() {
		
		logger.debug("testSave");
		
		dao.save(sodimac);
		
		Mockito.verify(mapper).save(sodimac);
		
	}

	@Test
	public void testSaveNull() {
		
		logger.debug("testSaveNull");
		
		Supplier empty = null;
		dao.save(empty);
		
		Mockito.verify(mapper).save(empty);
		
	}
	
	@Test
	public void testGetAllWithData() {

		logger.debug("testGetAllWithData");
		
		Mockito.when(mapper.getAll()).thenReturn(list);
		
		List<Supplier> suppliers = dao.getAll();
		
		Assert.assertEquals("list size don't match", list.size(), suppliers.size());
		
		Assert.assertEquals("comparing 1st element", list.get(0).getName(), suppliers.get(0).getName() );
		Assert.assertEquals("comparing 2nd element", list.get(1).getEmail(), suppliers.get(1).getEmail() );
		
		Mockito.verify(mapper).getAll();
		
	}

	@Test
	public void testGetAllWithoutData() {

		logger.debug("testGetAllWithoutData");
		
		Mockito.when(mapper.getAll()).thenReturn(null);
		
		List<Supplier> suppliers = dao.getAll();
		
		Assert.assertNull("list must be null", suppliers);
		
		Mockito.verify(mapper).getAll();
		
	}
	
	@Test
	public void testGetExisting() {
		
		logger.debug("testGetExisting");
		
		Mockito.when(mapper.get(sodimac.getName())).thenReturn(sodimac);
		
		Supplier response = dao.get(sodimac.getName());
		
		Assert.assertEquals("names must be equals", sodimac.getName(), response.getName());
		Assert.assertEquals("emails must be equals", sodimac.getEmail(), response.getEmail());
		Assert.assertEquals("phones must be equals", sodimac.getPhone(), response.getPhone());
		
		Mockito.verify(mapper).get(sodimac.getName());
		
	}

	@Test
	public void testGetNotExisting() {
		
		logger.debug("testGetNotExisting");
		
		Mockito.when(mapper.get(nisum.getName())).thenReturn(null);
		
		Supplier response = dao.get(nisum.getName());
		
		Assert.assertNull("supplier must be null", response);
		
		Mockito.verify(mapper).get(nisum.getName());
		
	}

	@Test 
	public void testUpdate() {
		
		logger.debug("testUpdate");
		
		dao.update(sodimac);
		
		Mockito.verify(mapper).update(sodimac);
		
	}

	@Test 
	public void testDelete() {
		
		logger.debug("testDelete");
		
		dao.delete(sodimac);
		
		Mockito.verify(mapper).delete(sodimac);
		
	}

}
