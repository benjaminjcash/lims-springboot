package com.mycompany.frs_maven.presentation;
import java.awt.Container;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.frs_maven.business.FlightMgr;
import com.mycompany.frs_maven.domain.Flight;
import com.mycompany.frs_maven.service.Factory;
import com.mycompany.frs_maven.service.IFlightSvc;

public class CreateFlightUI extends JInternalFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, Month> months = new HashMap<>();
	private JLabel flightNumberLbl = new JLabel("Flight Number");
	private JLabel airlineCodeLbl = new JLabel("Airline Code");
	private JLabel departureCodeLbl = new JLabel("Departure Code");
	private JLabel departureTimeYearLbl = new JLabel("Departure Time - Year");
	private JLabel departureTimeMonthLbl = new JLabel("Departure Time - Month");
	private JLabel departureTimeDayLbl = new JLabel("Departure Time - Day");
	private JLabel departureTimeHourLbl = new JLabel("Departure Time - Hour");
	private JLabel departureTimeMinuteLbl = new JLabel("Departure Time - Minute");
	private JLabel arrivalCodeLbl = new JLabel("Arrival Code");
	private JLabel arrivalTimeYearLbl = new JLabel("Arrival Time - Year");
	private JLabel arrivalTimeMonthLbl = new JLabel("Arrival Time - Month");
	private JLabel arrivalTimeDayLbl = new JLabel("Arrival Time - Day");
	private JLabel arrivalTimeHourLbl = new JLabel("Arrival Time - Hour");
	private JLabel arrivalTimeMinuteLbl = new JLabel("Arrival Time - Minute");
	private JLabel businessTicketLbl = new JLabel("Business Ticket");
	private JLabel economyTicketLbl = new JLabel("Economy Ticket");
	private JTextField flightNumberFld = new JTextField(10);
	private JTextField airlineCodeFld = new JTextField(10);
	private JTextField departureCodeFld = new JTextField(10);
	private JTextField departureTimeYearFld = new JTextField(10);
	private JTextField departureTimeMonthFld = new JTextField(10);
	private JTextField departureTimeDayFld = new JTextField(10);
	private JTextField departureTimeHourFld = new JTextField(10);
	private JTextField departureTimeMinuteFld = new JTextField(10);
	private JTextField arrivalCodeFld = new JTextField(10);
	private JTextField arrivalTimeYearFld = new JTextField(10);
	private JTextField arrivalTimeMonthFld = new JTextField(10);
	private JTextField arrivalTimeDayFld = new JTextField(10);
	private JTextField arrivalTimeHourFld = new JTextField(10);
	private JTextField arrivalTimeMinuteFld = new JTextField(10);
	private JTextField businessTicketFld = new JTextField(10);
	private JTextField economyTicketFld = new JTextField(10);
	private JButton submitBtn = new JButton("Submit");
	static private Logger logger = LogManager.getLogger();
	
	public CreateFlightUI() {
		super("Create Flight", false, true);
		
		months.put("January", Month.JANUARY);
		months.put("Febuary", Month.FEBRUARY);
		months.put("March", Month.MARCH);
		months.put("April", Month.APRIL);
		months.put("May", Month.MAY);
		months.put("June", Month.JUNE);
		months.put("July", Month.JULY);
		months.put("August", Month.AUGUST);
		months.put("September", Month.SEPTEMBER);
		months.put("October", Month.OCTOBER);
		months.put("November", Month.NOVEMBER);
		months.put("December", Month.DECEMBER);
		
		Container container = getContentPane();
		GridLayout layout = new GridLayout(17, 2);
		container.setLayout(layout);
		container.add(flightNumberLbl);
		container.add(flightNumberFld);
		container.add(airlineCodeLbl);
		container.add(airlineCodeFld);
		container.add(departureCodeLbl);
		container.add(departureCodeFld);
		container.add(departureTimeYearLbl);
		container.add(departureTimeYearFld);
		container.add(departureTimeMonthLbl);
		container.add(departureTimeMonthFld);
		container.add(departureTimeDayLbl);
		container.add(departureTimeDayFld);
		container.add(departureTimeHourLbl);
		container.add(departureTimeHourFld);
		container.add(departureTimeMinuteLbl);
		container.add(departureTimeMinuteFld);
		container.add(arrivalCodeLbl);
		container.add(arrivalCodeFld);
		container.add(arrivalTimeYearLbl);
		container.add(arrivalTimeYearFld);
		container.add(arrivalTimeMonthLbl);
		container.add(arrivalTimeMonthFld);
		container.add(arrivalTimeDayLbl);
		container.add(arrivalTimeDayFld);
		container.add(arrivalTimeHourLbl);
		container.add(arrivalTimeHourFld);
		container.add(arrivalTimeMinuteLbl);
		container.add(arrivalTimeMinuteFld);
		container.add(businessTicketLbl);
		container.add(businessTicketFld);
		container.add(economyTicketLbl);
		container.add(economyTicketFld);
		container.add(submitBtn);
		
		submitBtn.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					FlightMgr flightMgr = new FlightMgr();
					LocalDateTime departureTime = LocalDateTime.of(Integer.parseInt(departureTimeYearFld.getText()),
																   months.get(departureTimeMonthFld.getText()), 
																   Integer.parseInt(departureTimeDayFld.getText()), 
																   Integer.parseInt(departureTimeHourFld.getText()), 
																   Integer.parseInt(departureTimeMinuteFld.getText()));
					LocalDateTime arrivalTime = LocalDateTime.of(Integer.parseInt(arrivalTimeYearFld.getText()),
							   months.get(arrivalTimeMonthFld.getText()), 
							   Integer.parseInt(arrivalTimeDayFld.getText()), 
							   Integer.parseInt(arrivalTimeHourFld.getText()), 
							   Integer.parseInt(arrivalTimeMinuteFld.getText()));						
					try {
						Flight newFlight = new Flight();
						newFlight.setFlightNumber(flightNumberFld.getText());
						newFlight.setAirlineCode(airlineCodeFld.getText());
						newFlight.setDepartureCode(departureCodeFld.getText());
						newFlight.setDepartureTime(departureTime);
						newFlight.setArrivalCode(arrivalCodeFld.getText());
						newFlight.setArrivalTime(arrivalTime);
						newFlight.setBusinessTicket(Double.parseDouble(businessTicketFld.getText()));
						newFlight.setEconomyTicket(Double.parseDouble(economyTicketFld.getText()));
						flightMgr.publishFlight(newFlight);
					}
					catch (Exception e) { logger.fatal(e.getMessage()); }
					dispose();
					MainUI.openViewFlightsUI();
				}
			}
		);
		
		pack();
		setVisible(true);
	}
	
	// For printing to console
	public void printFlights() {
		Factory factory = Factory.getInstance();
		try { 
			System.out.println();
			IFlightSvc flightSvc = (IFlightSvc)factory.getService(IFlightSvc.NAME);
			flightSvc.printAllFlights();
			System.out.println();
		}
		catch(Exception e) { logger.fatal(e.getMessage()); }
	}
	
}
