package com.mycompany.frs_maven.service.file_svc;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import com.mycompany.frs_maven.exceptions.RecordNotFoundException;
import com.mycompany.frs_maven.service.IFlightSvc;
import com.mycompany.frs_maven.domain.Flight;
import com.mycompany.frs_maven.service.StringBuilder;

public class FlightSvcFileImpl implements IFlightSvc {

	static private Logger logger = LogManager.getLogger();
	
	public Flight fetchFlight(String flightNumber) {
		Flight flight = new Flight();
		try {
			flight = getRecord(flightNumber);
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
		return flight;
	}

	
	public boolean publishFlight(Flight flight) {
		boolean didWrite;
		try {
			didWrite = addRecord(flight);
		}
		catch(IOException e) {
			logger.error(e.getMessage());	
			didWrite = false;
		}
		catch(ClassNotFoundException e) {
			logger.error(e.getMessage());
			didWrite = false;
		}
		catch(RecordNotFoundException e) {
			logger.error(e.getMessage());
			didWrite = false;
		}
		return didWrite;
	}
	
	public boolean deleteFlight(String flightNumber) {
		boolean didDelete;
		try {
			didDelete = deleteRecords(flightNumber);
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
	
	public boolean updateFlight(Flight flight) {
		boolean didUpdate = true;
		// update flight
		return didUpdate;
	}
	
	public ArrayList<Flight> fetchAllFlights() {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		try {
			flights = getRecords();
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return flights;
	}
	
	public ArrayList<Flight> getRecords() throws IOException, ClassNotFoundException, RecordNotFoundException {
		ArrayList<Flight> flights = new ArrayList<Flight>();
		// retrieve data
		
		// dummy data for tests
		Flight f1 = new Flight();
		f1.setFlightNumber("AM32");
		f1.setAirlineCode("AM"); //Aeromexico
		f1.setDepartureCode("AZT"); //Zapatoca Airport, Zapatoca, Columbia
		f1.setArrivalCode("AZO"); //Kalamazoo Intl Airport, Kalamazoo, Michigan, US
		f1.setDepartureTime(LocalDateTime.of(2020, Month.JULY, 20, 16, 30, 00));
		f1.setArrivalTime(LocalDateTime.of(2020, Month.JULY, 20, 23, 30, 00));
		f1.setEconomyTicket(650);
		f1.setBusinessTicket(780);
		
		Flight f2 = new Flight();
		f2.setFlightNumber("JL16");
		f2.setAirlineCode("JL"); //Japan Airlines
		f2.setDepartureCode("KIX"); //Kansai International Airport, Japan
		f2.setArrivalCode("NRT"); //Narita International Airport, Japan
		f2.setDepartureTime(LocalDateTime.of(2020, Month.MAY, 3, 2, 45, 00));
		f2.setArrivalTime(LocalDateTime.of(2020, Month.MAY, 3, 5, 30, 00));
		f2.setEconomyTicket(200);
		f2.setBusinessTicket(380);
		
		Flight f3 = new Flight();
		f3.setFlightNumber("UA99");
		f3.setAirlineCode("UA"); //United Airlines
		f3.setDepartureCode("BUR"); //Hollywood Burbank Airport, California, US
		f3.setArrivalCode("EYE"); //Eagle Creek Airport, Indiana, US
		f3.setDepartureTime(LocalDateTime.of(2020, Month.DECEMBER, 11, 14, 00, 00));
		f3.setArrivalTime(LocalDateTime.of(2020, Month.DECEMBER, 11, 19, 45, 00));
		f3.setEconomyTicket(350);
		f3.setBusinessTicket(425);
		
		flights.add(f1);
		flights.add(f2);
		flights.add(f3);
		
 		return flights;
	}
	
	public boolean addRecords(ArrayList<Flight> data) throws IOException, ClassNotFoundException {
		boolean success = true;
		// write data
		return success;	
	}
	
	private Flight getRecord(String flightNumber) throws IOException, ClassNotFoundException, RecordNotFoundException {
		Flight requestedFlight = new Flight();
		// retrieve data
		return requestedFlight;
	}
	
	private boolean addRecord(Flight data) throws IOException, ClassNotFoundException, RecordNotFoundException {
		boolean success = true;
		// write data
		return success;
	}
	
	private boolean deleteRecords(String flightNumber) throws IOException, ClassNotFoundException, RecordNotFoundException {
		boolean success = true;
		// delete data
		return success;
	}
	
	public void printAllFlights() throws IOException, ClassNotFoundException, RecordNotFoundException {
		ArrayList<Flight> allFlights = this.fetchAllFlights();
		
		// define lambda expression
		StringBuilder builder = (String label, String value) -> {
			String str = label + ". " + value;
			return str;
		};
		
		// build list of flight numbers
		ArrayList<String> flightNumbers = new ArrayList<String>();
		for(int i = 0; i < allFlights.size(); i++) {
			Flight flight = allFlights.get(i);
			flightNumbers.add(flight.getFlightNumber());
		}
		
		// sort list
		Stream<String> stream = flightNumbers.stream().sorted();
		List<String> list = stream.collect(Collectors.toList());
		
		// print list
		for(int i = 0; i < list.size(); i++) {
			String label = String.valueOf(i + 1);
			System.out.println(builder.build(label, list.get(i)));
		}
	}
}
