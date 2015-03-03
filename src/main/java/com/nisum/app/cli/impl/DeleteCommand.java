package com.nisum.app.cli.impl;

import java.util.Properties;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nisum.app.cli.CLICommand;
import com.nisum.model.Customer;
import com.nisum.service.CustomerService;

@Component
public class DeleteCommand implements CLICommand {

	private static Logger logger = LogManager.getLogger();
	
	@Autowired @Qualifier("customerServiceImpl")
	public CustomerService service;
	
	@Override
	public void execute(Properties props) {
		
		logger.debug(props);
		
		if (props == null) {
			System.out.println("props [-D] is missing");
			return;
		}
		
		String name = props.getProperty("name");
		
		if (name == null || name.trim().equalsIgnoreCase("")) {
			System.out.println("-D name=<name> is missing");
			return;
		}
			
		Customer customer = service.get(name);
		
		if (customer != null) {
			
			service.delete(customer);
			System.out.printf("deleted customer --> %s", customer.toString());
		}
		else {
			
			System.out.printf("customer %s doesn't exists!", name);
		}
		
	}

	@Override
	public Option getOption() {

		// -D --delete option to DELETE

		OptionBuilder.hasArg(false);
		OptionBuilder.withDescription("delete customer by name");
		OptionBuilder.withLongOpt("delete");

		return OptionBuilder.create("D");
	}
}
