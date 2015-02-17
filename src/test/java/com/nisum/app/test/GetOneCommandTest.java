package com.nisum.app.test;

import static org.mockito.Mockito.when;

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
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.nisum.app.cli.CLICommand;
import com.nisum.app.cli.impl.GetOneCommand;
import com.nisum.model.Customer;
import com.nisum.service.CustomerService;

public class GetOneCommandTest {

	private static Logger logger = LogManager.getLogger();

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	private CLICommand cmd;

	@Mock
	private CustomerService svc;

	private final Customer pablo = new Customer("Pablo", "pdamaso@nisum.com", "+56 9 94600161");
	private final Customer rafael = new Customer("Rafael", "rcarrasco@nisum.com", "+56 9 87654321");

	private Properties getProps(Customer customer) {

		if (customer == null)
			return null;

		Properties props = new Properties();

		props.setProperty("name", customer.getName());
		props.setProperty("email", customer.getEmail());
		props.setProperty("phone", customer.getPhone());

		return props;

	}

	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		System.setOut(new PrintStream(outContent));
		System.setErr(new PrintStream(errContent));

		cmd = new GetOneCommand();

		ReflectionTestUtils.setField(cmd, "service", svc);

	}

	@After
	public void tearDown() throws Exception {

		System.setOut(null);
		System.setErr(null);

	}

	@Test
	public void testExecuteCustomerExists() {

		// Assert.assertNotNull(svc);
		when(svc.get(pablo.getName())).thenReturn(pablo);

		logger.debug(" > testExecuteCustomerExists");

		cmd.execute(getProps(pablo));

		String msg = outContent.toString();
		Assert.assertTrue(msg.contains(pablo.toString()));
		logger.debug(" < MSG :: " + msg);

	}

	@Test
	public void testExecuteCustomerDoesNotExists() {

		// Assert.assertNotNull(svc);
		when(svc.get(rafael.getName())).thenReturn(null);

		logger.debug(" > testExecuteCustomerDoesNotExists");

		cmd.execute(getProps(rafael));

		String msg = outContent.toString();
		Assert.assertTrue(msg.contains("customer " + rafael.getName() + " doesn't exists!"));
		logger.debug(" < MSG :: " + msg);

	}

	@Test
	public void testGetOption() {

		logger.debug(" > testGetOption");

		Option getOne = cmd.getOption();

		Assert.assertFalse("it must be no args", getOne.hasArg());
		Assert.assertEquals("description must be ...", "get customer by name", getOne.getDescription());
		Assert.assertEquals("longopt must be ... ", "get", getOne.getLongOpt());
		Assert.assertEquals("opt must be ... ", "G", getOne.getOpt());

		logger.debug(" < MSG :: " + getOne.toString());
	}

}
