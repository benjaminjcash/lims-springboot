package com.mycompany.frs_maven.service.jdbc_svc;

import java.io.FileInputStream;

import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.time.LocalDateTime;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mycompany.frs_maven.domain.Flight;
import com.mycompany.frs_maven.exceptions.RecordNotFoundException;
import com.mycompany.frs_maven.service.IFlightSvc;
import com.mycompany.frs_maven.service.StringBuilder;

public class FlightSvcJDBCImpl implements IFlightSvc {
	
	static private Logger logger = LogManager.getLogger();
	private Connection connection = null;
	private String url;
	private String userId;
	private String password;
	
	private void getDatabaseCredentials() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder parser = factory.newDocumentBuilder();
			Document document = parser.parse(new FileInputStream("src/main/resources/db_credentials.xml"));
			Element root = document.getDocumentElement();
			NodeList nodes = root.getChildNodes();
			for(int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if(node instanceof Element) {
					Element element = (Element) node;
					String tag = element.getTagName();
					String value = element.getTextContent();
					switch(tag) {
					case "url":
						this.url = value;
					case "userId":
						this.userId = value;
					case "password":
						this.password = value;
					}
				}
			}
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	private void fetchConnection() {
		getDatabaseCredentials();
		try {
			connection = DriverManager.getConnection(url, userId, password);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	
	public Flight fetchFlight(String flightNumber) {
		Flight flight = new Flight();
		fetchConnection();
		if(connection != null) {
			try {
				Statement statement = connection.createStatement();
				String sql = "SELECT * FROM flights WHERE flightNumber = '" + flightNumber + "'";
				ResultSet resultSet = statement.executeQuery(sql);
				while(resultSet.next()) {
					flight.setFlightNumber(resultSet.getString("flightNumber"));
					flight.setAirlineCode(resultSet.getString("airlineCode"));
					flight.setDepartureCode(resultSet.getString("departureCode"));
					flight.setArrivalCode(resultSet.getString("arrivalCode"));
					flight.setDepartureTime(resultSet.getObject("departureTime", LocalDateTime.class));
					flight.setArrivalTime(resultSet.getObject("arrivalTime", LocalDateTime.class));
					flight.setEconomyTicket(resultSet.getDouble("economyTicket"));
					flight.setBusinessTicket(resultSet.getDouble("businessTicket"));
				}
				statement.close();
				connection.close();
			}
			catch (Exception e) {
				logger.error(e.getMessage());
			}
			
		} else {
			logger.info("no connection to database");
		}
		return flight;
	}

	public boolean publishFlight(Flight flight) {
		Boolean success = false;
		fetchConnection();
		if(connection != null) {
			try {
				Statement statement = connection.createStatement();
				String sql = "INSERT INTO flights (flightNumber, airlineCode, departureCode, arrivalCode, departureTime, arrivalTime, economyTicket, businessTicket) VALUES ("
					+ "'" + flight.getFlightNumber() + "', "
					+ "'" + flight.getAirlineCode() + "', "
					+ "'" + flight.getDepartureCode() + "', "
					+ "'" + flight.getArrivalCode() + "', "
					+ "'" + flight.getDepartureTime() + "', "
					+ "'" + flight.getArrivalTime() + "', "
					+ "'" + flight.getEconomyTicket() + "', "
					+ "'" + flight.getBusinessTicket() + "')";
				statement.executeUpdate(sql);
				statement.close();
				connection.close();
				success = true;
			}
			catch (Exception e) {
				logger.error(e.getMessage());
			}
			
		} else {
			logger.info("no connection to database");
		}
		return success;
	}

	public boolean deleteFlight(String flightNumber) {
		Boolean success = false;
		fetchConnection();
		if(connection != null) {
			try {
				Statement statement = connection.createStatement();
				String sql = "DELETE FROM flights WHERE flightNumber = '" + flightNumber + "'";
				statement.executeUpdate(sql);
				statement.close();
				connection.close();
				success = true;
			}
			catch (Exception e) {
				logger.error(e.getMessage());
			}
			
		} else {
			logger.info("no connection to database");
		}
		return success;
	}
	
	public boolean updateFlight (Flight flight) throws RecordNotFoundException {
		Boolean success = false;
		if(flight.getFlightNumber() == null) {
			throw new RecordNotFoundException("A flight number must be specified when updating a record.");
		}
		fetchConnection();
		if(connection != null) {
			try {
				Statement statement = connection.createStatement();
				String sql = "UPDATE flights SET ";
				if(flight.getAirlineCode() != null) sql += "airlineCode = '" + flight.getAirlineCode() + "', ";
				if(flight.getDepartureCode() != null) sql += "departureCode = '" + flight.getDepartureCode() + "', ";
				if(flight.getArrivalCode() != null) sql += "arrivalCode = '" + flight.getArrivalCode() + "', ";
				if(flight.getDepartureTime() != null) sql += "departureTime = '" + flight.getDepartureTime() + "', ";
				if(flight.getArrivalTime() != null) sql += "arrivalTime = '" + flight.getArrivalTime() + "', ";
				if((Double)flight.getEconomyTicket() != null) sql += "economyTicket = '" + flight.getEconomyTicket() + "', ";
				if((Double)flight.getBusinessTicket() != null) sql += "businessTicket = '" + flight.getBusinessTicket() + "', ";
				sql = sql.substring(0, sql.length() - 2);
				sql += " WHERE flightNumber = '" + flight.getFlightNumber() + "'";
				statement.executeUpdate(sql);
				statement.close();
				connection.close();
				
				success = true;
			}
			catch (Exception e) {
				logger.error(e.getMessage());
			}
		} else {
			logger.info("no connection to database");
		}
		return success;
	}

	public ArrayList<Flight> fetchAllFlights() {
		ArrayList<Flight> flightList = new ArrayList<Flight>();
		fetchConnection();
		if(connection != null) {
			try {
				Statement statement = connection.createStatement();
				String sql = "SELECT * FROM flights";
				ResultSet resultSet = statement.executeQuery(sql);
				while(resultSet.next()) {
					Flight flight = new Flight();
					flight.setFlightNumber(resultSet.getString("flightNumber"));
					flight.setAirlineCode(resultSet.getString("airlineCode"));
					flight.setDepartureCode(resultSet.getString("departureCode"));
					flight.setArrivalCode(resultSet.getString("arrivalCode"));
					flight.setDepartureTime(resultSet.getObject("departureTime", LocalDateTime.class));
					flight.setArrivalTime(resultSet.getObject("arrivalTime", LocalDateTime.class));
					flight.setEconomyTicket(resultSet.getDouble("economyTicket"));
					flight.setBusinessTicket(resultSet.getDouble("businessTicket"));
					flightList.add(flight);
				}
				statement.close();
				connection.close();
			}
			catch (Exception e) {
				logger.error(e.getMessage());
			}
			
		} else {
			logger.info("no connection to database");
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
