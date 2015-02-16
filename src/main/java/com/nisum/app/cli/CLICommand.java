package com.nisum.app.cli;

import java.util.Properties;

import org.apache.commons.cli.Option;

public interface CLICommand {

	public Option getOption();
	
	public void execute(Properties props);
	
}
