package com.nisum.app;

import java.util.List;

import org.junit.Assert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.nisum.dao.CustomerDao;
import com.nisum.dao.impl.CustomerMyBatisDaoImpl;
import com.nisum.model.Customer;

public class AppMyBatis {

	private static ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		applicationContext = new ClassPathXmlApplicationContext("spring-app-config.xml");
		AppMyBatis cli = new AppMyBatis();
		cli.run(applicationContext, args);
	}

	private void run(ApplicationContext applicationContext, String[] args) {

		CustomerDao dao = applicationContext.getBean(CustomerMyBatisDaoImpl.class);
		Assert.assertNotNull(dao);
		
		// CustomerMapper mapper = applicationContext.getBean(CustomerMapper.class);
		// Assert.assertNotNull(mapper);
		
		// ReflectionTestUtils.setField(dao, "customerMapper", mapper);
		
		Customer roman = new Customer("Steiner, Roman", "rsteiner@nisum.com", "+56 9 67890000");
		print("### save");
		dao.save(roman);
		print(roman);
		
		print("### get");
		Customer pablo = dao.get("Pablo");
		print(pablo);
		
		print("### update");
		pablo.setEmail("pablo.damaso@gmail.com");
		pablo.setPhone("61408024");
		dao.update(pablo);
		print(pablo);
		
		print("### get all");
		List<Customer> list = dao.getAll();
		for (Customer customer : list) {
			print(customer);
		}
	}

	private void print(String msg) {
		System.out.println(msg);
	}
	
	private void print(Customer customer) {
		System.out.printf("get a customer by name : %s \n", customer.toString());
	}

}
