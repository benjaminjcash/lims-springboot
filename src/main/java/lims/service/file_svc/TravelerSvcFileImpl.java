package com.mycompany.frs_maven.service.file_svc;
import com.mycompany.frs_maven.domain.Traveler;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.frs_maven.exceptions.RecordNotFoundException;
import com.mycompany.frs_maven.service.ITravelerSvc;
import com.mycompany.frs_maven.service.StringBuilder;

public class TravelerSvcFileImpl implements ITravelerSvc {
	static private Logger logger = LogManager.getLogger();
	
	public Traveler fetchProfile(String username) {
		Traveler traveler = new Traveler();
		try {
			traveler = getRecord(username);
		}
		catch(RecordNotFoundException e) {
			logger.error(e.getMessage());
			return null;
		}
		catch(IOException e) {
			logger.error(e.getMessage());
			return null;
		}
		catch(ClassNotFoundException e) {
			logger.error(e.getMessage());
			return null;
		}
		return traveler;
	}
	
	public boolean createProfile(Traveler traveler) throws IOException, ClassNotFoundException {
		boolean didWrite = true;
		try {
			didWrite = addRecord(traveler);
		}
		catch(RecordNotFoundException e) {
			logger.error(e.getMessage());
			didWrite = false;
		}
		catch(IOException e) {
			logger.error(e.getMessage());
			didWrite = false;
		}
		catch(ClassNotFoundException e) {
			logger.error(e.getMessage());
			didWrite = false;
		}
		return didWrite;
	}
	
	public boolean deleteProfile(String username) {
		boolean didDelete;
		try {
			didDelete = deleteRecord(username);
		}
		catch(IOException e) {
			logger.error(e.getMessage());
			didDelete = false;
		}
		catch(ClassNotFoundException e) {
			logger.error(e.getMessage());
			didDelete = false;
		}
		catch(RecordNotFoundException e) {
			logger.error(e.getMessage());
			didDelete = false;
		}
		return didDelete;
	}
	
	public boolean updateProfile(Traveler traveler) {
		boolean didUpdate = true;
		return didUpdate;
	}
	
	public ArrayList<Traveler> fetchAllProfiles() {
		ArrayList<Traveler> travelers = new ArrayList<Traveler>();
		try {
			travelers = getRecords();
		}
		catch(Exception e) {
			logger.error(e);
		}
		return travelers;
	}
	
	public boolean addPaymentInformation(String username, String creditCardNumber, String expirationDate) {
		boolean didWrite = true;
		Traveler toBeUpdated = new Traveler();
		try {
			toBeUpdated = getRecord(username);
		}
		catch(RecordNotFoundException e) {
			logger.error(e.getMessage());
			didWrite = false;
		}
		catch(IOException e) {
			logger.error(e.getMessage());
			didWrite = false;
		}
		catch(ClassNotFoundException e) {
			logger.error(e.getMessage());
			didWrite = false;
		}
		
		deleteProfile(username);
		toBeUpdated.setCreditCardNumber(creditCardNumber);
		toBeUpdated.setExpirationDate(expirationDate);

		try {
			addRecord(toBeUpdated);
		}
		catch(RecordNotFoundException e) {
			logger.error(e.getMessage());
			didWrite = false;
		}
		catch(IOException e) {
			logger.error(e.getMessage());
			didWrite = false;
		}
		catch(ClassNotFoundException e) {
			logger.error(e.getMessage());
			didWrite = false;
		}
		
		return didWrite;
	}
	
	public ArrayList<Traveler> getRecords() throws IOException, ClassNotFoundException, RecordNotFoundException {
		ArrayList<Traveler> travelers = new ArrayList<Traveler>();
		// retrieve data
 		return travelers;
	}
	
	public boolean addRecords(ArrayList<Traveler> data) throws IOException, ClassNotFoundException {
		boolean success = true;
		// write data
		return success;
	}
	
	private Traveler getRecord(String username) throws IOException, ClassNotFoundException, RecordNotFoundException {
		Traveler requestedTraveler = new Traveler();
		// retrieve data
		return requestedTraveler;
	}
	
	private boolean addRecord(Traveler data) throws IOException, ClassNotFoundException, RecordNotFoundException {
		boolean success = true;
		// write data
		return success;
	}
	
	private boolean deleteRecord(String username) throws IOException, ClassNotFoundException, RecordNotFoundException {
		boolean success = true;
		// delete data
		return success;
	}
	
	public void printAllTravelers() throws IOException, ClassNotFoundException, RecordNotFoundException {
		ArrayList<Traveler> allTravelers = this.fetchAllProfiles();
		
		// define lambda expression
		StringBuilder builder = (String label, String value) -> {
			String str = label + ": " + value;
			return str;
		};
		
		// build list of traveler names
		ArrayList<String> travelerNames = new ArrayList<String>();
		for(int i = 0; i < allTravelers.size(); i++) {
			Traveler traveler = allTravelers.get(i);
			travelerNames.add(traveler.getName());
		}
		
		// sort list
		Stream<String> stream = travelerNames.stream().sorted();
		List<String> list = stream.collect(Collectors.toList());
		
		// print list
		for(int i = 0; i < list.size(); i++) {
			String label = (i + 1) + ". ";
			System.out.println(builder.build(label, list.get(i)));
		}
	}
}
