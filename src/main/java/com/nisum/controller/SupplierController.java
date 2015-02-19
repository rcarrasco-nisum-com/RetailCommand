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

import com.nisum.model.Supplier;
import com.nisum.service.SupplierService;
import com.wordnik.swagger.annotations.Api;

@Controller
@RequestMapping("/supplier")
@Api(value = "supplier", description = "supplier front-end!")
public class SupplierController {

	public static final Logger logger = LogManager.getLogger();
	
	@Autowired
	private SupplierService service;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	@ResponseBody
	public List<Supplier> getAll() {
		
		return service.getAll();
	}
	
	@RequestMapping(value = "/{name}", method = RequestMethod.GET)
	@ResponseBody
	public Supplier get(@PathVariable(value="name") String name) {
		
		return service.get(name);
	}
	
	@RequestMapping(value = "", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public void create(@RequestBody Supplier supplier, HttpServletResponse response) {
		
		service.save(supplier);
		String location = ServletUriComponentsBuilder.fromCurrentRequest()
				.pathSegment("{id}").buildAndExpand(supplier.getName())
				.toUriString();
		response.setHeader("Location",location);	
	}
	
	@RequestMapping(value = "", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(Supplier supplier) {
		
		service.update(supplier);
	}
	
	@RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(value = "name") String name) {
		
		Supplier supplier = service.get(name);
		if (supplier != null) {
			service.delete(supplier);
		}
	}
}
