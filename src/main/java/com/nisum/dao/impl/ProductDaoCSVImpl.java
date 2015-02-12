package com.nisum.dao.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.nisum.dao.ProductDao;
import com.nisum.model.Product;

/**
 * Created by rcarrasco on 09-02-2015.
 */

@Repository
public class ProductDaoCSVImpl implements ProductDao{

    public static final String CSV_SEPARATOR = ",";

    @Override
    public List<Product> findAll() {

        String csvFile = "/tmp/products.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] products = new String[0];
        List<Product> productList= new ArrayList<Product>();

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                products = line.split(cvsSplitBy);

                Product product = new Product();
                product.setCode(products[0]);
                product.setCountry(products[1]);
                product.setPrice(Double.parseDouble(products[2]));
                product.setSize(products[3]);
                product.setType(products[4]);
                productList.add(product);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return productList;
    }

    @Override
    public Product findByType(String type) {

        String csvFile = "/tmp/products.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] products = new String[0];
        Product product = new Product();

        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                products = line.split(cvsSplitBy);

                if(products[0].equals(type)){
                    product.setCode(products[0]);
                    product.setCountry(products[1]);
                    product.setPrice(Double.parseDouble(products[2]));
                    product.setSize(products[3]);
                    product.setType(products[4]);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return product;
    }

    @Override
    public int create(Product product) {
        return 0;
    }

    @Override
    public int create(List<Product> products) {

        try {

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/tmp/products.csv", false), "UTF-8"));

            for (Product product : products) {

                StringBuffer line = new StringBuffer();

                line.append(product.getCode());
                line.append(CSV_SEPARATOR);
                line.append(product.getCountry());
                line.append(CSV_SEPARATOR);
                line.append(product.getPrice());
                line.append(CSV_SEPARATOR);
                line.append(product.getSize());
                line.append(CSV_SEPARATOR);
                line.append(product.getType());

                bw.write(line.toString());
                bw.newLine();

            }

            bw.flush();
            bw.close();

        }
        catch (UnsupportedEncodingException e) {return 0;}
        catch (FileNotFoundException e){return 0;}
        catch (IOException e){return 0;}
        return 1;
    }

    @Override
    public int update(Product product) {

        try {

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/tmp/products.csv", false), "UTF-8"));

                StringBuffer line = new StringBuffer();

                line.append(product.getCode());
                line.append(CSV_SEPARATOR);
                line.append(product.getCountry());
                line.append(CSV_SEPARATOR);
                line.append(product.getPrice());
                line.append(CSV_SEPARATOR);
                line.append(product.getSize());
                line.append(CSV_SEPARATOR);
                line.append(product.getType());

                bw.write(line.toString());
                bw.newLine();

            bw.flush();
            bw.close();

        }
        catch (UnsupportedEncodingException e) {return 0;}
        catch (FileNotFoundException e){return 0;}
        catch (IOException e){return 0;}
        return 1;
    }

    @Override
    public int delete(Product product) {
        return 0;
    }
}
