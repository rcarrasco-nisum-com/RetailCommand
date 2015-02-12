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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nisum.dao.CustomerDao;
import com.nisum.model.Customer;

@Service
public class CustomerDaoImpl implements CustomerDao {

	public static Logger logger = LogManager.getLogger();
	
	public static final String CSV_SEPARATOR = ",";
	
	public static final String CSV_FILE = "/tmp/customer.csv";
	
	public static final String CSV_ENCODING = "UTF-8";
	
	private BufferedWriter openFileToWrite(boolean append) throws UnsupportedEncodingException,
			FileNotFoundException {

		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				CSV_FILE, append), CSV_ENCODING));
	}

	private BufferedReader openFileToRead() throws FileNotFoundException {

		return new BufferedReader(new FileReader(CSV_FILE));
	}
	
	public void save(List<Customer> list) {

		logger.debug("saving list of customer");

		BufferedWriter writer = null;
		
		try {

			writer = openFileToWrite(false);

			for (Customer customer : list) {

				StringBuffer line = new StringBuffer();

				line.append(customer.getName());
				line.append(CSV_SEPARATOR);
				line.append(customer.getEmail());
				line.append(CSV_SEPARATOR);
				line.append(customer.getPhone());

				writer.write(line.toString());
				writer.newLine();

			}

			writer.flush();
			writer.close();

		} catch (UnsupportedEncodingException e) {
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

	}

	@Override
	public void save(Customer customer) {
		
		logger.debug("saving a customer");
		
		BufferedWriter writer = null;
		
		try {

			writer = openFileToWrite(true);

			StringBuffer line = new StringBuffer();

			line.append(customer.getName());
			line.append(CSV_SEPARATOR);
			line.append(customer.getEmail());
			line.append(CSV_SEPARATOR);
			line.append(customer.getPhone());

			writer.write(line.toString());
			writer.newLine();

			writer.flush();
			writer.close();

		} catch (UnsupportedEncodingException e) {
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
	}

	@Override
	public List<Customer> getAll() {
		
		logger.debug("getting all customer");
		
		List<Customer> list = new ArrayList<Customer>();
		BufferedReader reader = null;
		
		try {
			
			reader = openFileToRead();
			String line = null;
			while ((line = reader.readLine()) != null) {
				
				logger.debug("line >> " + line);
				
				if (!"".equalsIgnoreCase(line.trim())) {
					String[] array = line.split(",");
					Customer customer = new Customer(array[0],array[1],array[2]);
					list.add(customer);
				}
				
		    }
			
			reader.close();
			
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
		return list;
	}

	@Override
	public Customer get(String name) {
		
		logger.debug("get a customer by name");
		
		BufferedReader reader = null; 
		Customer customer = null;
		
		try {
			
			reader = openFileToRead();
			String line = null;

			while ((line = reader.readLine()) != null) {
				String[] array = line.split(",");
				if (array[0].equalsIgnoreCase(name)) {
					customer = new Customer(array[0],array[1],array[2]);
					return customer;
				}
		    }
			
			reader.close();
			
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
		return customer;
	}

	@Override
	public void update(Customer customer) {
		
		logger.debug("updating a customer");
		
		List<Customer> list = new ArrayList<Customer>();
		BufferedReader reader = null;
		
		try {
			
			reader = openFileToRead();
			String line = null;
			Customer aux = null;
			
			while ((line = reader.readLine()) != null) {
				
				if (! "".equalsIgnoreCase(line.trim())) {

					String[] array = line.split(",");
					if (array[0].equalsIgnoreCase(customer.getName())) {
						aux = customer;
					}
					else {
						aux = new Customer(array[0],array[1],array[2]);
					}
					list.add(aux);
				}
			
		    }
			
			reader.close();
			
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
		save(list);
		
	}

	@Override
	public void delete(Customer customer) {
		
		logger.debug("deleting a customer");
		
		List<Customer> list = new ArrayList<Customer>();
		BufferedReader reader = null;
		
		try {
			
			reader = openFileToRead();
			String line = null;

			while ((line = reader.readLine()) != null) {
				
				String[] array = line.split(",");
				
				Customer aux = null;
				if (!array[0].equalsIgnoreCase(customer.getName())) {
					aux = new Customer(array[0],array[1],array[2]);
					list.add(aux);
				}
				
		    }
			
			reader.close();
			
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
		save(list);
		
	}
	
}
