package com.nisum.dao.impl;

import com.nisum.dao.ProductDao;
import com.nisum.model.Product;

import java.util.List;

/**
 * Created by rcarrasco on 09-02-2015.
 */
public class ProductDaoPostgreSQLImpl implements ProductDao {
    @Override
    public List<Product> findAll() {
        return null;
    }

    @Override
    public List<Product> findByType(String type) {
        return null;
    }

    @Override
    public int create(Product product) {
        return 0;
    }

    @Override
    public int update(Product product) {
        return 0;
    }

    @Override
    public int delete(Product product) {
        return 0;
    }
}
