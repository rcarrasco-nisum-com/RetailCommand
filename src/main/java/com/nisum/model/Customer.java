package com.nisum.model;

public class Customer {

	private final String name;
	private String email;
	private String phone;

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

	public String getName() {
		return name;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Name: ").append(this.name).append("; ");
		sb.append("Email: ").append(this.email).append("; ");
		sb.append("Phone: ").append(this.phone).append(";");
		return sb.toString();
	}
}
