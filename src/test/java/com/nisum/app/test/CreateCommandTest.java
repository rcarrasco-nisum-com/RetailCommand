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
import com.nisum.app.cli.impl.CreateCommand;
import com.nisum.model.Customer;
import com.nisum.service.CustomerService;

public class CreateCommandTest {

	private static Logger logger = LogManager.getLogger();

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	private CLICommand cmd;

	@Mock
	private CustomerService svc;

	private final Customer roman = new Customer("Steiner, Roman", "rsteiner@nisum.com", "+56 9 67891234");

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

		cmd = new CreateCommand();

		ReflectionTestUtils.setField(cmd, "service", svc);

	}

	@After
	public void tearDown() throws Exception {

		System.setOut(null);
		System.setErr(null);

	}

	@Test
	public void testExecuteCreateNewCustomer() {

		logger.debug(" > testExecuteNewCustomer");
		
		Mockito.when( svc.get( roman.getName() ) ).thenReturn(null);
		
		cmd.execute(getProps(roman));
		
		String msg = outContent.toString();
		Assert.assertTrue("must be a new customer", msg.contains("new customer --> ") );
		Assert.assertTrue("must be .", msg.contains(roman.toString()) );
		
		Mockito.verify(svc).save(roman);
		
	}

	@Test 
	public void testExecuteCreateExistingCustomer() {

		logger.debug(" > testExecuteCreateExistingCustomer");
		
		Mockito.when( svc.get( roman.getName() ) ).thenReturn(roman);
		
		// Customer data = new Customer(roman.getName(), "my.new@mail.com", "+56 9 67896789");
		
		cmd.execute(getProps(roman));
		
		String msg = outContent.toString();
		Assert.assertTrue("must be a existing customer", msg.contains("updated customer --> ") );
		Assert.assertTrue("must be .", msg.contains(roman.toString()) );
		
		Mockito.verify(svc).update(roman);
		
	}

	@Test
	public void testExecuteCreateWithoutProperties() {

		logger.debug(" > testExecuteCreateWithoutProperties");
		
		Customer customer = null;
		
		cmd.execute(getProps(null));
		
		String msg = outContent.toString();
		Assert.assertTrue("no props", msg.contains("props [-D] is missing") );
		
		Mockito.verify(svc, Mockito.never()).get(Mockito.anyString());
		Mockito.verify(svc, Mockito.never()).save(customer);
		Mockito.verify(svc, Mockito.never()).update(customer);
		
	}
	
	@Test
	public void testExecuteCreateWithoutName() {

		logger.debug(" > testExecuteCreateWithoutName");
		
		Customer customer = new Customer("");
		
		cmd.execute(getProps(customer));
		
		String msg = outContent.toString();
		Assert.assertTrue("no name", msg.contains("-D name=<name> is missing") );
		
		Mockito.verify(svc, Mockito.never()).get(customer.getName());
		Mockito.verify(svc, Mockito.never()).save(customer);
		Mockito.verify(svc, Mockito.never()).update(customer);
		
	}
	
	@Test
	public void testGetOption() {

		logger.debug(" > testGetOption");

		Option create = cmd.getOption();

		Assert.assertFalse("it must be no args", create.hasArg());
		Assert.assertEquals("description must be ...", "create a customer", create.getDescription());
		Assert.assertEquals("longopt must be ... ", "create", create.getLongOpt());
		Assert.assertEquals("opt must be ... ", "C", create.getOpt());

		logger.debug(" < MSG :: " + create.toString());
	}

}
