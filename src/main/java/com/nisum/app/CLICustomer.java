package com.nisum.app;

import java.util.List;

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

import com.nisum.model.Customer;
import com.nisum.service.CustomerService;

public class CLICustomer {

	public static Logger logger = LogManager.getLogger();
	private static ApplicationContext applicationContext;
	private static CustomerService service;
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {

		applicationContext = new ClassPathXmlApplicationContext("spring-app-config.xml");
		service = applicationContext.getBean(CustomerService.class);

		// Options
		Options options = new Options();

		// -c create
		Option create = OptionBuilder.withArgName("name> <email> <phone")
				.hasArgs(3).withValueSeparator(' ')
				.withDescription("create a customer")
				.withLongOpt("create").create("c");
		options.addOption(create);

		// -u update
		Option update = OptionBuilder.withArgName("name> <email> <phone")
				.hasArgs(3).withValueSeparator(' ')
				.withDescription("update a customer")
				.withLongOpt("update").create("u");
		options.addOption(update);

		// -d delete
		Option delete = OptionBuilder.withArgName("name").hasArg()
				.withLongOpt("delete")
				.withDescription("delete customer by name").create("d");
		options.addOption(delete);

		// -g get
		Option get = OptionBuilder.withArgName("name").hasArg()
				.withLongOpt("get").withDescription("get customer by name")
				.create("g");
		options.addOption(get);

		// -a all
		options.addOption("a", "all", false, "get all customers");
		// -h help
		options.addOption("h", "help", false, "help");

		options.addOption("t", "test", false, "say hello");

		CommandLineParser parser = new BasicParser();
		HelpFormatter help = new HelpFormatter();

		try {

			CommandLine cmd = parser.parse(options, args);

			if (cmd.hasOption("c")) {
				
				String[] data = cmd.getOptionValues("c");
				String name = data[0];
				Customer customer = service.get(name);
				if (customer != null) { 
					service.update(customer);
					System.out.println("updated customer --> " + customer.toString());
				} else {
					customer = new Customer(name, data[1], data[2]);
					service.save(customer);
					System.out.println("new customer --> " + customer.toString());
				}
			}

			if (cmd.hasOption("u")) {

				String[] data = cmd.getOptionValues("u");
				String name = data[0];
				Customer customer = service.get(name);
				if (customer != null) { 
					service.update(customer);
					System.out.println("updated customer --> " + customer.toString());
				} else {
					customer = new Customer(name, data[1], data[2]);
					service.save(customer);
					System.out.println("new customer --> " + customer.toString());
				}
			}

			if (cmd.hasOption("d")) {

				String name = cmd.getOptionValue("d");
				Customer customer = service.get(name);
				if (customer != null) {
					service.delete(customer);
					System.out.println("deleted customer --> " + customer.toString());
				}
				else {
					System.out.println("customer " + name + " doesn't exists!");
				}
			}

			if (cmd.hasOption("a")) {

				List<Customer> list = service.getAll();
				for (Customer customer : list) {
					System.out.println(customer.toString());
				}

			}

			if (cmd.hasOption("g")) {

				String name = cmd.getOptionValue("g");
				Customer customer = service.get(name);
				if (customer != null)
					System.out.println(customer.toString());
				else
					System.out.println("customer " + name + " doesn't exists!");

			}

			if (cmd.hasOption("t")) {

				System.out.println("hello");
			}
			
			if (cmd.hasOption("h")) {

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
