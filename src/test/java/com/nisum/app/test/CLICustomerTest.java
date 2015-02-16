package com.nisum.app.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import com.nisum.app.CLICustomer;

// RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-app-config.xml")
public class CLICustomerTest {

	public static Logger logger = LogManager.getLogger();
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	@Before
	public void setUp() throws Exception {
		System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(null);
	    System.setErr(null);
	}
	
	@Test
	public void testHelp() {
		
		String[] args = {"-H"};
		CLICustomer.main(args);
		String msg = outContent.toString();
		Assert.assertTrue( msg.contains("usage: CLICustomer") );
		logger.debug("testHelp --> " + msg);
	}

	@Test
	public void testGetAll() {
		
		String[] args = {"-A"};
		CLICustomer.main(args);
		String msg = outContent.toString();
		// Assert.assertTrue( msg.contains("Name: Pablo; Email: pdamaso@nisum.com; Phone: +56 9 94600161;") );
		Assert.assertTrue( msg.contains("Name: Rafael; Email: rcarrasco@nisum.com; Phone: +56 9 87654321;") );
		Assert.assertTrue( msg.contains("Name: Roman; Email: rsteiner@nisum.com; Phone: +56 9 97654321;") );
		logger.debug("testGetAll --> " + msg);
	}
	
	@Test
	public void testCreate() {
	
		String[] args = {"-C", "-S", "name=Steiner, Roman", "-S", "email=new.mail@company.com", "-S", "phone=+56 9 98769876"};
		CLICustomer.main(args);
		String msg = outContent.toString();
		Assert.assertTrue( msg.contains("new customer --> ") );
		Assert.assertTrue( msg.contains("Name: Steiner, Roman; Email: new.mail@company.com; Phone: +56 9 98769876;") );
		logger.debug("testCreate --> " + msg);
		
		// outContent.reset();
		//
		// String[] args2 = {"-D", "-S", "name=Steiner, Roman"};
		// msg = outContent.toString();
		// Assert.assertTrue( msg.contains("deleted customer --> ") );
	}
	
	@Test
	public void testUpdate() {
	
		String[] firstUpdate = {"-U", "-S", "name=Pablo", "-S", "email=pablo.damaso@gmail.com", "-S", "phone=+56 9 61408024"};
		CLICustomer.main(firstUpdate);
		String msg = outContent.toString();
		Assert.assertTrue( msg.contains("updated customer --> ") );
		Assert.assertTrue( msg.contains("Name: Pablo; Email: pablo.damaso@gmail.com; Phone: +56 9 61408024;") );
		logger.debug("testUpdate 1 --> " + msg);

		String[] secondUpdate = {"-U", "-S", "name=Pablo", "-S", "email=pdamaso@nisum.com", "-S", "phone=+56 9 94600161"};
		CLICustomer.main(secondUpdate);
		msg = outContent.toString();
		Assert.assertTrue( msg.contains("updated customer --> ") );
		Assert.assertTrue( msg.contains("Name: Pablo; Email: pdamaso@nisum.com; Phone: +56 9 94600161;") );
		logger.debug("testUpdate 2 --> " + msg);
	}
	
	@Test
	public void testDelete() {
	
		String[] firstDelete = {"-D", "-S", "name=Steiner, Roman"};
		CLICustomer.main(firstDelete);
		String msg = outContent.toString();
		Assert.assertTrue( msg.contains("deleted customer --> ") );
		
		String[] secondDelete = {"-D", "-S", "name=Steiner, Roman"};
		CLICustomer.main(secondDelete);
		msg = outContent.toString();
		Assert.assertTrue( msg.contains("doesn't exists!") );

		logger.debug("testDelete --> " + msg);
	}
	
	@Test
	public void testGet() {
	
		String[] firstGet = {"-G", "-S", "name=Rafael"};
		CLICustomer.main(firstGet);
		String msg = outContent.toString();
		Assert.assertTrue( msg.contains("Name: Rafael; Email: rcarrasco@nisum.com; Phone: +56 9 87654321;") );
		
		String[] secondGet = {"-G", "-S", "name=Claudio"};
		CLICustomer.main(secondGet);
		msg = outContent.toString();
		Assert.assertTrue( msg.contains("doesn't exists!") );

		logger.debug("testGet --> " + msg);
	}
}
