package com.nisum.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nisum.app.cli.CLICommand;
import com.nisum.app.cli.impl.CreateCommand;
import com.nisum.app.cli.impl.DeleteCommand;
import com.nisum.app.cli.impl.GetAllCommand;
import com.nisum.app.cli.impl.GetOneCommand;
import com.nisum.app.cli.impl.HelpCommand;
import com.nisum.app.cli.impl.PropertyCommand;
import com.nisum.app.cli.impl.UpdateCommand;

public class CLICustomer {

	private static final String OPTION_HELP = "H";
	private static final String OPTION_SET = "S";
	
	private static Logger logger = LogManager.getLogger();

	private Map<Option, CLICommand> map;

	private Options options;
	
	public CLICustomer() {
		super();
		map = new HashMap<Option, CLICommand>();
		options = new Options();
	}
	
	public static void main(String[] args) {
		
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-app-config.xml");
		CLICustomer cli = new CLICustomer();
		cli.run(applicationContext, args);
		
	}
	
	private void addCommand(CLICommand command) {
		
		Option option = command.getOption();
		map.put(option, command);
		options.addOption(option);
		
	}

	public void run(ApplicationContext applicationContext, String[] args) {

		// for (String arg : args)
		// System.out.println("arg --> " + arg);
		
		// Options
		addCommand(applicationContext.getBean(PropertyCommand.class));
		addCommand(applicationContext.getBean(CreateCommand.class));
		addCommand(applicationContext.getBean(UpdateCommand.class));
		addCommand(applicationContext.getBean(DeleteCommand.class));
		addCommand(applicationContext.getBean(GetOneCommand.class));
		addCommand(applicationContext.getBean(GetAllCommand.class));
		addCommand(applicationContext.getBean(HelpCommand.class));

		CommandLineParser parser = new BasicParser();
		HelpFormatter HELP = new HelpFormatter();
	
		try {

			CommandLine cmd = parser.parse(options, args);
			
			Properties properties = cmd.getOptionProperties(OPTION_SET);
			
			Option[] opts = cmd.getOptions();
			
			for (Option option : opts ) {
				
				map.get(option).execute(properties);
				
			}
			
			if (cmd.hasOption(OPTION_HELP)) {

				HELP.printHelp("CLICustomer", options);
				
			}

		} catch (ParseException e) {
			e.printStackTrace();
			logger.error(e);
		} finally {

			// ...
		}

		
	}

}
