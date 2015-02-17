package com.nisum.app.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Properties;

import org.apache.commons.cli.Option;
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

import com.nisum.app.cli.CLICommand;
import com.nisum.app.cli.impl.DeleteCommand;
import com.nisum.model.Customer;
import com.nisum.service.CustomerService;

public class DeleteCommandTest {
	
	private static Logger logger = LogManager.getLogger();

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	private CLICommand cmd;

	@Mock
	private CustomerService svc;

	// private final Customer pablo = new Customer("Pablo", "pdamaso@nisum.com", "+56 9 94600161");
	// private final Customer rafael = new Customer("Rafael", "rcarrasco@nisum.com", "+56 9 87654321");
	private final Customer roman = new Customer("Steiner, Roman", "rsteiner@nisum.com", "+56 9 67891234");

	// private final List<Customer> full = Arrays.asList(pablo, rafael, roman);

	private Properties getProps(Customer customer) {

		if (customer == null)
			return null;

		Properties props = new Properties();

		props.setProperty("name", customer.getName() != null ? customer.getName() : "");
		props.setProperty("email", customer.getEmail() != null ? customer.getEmail() : "");
		props.setProperty("phone", customer.getPhone() != null ? customer.getPhone() : "");

		return props;

	}

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));

		cmd = new DeleteCommand();

		ReflectionTestUtils.setField(cmd, "service", svc);

	}

	@After
	public void tearDown() throws Exception {

		System.setOut(null);
		System.setErr(null);

	}

	@Test
	public void testExecuteDeleteExistingCustomer() {

		logger.debug(" > testExecuteDeleteExistingCustomer");
		
		Mockito.when( svc.get(roman.getName()) ).thenReturn(roman);
		
		cmd.execute(getProps(roman));
		
		String msg = outContent.toString();
		Assert.assertTrue( msg.contains("deleted customer --> ") );
		Assert.assertTrue( msg.contains(roman.toString()) );
		
		Mockito.verify( svc, Mockito.times(1) ).get(roman.getName());
		Mockito.verify( svc, Mockito.times(1) ).delete(roman);
	}

	@Test
	public void testExecuteDeleteNotExistingCustomer() {

		logger.debug(" > testExecuteDeleteNotExistingCustomer");
		
		Mockito.when( svc.get(roman.getName()) ).thenReturn(null);
		
		cmd.execute(getProps(roman));
		
		String msg = outContent.toString();
		Assert.assertTrue( msg.contains("customer " + roman.getName() + " doesn't exists!") );

		Mockito.verify( svc, Mockito.times(1) ).get(roman.getName());
		Mockito.verify( svc, Mockito.never() ).delete(roman);
	
	}
	
	@Test
	public void testExecuteDeleteWithoutName() {

		logger.debug(" > testExecuteDeleteWithoutName");
		
		Customer customer = new Customer("");
		
		cmd.execute(getProps(customer));
		
		String msg = outContent.toString();
		Assert.assertTrue("no name", msg.contains("-D name=<name> is missing") );
		
		Mockito.verify(svc, Mockito.never()).get(customer.getName());
		Mockito.verify(svc, Mockito.never()).delete(customer);
		
	}
	
	@Test
	public void testGetOption() {

		logger.debug(" > testGetOption");

		Option delete = cmd.getOption();

		Assert.assertFalse("it must be no args", delete.hasArg());
		Assert.assertEquals("description must be ...", "delete customer by name", delete.getDescription());
		Assert.assertEquals("longopt must be ... ", "delete", delete.getLongOpt());
		Assert.assertEquals("opt must be ... ", "D", delete.getOpt());

		logger.debug(" < MSG :: " + delete.toString());
	}
}
