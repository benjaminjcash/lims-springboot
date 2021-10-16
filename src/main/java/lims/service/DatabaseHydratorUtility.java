package com.mycompany.frs_maven.service;

import java.time.LocalDateTime;

import java.time.Month;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.frs_maven.domain.Flight;
import com.mycompany.frs_maven.domain.Traveler;

public class DatabaseHydratorUtility {
	static private Logger logger = LogManager.getLogger();
	static private IFlightSvc flightSvc;
	static private ITravelerSvc travelerSvc;
	
	public static void main(String[] args) {
		Factory factory = Factory.getInstance();
		
		// Flights
		Flight f1 = new Flight();
		f1.setFlightNumber("AA01");
		f1.setAirlineCode("AA"); //American Airlines
		f1.setDepartureCode("IAD"); //Washington, Dulles International Airport
		f1.setArrivalCode("MLI"); //Quad Cities International Airport
		f1.setDepartureTime(LocalDateTime.of(2020, Month.AUGUST, 20, 16, 30, 00));
		f1.setArrivalTime(LocalDateTime.of(2020, Month.AUGUST, 20, 23, 30, 00));
		f1.setEconomyTicket(1680);
		f1.setBusinessTicket(2200);
		
		Flight f2 = new Flight();
		f2.setFlightNumber("DL16");
		f2.setAirlineCode("DL"); //Southwest Airlines
		f2.setDepartureCode("ATL"); //Atlanta Hartsfield Jackson International Airport
		f2.setArrivalCode("PSP"); //Denver International Airport
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
		
		try {
			flightSvc = (IFlightSvc) factory.getService(IFlightSvc.NAME);
			flightSvc.publishFlight(f1);
			flightSvc.publishFlight(f2);
			flightSvc.publishFlight(f3);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		// Travelers
		Traveler t1 = new Traveler();
		t1.setUsername("beachytrashy123");
		t1.setPassword("underdase4");
		t1.setName("Johnny Bravo");
		t1.setAddress("123 Easy Street, Irvine CA 90021");
		t1.setCreditCardNumber("1223455566789900");
		t1.setExpirationDate("09/22");
		
		Traveler t2 = new Traveler();
		t2.setUsername("frodorules99");
		t2.setPassword("0nering2rulethem4ll");
		t2.setName("Frodo Baggins");
		t2.setAddress("45 Hobbiton Hill, Shire, Middle Earth");
		t2.setCreditCardNumber("6657838499760099");
		t2.setExpirationDate("11/25");
		
		Traveler t3 = new Traveler();
		t3.setUsername("scarsRsexy4");
		t3.setPassword("imm4wiz4rd");
		t3.setName("Harry Potter");
		t3.setAddress("4 Privet Drive, Little Whinging, Surrey, England, Great Britain");
		t3.setCreditCardNumber("7765898712133333");
		t3.setExpirationDate("06/23");
		
		try {
			travelerSvc = (ITravelerSvc) factory.getService(ITravelerSvc.NAME);
			travelerSvc.createProfile(t1);
			travelerSvc.createProfile(t2);
			travelerSvc.createProfile(t3);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
