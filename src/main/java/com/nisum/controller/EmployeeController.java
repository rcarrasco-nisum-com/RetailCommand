package com.nisum.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

import com.nisum.model.Employee;
import com.nisum.service.EmployeeService;
import com.wordnik.swagger.annotations.Api;

@Controller
@RequestMapping("/employee")
@Api(value = "employee", description = "employee front-end!")
public class EmployeeController {

	private static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private EmployeeService service;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public List<Employee> getAll() {
		// ...
		return service.getAll();
	}
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@ResponseBody
	public Employee get(@PathVariable(value = "name") String name) {
		// ...
		return service.get(name);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Employee employee, HttpServletResponse response) {
		// ...
		logger.debug("create --> " + employee);
		service.save(employee);
		String location = ServletUriComponentsBuilder.fromCurrentRequest()
				.pathSegment("{id}").buildAndExpand(employee.getName())
				.toUriString();
		response.setHeader("Location",location);	
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@RequestBody Employee employee) {
		// ...
		logger.debug("update --> " + employee);
		service.update(employee);
	}
	
	@RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(value = "name") String name) {
		// ...
		Employee employee = service.get(name);
		if (employee != null)
			service.delete(employee);
	}
	
}
