package com.nisum.model;

import java.io.Serializable;

public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String email;
	private String phone;

	public Customer() {
		super();
	}
	
	public Customer(String name) {
		super();
		this.name = name;
	}

	public Customer(String name, String email, String phone) {
		super();
		this.name = name;
		this.email = email;
		this.phone = phone;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Name: ").append(this.name).append("; ");
		sb.append("Email: ").append(this.email).append("; ");
		sb.append("Phone: ").append(this.phone).append(";");
		return sb.toString();
	}
}
