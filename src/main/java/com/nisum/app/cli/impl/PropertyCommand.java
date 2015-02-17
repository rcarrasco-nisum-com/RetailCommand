package com.nisum.app.cli.impl;

import java.util.Properties;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.springframework.stereotype.Component;

import com.nisum.app.cli.CLICommand;

@Component
public class PropertyCommand implements CLICommand {

	// private static Logger logger = LogManager.getLogger();
	
	@Override
	public void execute(Properties props) {
		
		// String name = props.getProperty("name");
		// logger.debug(name);
		
		// String email = props.getProperty("email");
		// logger.debug(email);
		
		// String phone = props.getProperty("phone");
		// logger.debug(phone);
	}

	@Override
	public  Option getOption() {
		
		// -D to set the properties [ name | email | phone ]
		
		OptionBuilder.withArgName("property=value");
		OptionBuilder.hasArgs(2);
		OptionBuilder.withValueSeparator();
		OptionBuilder.withDescription("set the property [ name | email | phone ]");
		
		return OptionBuilder.create("S");
	}

}
