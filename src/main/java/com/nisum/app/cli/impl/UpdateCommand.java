package com.nisum.app.cli.impl;

import java.util.Properties;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nisum.app.cli.CLICommand;
import com.nisum.model.Customer;
import com.nisum.service.CustomerService;

@Component
public class UpdateCommand implements CLICommand {

	private static Logger logger = LogManager.getLogger();
	
	@Autowired
	public CustomerService service;
	
	@Override
	public void execute(Properties props) {
		
		logger.debug(props);
		
		String name = props.getProperty("name");
		// logger.debug(name);
		String email = props.getProperty("email");
		// logger.debug(email);
		String phone = props.getProperty("phone");
		// logger.debug(phone);
		
		if (name == null) {
			logger.error("-D name=<name> is missing");
			return;
		}
		
		if (email == null) {
			logger.error("-D email=<email> is missing");
			return;
		}
			
		if (phone == null) {
			logger.error("-D phone=<phone> is missing");
			return;
		}
		
		Customer customer = service.get(name);
		
		if (customer != null) {

			customer.setEmail(email);
			customer.setPhone(phone);
			service.update(customer);
			
			System.out.println("updated customer --> " + customer.toString());
			
		} else {
			
			customer = new Customer(name, email, phone);
			service.save(customer);
			
			System.out.println("new customer --> " + customer.toString());
			
		}
	
	}

	@Override
	public Option getOption() {
		
		// -U --update option to UPDATE
		
		OptionBuilder.hasArg(false);
		OptionBuilder.withDescription("update a customer");
		OptionBuilder.withLongOpt("update");
		
		return OptionBuilder.create("U");
	}

}
