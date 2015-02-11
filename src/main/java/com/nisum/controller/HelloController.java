package com.nisum.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nisum.dao.ProductDao;
import com.nisum.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class HelloController {

    @Autowired
    @Qualifier("productDaoCSVImpl")
    ProductDao productDao;

	@RequestMapping()//method = RequestMethod.GET
	public String printWelcome(ModelMap model) {

		model.addAttribute("message", "Hello world!");
        List<Product> productListCSV = productDao.findAll();
		return "hello";
	}

    @RequestMapping(value = "/gsonExample",
            method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    String getParamTypesActiveList() throws IOException {


        List<Product> productList = new ArrayList<Product>();
        Product product1 = new Product();
        Product product2 = new Product();
        Product product3 = new Product();
        Product product4 = new Product();

        product1.setCode("1");
        product1.setCountry("chile");
        product1.setPrice(100);
        product1.setSize("S");
        product1.setType("shirt");
        productList.add(product1);

        product2.setCode("2");
        product2.setCountry("chile");
        product2.setPrice(130);
        product2.setSize("M");
        product2.setType("coat");
        productList.add(product2);

        product3.setCode("3");
        product3.setCountry("argentina");
        product3.setPrice(300);
        product3.setSize("L");
        product3.setType("pant");
        productList.add(product3);

        product4.setCode("4");
        product4.setCountry("tibet");
        product4.setPrice(500);
        product4.setSize("L");
        product4.setType("hat");
        productList.add(product4);
/*        ParameterTypeJson parameterTypeJson = new ParameterTypeJson();
        parameterTypeJson.setiTotalDisplayRecords(parameterTypeTO.size());
        parameterTypeJson.setiTotalRecords(parameterTypeTO.size());
        parameterTypeJson.setAaData(parameterTypeTO); */

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        return gson.toJson(productList);
    }
}