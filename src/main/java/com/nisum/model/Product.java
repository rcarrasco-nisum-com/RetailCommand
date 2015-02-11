package com.nisum.model;

/**
 * Created by rcarrasco on 09-02-2015.
 */
public class Product {

    private String code;
    private String type;
    private double price;
    private String size;
    private String country;

    public Product(String code, String type, Double price, String size, String country) {
    }

    public Product() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;

        Product product = (Product) o;

        if (Double.compare(product.price, price) != 0) return false;
        if (code != null ? !code.equals(product.code) : product.code != null) return false;
        if (country != null ? !country.equals(product.country) : product.country != null) return false;
        if (size != null ? !size.equals(product.size) : product.size != null) return false;
        if (type != null ? !type.equals(product.type) : product.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = code != null ? code.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        temp = Double.doubleToLongBits(price);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        return result;
    }
}
