package com.nisum.app.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import com.nisum.app.CLICustomer;
import com.nisum.app.cli.CLICommand;
import com.nisum.app.cli.impl.GetAllCommand;
import com.nisum.model.Customer;
import com.nisum.service.CustomerService;

// ContextConfiguration
// RunWith(MockitoJUnitRunner.class)
public class CLICustomerMockTest {

	private static final Logger logger = LogManager.getLogger();
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
	
	@Mock
	private CustomerService svc;
	
	// private CLICommand getAll;
	
	private final Customer pablo = new Customer("Damaso, Pablo", "pdamaso@nisum.com", "+56 9 94600161");
	private final Customer rafael = new Customer("Carrasco, Rafael", "rcarrasco@nisum.com", "+56 9 87654321");
	private final Customer roman = new Customer("Steiner, Roman", "rsteiner@nisum.com", "+56 9 67891234");

	private final List<Customer> full = Arrays.asList(pablo, rafael, roman);
	
	// Mock
	// private ApplicationContext applicationContext;
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		// applicationContext = new ClassPathXmlApplicationContext("classpath*:spring-app-config.xml");
		
		System.setOut(new PrintStream(outContent));
	    System.setErr(new PrintStream(errContent));

		// ReflectionTestUtils.setField(applicationContext.getBean(GetAllCommand.class), "service", svc);
		
	    // getAll = new GetAllCommand();
	    // ReflectionTestUtils.setField(getAll, "service", svc);
	    
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
		
	}

	@Test
	public void testGetAll() {
		
		logger.debug(" > testGetAll");
		
		// Mockito.when( applicationContext.getBean(GetAllCommand.class) ).thenReturn( (GetAllCommand)getAll );
		// Mockito.when( svc.getAll() ).thenReturn(full);
		
		String[] args = {"-A"};
		CLICustomer.main(args);
		
		String msg = outContent.toString();
		Assert.assertTrue( msg.contains(pablo.toString()) );
		Assert.assertTrue( msg.contains(rafael.toString()) );
		Assert.assertTrue( msg.contains(roman.toString()) );
		
		logger.debug(" > MSG ::" + msg);
	
	}
}
