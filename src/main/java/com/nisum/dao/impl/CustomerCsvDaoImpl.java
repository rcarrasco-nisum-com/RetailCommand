package com.nisum.dao.impl;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.nisum.dao.CustomerDao;
import com.nisum.model.Customer;

@Service
public class CustomerCsvDaoImpl implements CustomerDao {

	private static final Logger logger = LogManager.getLogger();
	
	// Delimiter used in CSV file
	private static final String NEW_LINE_SEPARATOR = "\n";
	
	// CSV file header
	private static final Object[] FILE_HEADER = { "name", "email", "phone" };

	//CSV file header
    private static final String [] FILE_HEADER_MAPPING = { "name", "email", "phone" };
	
	public static final String FILE = "/tmp/customers.csv";
	
	@Override
	public void save(Customer customer) {

		logger.debug("CSV save customer");
		
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		try {
			
			fileWriter = new FileWriter(FILE, true);
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
			e.printStackTrace();
			logger.error(e);
		}

	}

	@Override
	public void save(List<Customer> list) {

		logger.debug("CSV save list of customer");
		
		FileWriter fileWriter = null;
		CSVPrinter csvFilePrinter = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR);

		try {
			
			fileWriter = new FileWriter(FILE, false);
			csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
			csvFilePrinter.printRecord(FILE_HEADER);
			
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
			e.printStackTrace();
			logger.error(e);
		}

	}

	@Override
	public List<Customer> getAll() {

		logger.debug("get all customer");
		
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

		List<Customer> list = new ArrayList<Customer>();
		
		try {
			
			fileReader = new FileReader(FILE);
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			List<CSVRecord> records = csvFileParser.getRecords();
			
			for (int i = 1; i < records.size(); i++) {
				
				CSVRecord record = records.get(i);
				
				String name = record.get(FILE_HEADER_MAPPING[0]);
				String email = record.get(FILE_HEADER_MAPPING[1]);
				String phone = record.get(FILE_HEADER_MAPPING[2]);
				
				Customer customer = new Customer(name, email, phone);
				
				list.add(customer);
			}
			
			fileReader.close();
			csvFileParser.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		return list;
	}

	@Override
	public Customer get(String name) {

		logger.debug("get customer by name");
		
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

		Customer customer = null;
		
		try {
			
			fileReader = new FileReader(FILE);
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			List<CSVRecord> records = csvFileParser.getRecords();
			
			for (int i = 1; i < records.size(); i++) {
				
				CSVRecord record = records.get(i);
				
				if (name.trim().equalsIgnoreCase( record.get(FILE_HEADER_MAPPING[0]).trim() )) {
					
					name = record.get(FILE_HEADER_MAPPING[0]);
					String email = record.get(FILE_HEADER_MAPPING[1]);
					String phone = record.get(FILE_HEADER_MAPPING[2]);
					
					customer = new Customer(name, email, phone);
					break;
				}
				
			}
			
			fileReader.close();
			csvFileParser.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		return customer;
	}

	@Override
	public void update(Customer customer) {

		logger.debug("update a customer");
		
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

		List<Customer> list = new ArrayList<Customer>();
		Customer aux = null;
		
		try {
			
			fileReader = new FileReader(FILE);
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			List<CSVRecord> records = csvFileParser.getRecords();
			
			for (int i = 1; i < records.size(); i++) {
				
				CSVRecord record = records.get(i);
				
				if (customer.getName().trim().equalsIgnoreCase( record.get(FILE_HEADER_MAPPING[0]).trim() )) {
					
					aux = customer;
					
				}
				else {
					
					String name = record.get(FILE_HEADER_MAPPING[0]);
					String email = record.get(FILE_HEADER_MAPPING[1]);
					String phone = record.get(FILE_HEADER_MAPPING[2]);
					
					customer = new Customer(name, email, phone);
				}
				
				list.add(aux);
			}
			
			fileReader.close();
			csvFileParser.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		save(list);
		
	}

	@Override
	public void delete(Customer customer) {

		logger.debug("delete a customer");
		
		FileReader fileReader = null;
		CSVParser csvFileParser = null;
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING);

		List<Customer> list = new ArrayList<Customer>();
		
		try {
			
			fileReader = new FileReader(FILE);
			csvFileParser = new CSVParser(fileReader, csvFileFormat);
			List<CSVRecord> records = csvFileParser.getRecords();
			
			Customer aux = null;
			
			for (int i = 1; i < records.size(); i++) {
				
				CSVRecord record = records.get(i);
				
				if (!customer.getName().trim().equalsIgnoreCase( record.get(FILE_HEADER_MAPPING[0]).trim() )) {
					
					String name = record.get(FILE_HEADER_MAPPING[0]);
					String email = record.get(FILE_HEADER_MAPPING[1]);
					String phone = record.get(FILE_HEADER_MAPPING[2]);
					
					aux = new Customer(name, email, phone);
					list.add(aux);
				}
								
			}
			
			fileReader.close();
			csvFileParser.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e);
		}
		
		save(list);
		
	}

}
