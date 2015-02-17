package com.nisum.app.test;

import org.apache.commons.cli.Option;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.nisum.app.cli.CLICommand;
import com.nisum.app.cli.impl.HelpCommand;

public class HelpCommandTest {

	private static final Logger logger = LogManager.getLogger();
	
	private CLICommand cmd;
	
	@Before
	public void setUp() throws Exception {
		
		cmd = new HelpCommand();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetOption() {

		logger.debug(" > testGetOption");

		Option help = cmd.getOption();

		Assert.assertFalse("it must be no args", help.hasArg());
		Assert.assertEquals("description must be ...", "help", help.getDescription());
		Assert.assertEquals("longopt must be ... ", "help", help.getLongOpt());
		Assert.assertEquals("opt must be ... ", "H", help.getOpt());

		logger.debug(" < MSG :: " + help.toString());
	}

}
