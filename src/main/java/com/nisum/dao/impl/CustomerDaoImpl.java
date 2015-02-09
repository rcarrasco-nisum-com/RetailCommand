package com.nisum.dao.impl;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.nisum.dao.CustomerDao;
import com.nisum.model.Customer;

@Service
public class CustomerDaoImpl implements CustomerDao {

	public static final String CSV_SEPARATOR = ",";
	
	public void write(List<Customer> list) {
		
		try {
			
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("products.csv", false), "UTF-8"));
		
			for (Customer customer : list) {
				
				StringBuffer line = new StringBuffer();
				
				line.append(customer.getName());
				line.append(CSV_SEPARATOR);
				line.append(customer.getEmail());
				line.append(CSV_SEPARATOR);
				line.append(customer.getPhone());
				
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
	
}
