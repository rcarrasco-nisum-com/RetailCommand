package com.nisum.model;

import java.io.Serializable;

public class Employee implements Serializable {


	private static final long serialVersionUID = 1L;

	private String name;
	private String email;
	private String phone;	

	public Employee() {
		super();
	}

	public Employee(String name, String email, String phone) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
	}
	
	
}
