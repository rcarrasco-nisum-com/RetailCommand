package com.nisum.controller;

import com.nisum.dao.ProductDao;
import com.nisum.model.Product;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rcarrasco on 10-02-2015.
 */

@FixMethodOrder(MethodSorters.JVM)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class ProductDaoTest {

    @Qualifier("productDaoCSVImpl")
    @Autowired
    private ProductDao productDao;

    @Ignore
    @Test
    public void createProduct(){

        List<Product> products = new ArrayList<Product>();
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();

        product1.setCode("1");
        product1.setCountry("chile");
        product1.setPrice(100);
        product1.setSize("S");
        product1.setType("shirt");
        products.add(product1);

        product2.setCode("2");
        product2.setCountry("chile");
        product2.setPrice(130);
        product2.setSize("M");
        product2.setType("coat");
        products.add(product2);

        product3.setCode("3");
        product3.setCountry("argentina");
        product3.setPrice(300);
        product3.setSize("L");
        product3.setType("pant");
        products.add(product3);

        product4.setCode("4");
        product4.setCountry("tibet");
        product4.setPrice(500);
        product4.setSize("L");
        product4.setType("hat");
        products.add(product4);

        int code = productDao.create(products);
        Assert.assertEquals(1,code);
    }

    @Test @Ignore
    public void getByCode(){

        Product product1 = new Product();
        product1.setCode("1");
        product1.setCountry("chile");
        product1.setPrice(100.0);
        product1.setSize("S");
        product1.setType("shirt");

        Product foundProduct = productDao.findByType(product1.getCode());
        
        Assert.assertEquals(product1.getCode(),foundProduct.getCode());
    }

    @Test @Ignore
    public void findAll(){

        List<Product> products = new ArrayList<Product>();

        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();

        product1.setCode("1");
        product1.setCountry("chile");
        product1.setPrice(100);
        product1.setSize("S");
        product1.setType("shirt");
        products.add(product1);

        product2.setCode("2");
        product2.setCountry("chile");
        product2.setPrice(130);
        product2.setSize("M");
        product2.setType("coat");
        products.add(product2);

        product3.setCode("3");
        product3.setCountry("argentina");
        product3.setPrice(300);
        product3.setSize("L");
        product3.setType("pant");
        products.add(product3);

        product4.setCode("4");
        product4.setCountry("tibet");
        product4.setPrice(500);
        product4.setSize("L");
        product4.setType("hat");
        products.add(product4);

        List<Product> productListCSV = productDao.findAll();
        Assert.assertEquals(products,productListCSV);
    }

    @Test @Ignore
    public void Update(){

        Product product1 = new Product();
        product1.setCode("5");
        product1.setCountry("Marte");
        product1.setPrice(5000.0);
        product1.setSize("XXL");
        product1.setType("spacesuit");

        Assert.assertEquals(1, productDao.update(product1));
    }

}
