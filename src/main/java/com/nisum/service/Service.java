package com.nisum.service;

import java.util.List;

public interface Service<T extends Object> {

	public void save(T t);
	public List<T> getAll();
	public T get(String name);
	public void update(T t);
	public void delete(T t);
	
}
