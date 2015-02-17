package com.nisum.app.cli.impl;

import java.util.Properties;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.nisum.app.cli.CLICommand;

@Component
public class HelpCommand implements CLICommand {

	private static Logger logger = LogManager.getLogger();
	
	@Override
	public void execute(Properties props) {

		logger.debug("HelpCommand.execute()");
		
	}

	@Override
	public Option getOption() {

		// -H --help option for HELP

		OptionBuilder.hasArg(false);
		OptionBuilder.withDescription("help");
		OptionBuilder.withLongOpt("help");

		return OptionBuilder.create("H");
	}
}
