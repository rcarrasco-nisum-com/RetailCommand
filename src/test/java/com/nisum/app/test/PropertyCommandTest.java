package com.nisum.app.test;

import org.apache.commons.cli.Option;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.nisum.app.cli.CLICommand;
import com.nisum.app.cli.impl.PropertyCommand;

public class PropertyCommandTest {

	private static final Logger logger = LogManager.getLogger();
	
	private CLICommand cmd;
	
	@Before
	public void setUp() throws Exception {
		
		cmd = new PropertyCommand();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetOption() {

		logger.debug(" > testGetOption");

		Option property = cmd.getOption();

		Assert.assertTrue("it must have args", property.hasArgs());
		Assert.assertTrue("it must have value separator", property.hasValueSeparator());
		Assert.assertEquals("description must be ...", "set the property [ name | email | phone ]", property.getDescription());
		Assert.assertEquals("opt must be ... ", "S", property.getOpt());

		logger.debug(" < MSG :: " + property.toString());
	}

}
