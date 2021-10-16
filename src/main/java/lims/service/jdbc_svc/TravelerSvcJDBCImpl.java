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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mycompany.frs_maven.domain.Traveler;
import com.mycompany.frs_maven.exceptions.RecordNotFoundException;
import com.mycompany.frs_maven.service.ITravelerSvc;
import com.mycompany.frs_maven.service.StringBuilder;

public class TravelerSvcJDBCImpl implements ITravelerSvc {
	
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
	
	public Traveler fetchProfile(String username) {
		Traveler traveler = new Traveler();
		fetchConnection();
		if(connection != null) {
			try {
				Statement statement = connection.createStatement();
				String sql = "SELECT * FROM travelers WHERE username = '" + username + "'";
				ResultSet resultSet = statement.executeQuery(sql);
				while(resultSet.next()) {
					traveler.setUsername(resultSet.getString("username"));
					traveler.setPassword(resultSet.getString("password"));
					traveler.setName(resultSet.getString("name"));
					traveler.setAddress(resultSet.getString("address"));
					traveler.setCreditCardNumber(resultSet.getString("creditCardNumber"));
					traveler.setExpirationDate(resultSet.getString("expirationDate"));
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
		return traveler;
	}

	public boolean createProfile(Traveler traveler) throws IOException, ClassNotFoundException {
		Boolean success = false;
		fetchConnection();
		if(connection != null) {
			try {
				Statement statement = connection.createStatement();
				String sql = "INSERT INTO travelers (username, password, name, address, creditCardNumber, expirationDate) VALUES ("
					+ "'" + traveler.getUsername() + "', "
					+ "'" + traveler.getPassword() + "', "
					+ "'" + traveler.getName() + "', "
					+ "'" + traveler.getAddress() + "', "
					+ "'" + traveler.getCreditCardNumber() + "', "
					+ "'" + traveler.getExpirationDate() + "')";
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

	public boolean deleteProfile(String username) {
		Boolean success = false;
		fetchConnection();
		if(connection != null) {
			try {
				Statement statement = connection.createStatement();
				String sql = "DELETE FROM travelers WHERE username = '" + username + "'";
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
	
	public boolean updateProfile(Traveler traveler) throws RecordNotFoundException {
		Boolean success = false;
		if(traveler.getUsername() == null) {
			throw new RecordNotFoundException("A username must be specified when updating a record.");
		}
		fetchConnection();
		if(connection != null) {
			try {
				Statement statement = connection.createStatement();
				String sql = "UPDATE travelers SET ";
				if(traveler.getPassword() != null) sql += "password = '" + traveler.getPassword() + "', ";
				if(traveler.getName() != null) sql += "name = '" + traveler.getName() + "', ";
				if(traveler.getAddress() != null) sql += "address = '" + traveler.getAddress() + "', ";
				if(traveler.getCreditCardNumber() != null) sql += "creditCardNumber = '" + traveler.getCreditCardNumber() + "', ";
				if(traveler.getExpirationDate() != null) sql += "expirationDate = '" + traveler.getExpirationDate() + "', ";
				sql = sql.substring(0, sql.length() - 2);
				sql += " WHERE username = '" + traveler.getUsername() + "'";
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

	public ArrayList<Traveler> fetchAllProfiles() {
		ArrayList<Traveler> travelerList = new ArrayList<Traveler>();
		fetchConnection();
		if(connection != null) {
			try {
				Statement statement = connection.createStatement();
				String sql = "SELECT * FROM travelers";
				ResultSet resultSet = statement.executeQuery(sql);
				while(resultSet.next()) {
					Traveler traveler = new Traveler();
					traveler.setUsername(resultSet.getString("username"));
					traveler.setPassword(resultSet.getString("password"));
					traveler.setName(resultSet.getString("name"));
					traveler.setAddress(resultSet.getString("address"));
					traveler.setCreditCardNumber(resultSet.getString("creditCardNumber"));
					traveler.setExpirationDate(resultSet.getString("expirationDate"));
					travelerList.add(traveler);
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
		return travelerList;
	}

	public void printAllTravelers() throws IOException, ClassNotFoundException, RecordNotFoundException {
		ArrayList<Traveler> allTravelers = this.fetchAllProfiles();
		
		// define lambda expression
		StringBuilder builder = (String label, String value) -> {
			String str = label + ". " + value;
			return str;
		};
		
		// build list of usernames
		ArrayList<String> usernames = new ArrayList<String>();
		for(int i = 0; i < allTravelers.size(); i++) {
			Traveler traveler = allTravelers.get(i);
			usernames.add(traveler.getUsername());
		}
		
		// sort list
		Stream<String> stream = usernames.stream().sorted();
		List<String> list = stream.collect(Collectors.toList());
		
		// print list
		for(int i = 0; i < list.size(); i++) {
			String label = String.valueOf(i + 1);
			logger.info(builder.build(label, list.get(i)));
		}
		
	}
	
}