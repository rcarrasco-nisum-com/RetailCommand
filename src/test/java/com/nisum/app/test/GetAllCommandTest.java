package com.nisum.app.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
import com.nisum.app.cli.impl.GetAllCommand;
import com.nisum.model.Customer;
import com.nisum.service.CustomerService;

public class GetAllCommandTest {

	private static Logger logger = LogManager.getLogger();

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

	private CLICommand cmd;

	@Mock
	private CustomerService svc;

	private final Customer pablo = new Customer("Pablo", "pdamaso@nisum.com", "+56 9 94600161");
	private final Customer rafael = new Customer("Rafael", "rcarrasco@nisum.com", "+56 9 87654321");
	private final Customer roman = new Customer("Steiner, Roman", "rsteiner@nisum.com", "+56 9 67891234");

	private final List<Customer> full = Arrays.asList(pablo, rafael, roman);
	private final List<Customer> empty = new ArrayList<Customer>();

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

		cmd = new GetAllCommand();

		ReflectionTestUtils.setField(cmd, "service", svc);

	}

	@After
	public void tearDown() throws Exception {

		System.setOut(null);
		System.setErr(null);

	}

	@Test
	public void testExecuteGetAll() {

		logger.debug(" > testExecuteGetAll");

		Mockito.when(svc.getAll()).thenReturn(full);

		cmd.execute(getProps(null));

		String msg = outContent.toString();
		Assert.assertTrue(msg.contains(pablo.toString()));
		Assert.assertTrue(msg.contains(rafael.toString()));
		Assert.assertTrue(msg.contains(roman.toString()));
	}

	@Test
	public void testExecuteGetAllEmptyList() {

		logger.debug(" > testExecuteGetAllEmptyList");

		Mockito.when(svc.getAll()).thenReturn(empty);

		cmd.execute(getProps(null));

		String msg = outContent.toString();
		Assert.assertTrue(msg.isEmpty());
	}

	@Test
	public void testGetOption() {

		logger.debug(" > testGetOption");

		Option getAll = cmd.getOption();

		Assert.assertFalse("it must be no args", getAll.hasArg());
		Assert.assertEquals("description must be ...", "get all customers", getAll.getDescription());
		Assert.assertEquals("longopt must be ... ", "all", getAll.getLongOpt());
		Assert.assertEquals("opt must be ... ", "A", getAll.getOpt());

		logger.debug(" < MSG :: " + getAll.toString());
	}

}
