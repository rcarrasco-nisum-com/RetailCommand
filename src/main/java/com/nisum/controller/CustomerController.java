package com.nisum.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nisum.model.Customer;
import com.nisum.service.CustomerService;
import com.wordnik.swagger.annotations.Api;

@Controller
@RequestMapping("/customer")
@Api(value = "customer", description = "customer front-end!")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value = "/get/all", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public List<Customer> getAll() {
		// ...
		return customerService.getAll();
	}
	
	@RequestMapping(value = "/get/{name}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Customer get(@PathVariable("name") String name) {
		// ...
		return customerService.get(name);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, headers ="Content-Type=application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Customer customer, HttpServletResponse response) {
		// ...
		customerService.save(customer);
		String location = ServletUriComponentsBuilder.fromCurrentRequest()
				.pathSegment("{id}").buildAndExpand(customer.getName())
				.toUriString();
		response.setHeader("Location",location);	
	}
	
	@RequestMapping(value = "/update/{name}", method = RequestMethod.PUT, headers ="Content-Type=application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable String name, @RequestBody Customer customer) {
		// ...
		customerService.update(customer);
	}
	
	@RequestMapping(value="/delete/{name}", method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("name")String name){
		// ...
		customerService.delete(customerService.get(name));
	}
	
}