package com.nisum.dao.test;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.nisum.dao.ProductDao;
import com.nisum.model.Product;


/**
 * Created by rcarrasco on 10-02-2015.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/mvc-dispatcher-servlet.xml")
public class ProductDaoTest {

    private static Logger logger = LogManager.getLogger();
    private static final String path = "/tmp/products.csv";

    @Qualifier("productDaoCSVImpl")
    @Autowired
    private ProductDao productDao;

    private List<Product> productList = new ArrayList<Product>();

    @Before
    public void setUp() {

        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();

        product1.setCode("1");
        product1.setCountry("chile");
        product1.setPrice(100);
        product1.setSize("S");
        product1.setType("shirt");
        this.productList.add(product1);

        product2.setCode("2");
        product2.setCountry("chile");
        product2.setPrice(130);
        product2.setSize("M");
        product2.setType("coat");
        this.productList.add(product2);

        product3.setCode("3");
        product3.setCountry("argentina");
        product3.setPrice(300);
        product3.setSize("L");
        product3.setType("pant");
        this.productList.add(product3);

        product4.setCode("4");
        product4.setCountry("tibet");
        product4.setPrice(500);
        product4.setSize("L");
        product4.setType("hat");
        this.productList.add(product4);

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, false), "UTF-8"));
            for (Product product : productList) {
                StringBuffer line = new StringBuffer();
                line.append(product.getCode());
                line.append(",");
                line.append(product.getCountry());
                line.append(",");
                line.append(product.getPrice());
                line.append(",");
                line.append(product.getSize());
                line.append(",");
                line.append(product.getType());
                bw.write(line.toString());
                bw.newLine();
            }
            bw.flush();
            bw.close();
        }
        catch (UnsupportedEncodingException e) {}
        catch (FileNotFoundException e){}
        catch (IOException e){}
    }

    @After
    public void tearDown() {

    }

    @Test @Ignore
    public void createProduct(){

        Product productToInsert = new Product();

        productToInsert.setCode("7");
        productToInsert.setCountry("moon");
        productToInsert.setPrice(100000);
        productToInsert.setSize("K");
        productToInsert.setType("helmet");

        int code = productDao.create(productToInsert);
        Product productToFind = productDao.findByType(productToInsert.getCode());

        Assert.assertEquals(productToFind,productToInsert);
        Assert.assertEquals(1,code);
    }

    @Test @Ignore
    public void getByCode(){

        Product foundProduct = productDao.findByType(productList.get(0).getCode());
        Assert.assertEquals(productList.get(0),foundProduct);
    }

    @Test @Ignore
    public void findAll(){

        List<Product> productListCSV = productDao.findAll();
        Assert.assertEquals(this.productList,productListCSV);
    }

    @Test @Ignore
    public void Update(){

        Product product = new Product();
        product.setCode("1");
        product.setCountry("Marte");
        product.setPrice(5000.0);
        product.setSize("XXL");
        product.setType("spacesuit");

        int result = productDao.update(product);
        Product foundProduct = productDao.findByType(product.getCode());

        Assert.assertEquals(1,result);
        Assert.assertEquals(product,foundProduct);

    }

    @Test @Ignore
    public void Delete(){

        int result = productDao.delete(this.productList.get(0));

        Assert.assertEquals(1,result);
    }

}
