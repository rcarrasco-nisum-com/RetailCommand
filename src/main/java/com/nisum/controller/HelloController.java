package com.nisum.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nisum.model.Customer;
import com.wordnik.swagger.annotations.Api;

@Controller
@RequestMapping("/hello")
@Api(value = "services", description = "hello world services!")
public class HelloController {

	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {

		model.addAttribute("message", "Hello world!");
		return "hello";
	}
	
	@RequestMapping(value = "/world", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String printHello() {
		// ...
		return "Hello world!";
	}
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String printHelloYou(@PathVariable("name") String name) {
		// ...
		return "Hello " + name + "!";
	}
	
	@RequestMapping(value = "/customer", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Customer printHelloCustomer() {
		// ...
		Customer pablo = new Customer("<name>","<email>","<phone>");
		return pablo;
	}
}