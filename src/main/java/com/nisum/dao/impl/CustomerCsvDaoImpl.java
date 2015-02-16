package com.nisum.dao.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.nisum.dao.CustomerDao;
import com.nisum.model.Customer;

@Repository
public class CustomerCsvDaoImpl implements CustomerDao {

	private static final Logger logger = LogManager.getLogger();
	
	// Delimiter used in CSV file
	private static final String NEW_LINE_SEPARATOR = "\n";

	private static final String NAME = "name";
	private static final String EMAIL = "email";
	private static final String PHONE = "phone";
	
	// CSV file header
    private static final String [] FILE_HEADER = { NAME, EMAIL, PHONE };
	
    // CSV file (path + name)
	public static final String FILE = "/tmp/customers.csv";

	// private String fileName;
	//
	// public CustomerCsvDaoImpl(String fileName) {
	// super();
	// this.fileName = fileName;
	// }
	
	private FileWriter getWriter(boolean append) throws IOException {
		return new FileWriter(FILE, append);
	}
	
	private FileReader getReader() throws FileNotFoundException {
		return new FileReader(FILE);
	}
	
	@Override
	public void save(Customer customer) {

		logger.debug("CSV save customer");
		
		// FileWriter fileWriter = null;
		Writer fileWriter = null;
		
		CSVPrinter csvFilePrinter = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		try {
			
			fileWriter = getWriter(true);
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			
			List<String> data = new ArrayList<String>();
			data.add(customer.getName());
			data.add(customer.getEmail());
			data.add(customer.getPhone());
			csvFilePrinter.printRecord(data);
			
			fileWriter.flush();
			fileWriter.close();

			csvFilePrinter.close();
			
		} catch (IOException e) {
			logger.error("can't open the file --> " + e);
			e.printStackTrace();
		}

	}

	@Override
	public void save(List<Customer> list) {

		logger.debug("CSV save list of customer");
		
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		try {
			
			fileWriter = getWriter(false);
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			csvFilePrinter.printRecord((Object[])FILE_HEADER);
			
			for (Customer customer : list) {
				
				List<String> data = new ArrayList<String>();
				data.add(customer.getName());
				data.add(customer.getEmail());
				data.add(customer.getPhone());
				csvFilePrinter.printRecord(data);
			}
			
			fileWriter.flush();
			fileWriter.close();

			csvFilePrinter.close();
			
		} catch (IOException e) {
			logger.error("can't open the file --> " + e);
			e.printStackTrace();
		}

	}

	@Override
	public List<Customer> getAll() {

		logger.debug("get all customer");
		
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER);

		List<Customer> list = new ArrayList<Customer>();
		
		try {
			
			fileReader = getReader();
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			List<CSVRecord> records = csvFileParser.getRecords();
			
			Iterator<CSVRecord> iterator = records.iterator();
			
			// ignore the first element, is the HEADER
			iterator.next();
			
			for (; iterator.hasNext();) {
				
				CSVRecord record = (CSVRecord) iterator.next();
				
				String name = record.get(NAME);
				String email = record.get(EMAIL);
				String phone = record.get(PHONE);
				
				Customer customer = new Customer(name, email, phone);
				
				list.add(customer);
			}
			
			fileReader.close();
			csvFileParser.close();
			
		} catch (FileNotFoundException e) {
			logger.error("file not found --> " + e);
			e.printStackTrace();
			
		} catch (IOException e) {
			logger.error("can't open the file --> " + e);
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public Customer get(String name) {

		logger.debug("get customer by name");

		if (name == null)
			return null;

		Customer customer = null;
		List<Customer> list = getAll();

		for (Customer element : list) {
			if (name.equalsIgnoreCase(element.getName())) {
				customer = element;
				break;
			}
		}

		return customer;
	}

	@Override
	public void update(Customer customer) {

		logger.debug("update a customer");

		List<Customer> list = getAll();
		ListIterator<Customer> iterator = list.listIterator();

		while (iterator.hasNext()) {
			Customer aux = (Customer) iterator.next();
			if (aux.equals(customer)) {
				iterator.remove();
				iterator.add(customer);
			}
		}

		save(list);
	}

	@Override
	public void delete(Customer customer) {

		logger.debug("delete a customer");

		List<Customer> list = getAll();
		list.remove(customer);
		
		save(list);
	}

}
