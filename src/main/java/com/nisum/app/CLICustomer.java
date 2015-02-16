package com.nisum.app;

import java.util.List;
import java.util.Properties;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.nisum.model.Customer;
import com.nisum.service.CustomerService;

@Service
public class CLICustomer {

	private static final String HELP = "H";
	private static final String GET_ALL = "A";
	private static final String GET_ONE = "G";
	private static final String DELETE = "D";
	private static final String UPDATE = "U";
	private static final String CREATE = "C";
	private static final String SET = "S";
	
	public static Logger logger = LogManager.getLogger();
	
	@SuppressWarnings({ "static-access", "resource" })
	public static void main(String[] args) {

		// for (String arg : args)
		// System.out.println("arg --> " + arg);
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring-app-config.xml");
		CustomerService service = applicationContext.getBean(CustomerService.class);
		
		// Options
		Options options = new Options();

		// -D to set the properties [ name | email | phone ]
		Option optionProperty = OptionBuilder.withArgName("property=value")
				.hasArgs(2).withValueSeparator()
                .withDescription( "set the property [ name | email | phone ]" )
                .create( SET );
		options.addOption(optionProperty);
		
		// -C --create option to CREATE
		Option optionCreate = OptionBuilder.hasArg(false)
				.withDescription("create a customer")
				.withLongOpt("create").create(CREATE);
		options.addOption(optionCreate);
		
		// -U --update option to UPDATE
		Option optionUpdate = OptionBuilder.hasArg(false)
				.withDescription("update a customer")
				.withLongOpt("update").create(UPDATE);
		options.addOption(optionUpdate);
		
		// -D --delete option to DELETE
		Option optionDelete = OptionBuilder.hasArg(false)
				.withDescription("delete customer by name")
				.withLongOpt("delete").create(DELETE);
		options.addOption(optionDelete);

		// -G --get option to READ
		Option optionGet = OptionBuilder.hasArg(false)
				.withDescription("get customer by name")
				.withLongOpt("get").create(GET_ONE);
		options.addOption(optionGet);

		// -A --all option to READ
		Option optionGetAll = OptionBuilder.hasArg(false)
				.withDescription("get all customers")
				.withLongOpt("all").create(GET_ALL);
		options.addOption(optionGet);
		options.addOption(optionGetAll);
		
		// -H --help option for HELP
		options.addOption(HELP, "help", false, "help");

		CommandLineParser parser = new BasicParser();
		HelpFormatter help = new HelpFormatter();

		try {

			CommandLine cmd = parser.parse(options, args);

			String name = null;
			String email = null;
			String phone = null;
			
			if (cmd.hasOption(SET)) {
				
				Properties properties = cmd.getOptionProperties(SET);
				logger.debug("properties --> " + properties);
				
				name = properties.getProperty("name");
				logger.debug(name);
				
				email = properties.getProperty("email");
				logger.debug(email);
				
				phone = properties.getProperty("phone");
				logger.debug(phone);
				
			}
			
			if (cmd.hasOption(CREATE) || cmd.hasOption(UPDATE)) {
				
				if (name == null) 
					throw new ParseException("-D name=<name> is missing");
				
				if (email == null)
					throw new ParseException("-D email=<email> is missing");
				
				if (phone == null)
					throw new ParseException("-D phone=<phone> is missing");
				
			}
			
			if (cmd.hasOption(CREATE) || cmd.hasOption(UPDATE)) {
				
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

			if (cmd.hasOption(DELETE)) {

				if (name == null) 
					throw new ParseException("-D name=<name> is missing");
				
				Customer customer = service.get(name);
				
				if (customer != null) {
					service.delete(customer);
					System.out.println("deleted customer --> " + customer.toString());
				}
				else
					System.out.println("customer " + name + " doesn't exists!");
				
			}

			if (cmd.hasOption(GET_ONE)) {

				if (name == null) 
					throw new ParseException("-D name=<name> is missing");
				
				Customer customer = service.get(name);
				
				if (customer != null) 
					System.out.println(customer.toString());
				else 
					System.out.println("customer " + name + " doesn't exists!");
				
			}
			
			if (cmd.hasOption(GET_ALL)) {

				List<Customer> list = service.getAll();
				
				for (Customer customer : list)
					System.out.println(customer.toString());
				
			}
			
			if (cmd.hasOption(HELP)) {

				help.printHelp("CLICustomer", options);
				
			}

		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e);
		} finally {

			// ...
		}

	}

}
