package com.nisum.dao.impl;

import com.nisum.dao.ProductDao;
import com.nisum.model.Product;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rcarrasco on 09-02-2015.
 */

@Repository
public class ProductDaoCSVImpl implements ProductDao{

    public static final String CSV_SEPARATOR = ",";

    @Override
    public List<Product> findAll() {

        String csvFile = "C:\\Projects\\Onboarding\\RetailCommand\\customers.csv";
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

        String csvFile = "C:\\Projects\\Onboarding\\RetailCommand\\customers.csv";
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
    public int create(List<Product> products) {

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Projects\\Onboarding\\RetailCommand\\customers.csv", false), "UTF-8"));
            for (Product product : products) {
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
        catch (UnsupportedEncodingException e) {return 0;}
        catch (FileNotFoundException e){return 0;}
        catch (IOException e){return 0;}

        return 1;
    }

    @Override
    public int create(Product product) {

        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Projects\\Onboarding\\RetailCommand\\customers.csv", true), "UTF-8"));
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
    public int update(Product product) {

        List<Product> list = new ArrayList<Product>();
        BufferedReader reader = null;

        try {
            reader = new BufferedReader(new FileReader("C:\\Projects\\Onboarding\\RetailCommand\\customers.csv"));
            String line = null;
            Product aux = null;

            while ((line = reader.readLine()) != null) {

                if (! "".equalsIgnoreCase(line.trim())) {

                    String[] array = line.split(",");
                    if (array[0].equalsIgnoreCase(product.getCode())) {
                        aux = product;
                    }
                    else {
                        aux = new Product(array[0],array[1],Double.parseDouble(array[2]),array[3],array[4]);
                    }
                    list.add(aux);
                }

            }

            reader.close();

        } catch (FileNotFoundException e) { return 0;}
        catch (IOException e) { return 0;}

        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\Projects\\Onboarding\\RetailCommand\\customers.csv", true), "UTF-8"));

            for (Product prod : list) {

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

                writer.write(line.toString());
                writer.newLine();
            }
            writer.flush();
            writer.close();

        } catch (UnsupportedEncodingException e) {return 0;}
          catch (FileNotFoundException e) {return 0;}
          catch (IOException e) {return 0;}

        return 1;
    }

    @Override
    public int delete(Product product) {

        List<Product> list = new ArrayList<Product>();
        BufferedReader reader = null;

        try {

            reader = new BufferedReader(new FileReader("C:\\Projects\\Onboarding\\RetailCommand\\customers.csv"));
            String line = null;

            while ((line = reader.readLine()) != null) {

                String[] array = line.split(",");

                Product aux = null;
                if (!array[0].equals(product.getCode())) {
                    aux = new Product(array[0],array[1],Double.parseDouble(array[2]),array[3],array[4]);
                    list.add(aux);
                }
            }

            reader.close();

        } catch (FileNotFoundException e) {return 0;}
          catch (IOException e) {return 0;}

        this.create(list);
        return 1;
    }
}
