package com.nisum.dao;

import com.nisum.model.Product;

import java.util.List;

/**
 * Created by rcarrasco on 09-02-2015.
 */
public interface ProductDao {

    public List <Product> findAll();

    public List <Product> findByType(String type);

    public int create(Product product);

    public int update(Product product);

    public int delete(Product product);
}
