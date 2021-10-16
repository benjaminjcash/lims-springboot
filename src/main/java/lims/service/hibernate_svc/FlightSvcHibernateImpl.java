package com.mycompany.frs_maven.service.hibernate_svc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.mycompany.frs_maven.domain.Flight;
import com.mycompany.frs_maven.exceptions.RecordNotFoundException;
import com.mycompany.frs_maven.service.IFlightSvc;
import com.mycompany.frs_maven.service.StringBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.*;

public class FlightSvcHibernateImpl extends BaseSvcHibernateImpl implements IFlightSvc {
	
	static private Logger logger = LogManager.getLogger();
	
	public Flight fetchFlight(String flightNumber) {
		Flight flight = new Flight();
		try {
			Session session = getSession();
			Transaction transaction = session.beginTransaction();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Flight> cq = cb.createQuery(Flight.class);
			Root<Flight> model = cq.from(Flight.class);
			cq.where(cb.equal(model.get("flightNumber"), flightNumber));
			TypedQuery<Flight> tq = session.createQuery(cq);
			flight = tq.getSingleResult();
			transaction.commit();
			session.close();
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return flight;
	}

	public boolean publishFlight(Flight flight) {
		Boolean success = false;
		try {
			Session session = getSession();
			Transaction transaction = session.beginTransaction();
			session.save(flight);
			transaction.commit();
			session.close();
			success = true;
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return success;
	}

	public boolean deleteFlight(String flightNumber) {
		Boolean success = false;
		try {
			Session session = getSession();
			Transaction transaction = session.beginTransaction();
			Flight flight = session.load(Flight.class, flightNumber);
			session.delete(flight);
			transaction.commit();
			session.close();
			success = true;
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return success;
	}

	public boolean updateFlight(Flight flight) throws RecordNotFoundException {
		// fetch flight from db, hydrate null values in flight to update
		Flight fetchedFlight = this.fetchFlight(flight.getFlightNumber());
		if(flight.getAirlineCode() == null) { flight.setAirlineCode(fetchedFlight.getAirlineCode()); }
		if(flight.getDepartureCode() == null) { flight.setDepartureCode(fetchedFlight.getDepartureCode()); }
		if(flight.getDepartureTime() == null) { flight.setDepartureTime(fetchedFlight.getDepartureTime()); }
		if(flight.getArrivalCode() == null) { flight.setArrivalCode(fetchedFlight.getArrivalCode()); }
		if(flight.getArrivalTime() == null) { flight.setArrivalTime(fetchedFlight.getArrivalTime()); }
		Double bt = Double.valueOf(flight.getBusinessTicket());
		if(bt == 0.0) { flight.setBusinessTicket(fetchedFlight.getBusinessTicket()); }
		Double et = Double.valueOf(flight.getEconomyTicket());
		if(et == 0.0) { flight.setEconomyTicket(fetchedFlight.getEconomyTicket()); }
		
		Boolean success = false;
		try {
			Session session = getSession();
			Transaction transaction = session.beginTransaction();
			session.update(flight);
			transaction.commit();
			session.close();
			success = true;
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return success;
	}

	public ArrayList<Flight> fetchAllFlights() {
		ArrayList<Flight> flightList = new ArrayList<Flight>();
		try {
			Session session = getSession();
			Transaction transaction = session.beginTransaction();
			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Flight> cq = cb.createQuery(Flight.class);
			cq.from(Flight.class);
			flightList = (ArrayList<Flight>) session.createQuery(cq).getResultList();
			transaction.commit();
			session.close();
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		return flightList;
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
			logger.info(builder.build(label, list.get(i)));
		}
	}

}
