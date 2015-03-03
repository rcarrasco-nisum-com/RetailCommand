package com.nisum.app.cli.impl;

import java.util.List;
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
public class GetAllCommand implements CLICommand {

	private static Logger logger = LogManager.getLogger();
	
	@Autowired @Qualifier("customerServiceImpl")
	public CustomerService service;
	
	@Override
	public void execute(Properties props) {
		
		logger.debug(props);
		
		List<Customer> list = service.getAll();
		
		for (Customer customer : list)
			System.out.println(customer.toString());
	}

	@Override
	public Option getOption() {

		// -A --all option to READ

		OptionBuilder.hasArg(false);
		OptionBuilder.withDescription("get all customers");
		OptionBuilder.withLongOpt("all");

		return OptionBuilder.create("A");
	}
}
