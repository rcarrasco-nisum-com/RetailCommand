package com.nisum.app.test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.nisum.app.CLICustomer;

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

	@Test @Ignore
	public void testMain() {
		
		String[] args = {"-t"};
		CLICustomer.main(args);
		String msg = outContent.toString();
		Assert.assertTrue( msg.contains("hello") );
		logger.debug(msg);
	}
	
	@Test
	public void testHelp() {
		
		String[] args = {"-h"};
		CLICustomer.main(args);
		String msg = outContent.toString();
		Assert.assertTrue( msg.contains("usage: CLICustomer") );
		logger.debug(msg);
	}

}
