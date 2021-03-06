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
public class CreateCommand implements CLICommand {

	private static Logger logger = LogManager.getLogger();
	
	@Autowired @Qualifier("customerServiceImpl")
	private CustomerService service;
	
	@Override
	public void execute(Properties props) {
		
		logger.debug(props);
		
		if (props == null) {
			System.out.println("props [-D] is missing");
			return;
		}
			
		String name = props.getProperty("name");
		// logger.debug(name);
		String email = props.getProperty("email");
		// logger.debug(email);
		String phone = props.getProperty("phone");
		// logger.debug(phone);
		
		if (name == null || name.trim().equalsIgnoreCase("")) {
			System.out.println("-D name=<name> is missing");
			return;
		}
		if (email == null || email.trim().equalsIgnoreCase("")) {
			System.out.println("-D email=<email> is missing");
			return;
		}
		if (phone == null || phone.trim().equalsIgnoreCase("")) {
			System.out.println("-D phone=<phone> is missing");
			return;
		}
		
		Customer customer = service.get(name);
		// logger.debug(customer);
		
		if (customer != null) {

			logger.debug("updating " + customer.toString());
			
			customer.setEmail(email);
			customer.setPhone(phone);
			service.update(customer);
			
			System.out.println("updated customer --> " + customer.toString());
			
		} else {
			
			customer = new Customer(name, email, phone);
			logger.debug("creating " + customer);
			
			service.save(customer);
			
			System.out.println("new customer --> " + customer.toString());
			
		}
	}

	@Override
	public Option getOption() {
		
		// -C --create option to CREATE
		
		OptionBuilder.hasArg(false);
		OptionBuilder.withDescription("create a customer");
		OptionBuilder.withLongOpt("create");
		
		return OptionBuilder.create("C");
	}

	public CustomerService getService() {
		return service;
	}

	public void setService(CustomerService service) {
		this.service = service;
	}

}
