package com.nisum.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nisum.model.Customer;
import com.nisum.service.CustomerService;

public class AppMain {
	
	private static final Logger logger = LogManager.getLogger(AppMain.class);
	
	public static void main(String[] args) {
	
		logger.info("Initializing Spring context.");
        @SuppressWarnings("resource")
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-app-config.xml");
        logger.info("Spring context initialized.");
        
        CustomerService service = applicationContext.getBean(CustomerService.class);
        
		List<Customer> customerList = new ArrayList<Customer>();
		
		Customer pablo = new Customer("Pablo");
		pablo.setEmail("pdamaso@nisum.com");
		pablo.setPhone("+56 9 94600161");
		customerList.add(pablo);
		
		Customer rafael = new Customer("Rafael");
		rafael.setEmail("rcarrasco@nisum.com");
		rafael.setPhone("+56 9 87654321");
		customerList.add(rafael);
		
		Customer roman = new Customer("Roman");
		roman.setEmail("rsteiner@nisum.com");
		roman.setPhone("+56 9 97654321");
		customerList.add(roman);
		
		for(Customer customer : customerList) {
			logger.info(customer.toString());
		}
		
		service.save(customerList);
		
	}
	
}
