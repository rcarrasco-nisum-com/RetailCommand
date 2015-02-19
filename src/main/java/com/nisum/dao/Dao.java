package com.nisum.dao;

import java.util.List;

public interface Dao<T extends Object> {

	public void save(T t);
	public List<T> getAll();
	public T get(String name);
	public void update(T t);
	public void delete(T t);
	
}
