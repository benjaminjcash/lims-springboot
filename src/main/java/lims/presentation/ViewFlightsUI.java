package com.mycompany.frs_maven.presentation;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.frs_maven.business.FlightMgr;
import com.mycompany.frs_maven.domain.Flight;

public class ViewFlightsUI extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Map<String, String> airports = new HashMap<>();
	private Map<String, String> airlines = new HashMap<>();
	static private Logger logger = LogManager.getLogger();
	
	public ViewFlightsUI() {
		super("All Flights", false, true);
		FlightMgr flightMgr = new FlightMgr();
		ArrayList<Flight> flights = new ArrayList<Flight>();
		try {
			flights = flightMgr.fetchAllFlights();
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		Container container = getContentPane();
		GridLayout layout = new GridLayout(flights.size(), 1);
		container.setLayout(layout);
		
		airports.put("BUR", "Hollywood Burbank Airport");
		airports.put("PSP", "Palm Springs");
		airports.put("EYE", "Eagle Creek Airport");
		airports.put("BHM", "Birmingham International Airport");
		airports.put("DEN", "Denver International Airport");
		airports.put("ATL", "Atlanta Hartsfield Jackson International Airport");
		airports.put("ANC", "Anchorage International Airport");
		airports.put("SEA", "Seattle, Tacoma International Airport");
		airports.put("CRW", "Charleston");
		airports.put("IAD", "Washington, Dulles International Airport");
		airports.put("MLI", "Quad Cities International Airport");
		airports.put("JFK", "John F Kennedy International Airport");
		airports.put("BOM", "Chattrapathi Shivaji International Airport");
		
		airlines.put("AA", "American Airlines");
		airlines.put("AS", "Alaska Airlines");
		airlines.put("WN", "Southwest Airlines");
		airlines.put("DL", "Delta Airlines");
		airlines.put("AI", "Air India");
		airlines.put("UA", "United Airlines");
		
		for(Integer i = 0; i < flights.size(); i++) {
			Flight flight = flights.get(i);
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
			LocalDateTime cdt = flight.getDepartureTime();
			LocalDateTime cat = flight.getArrivalTime();
			String dtm = String.valueOf(cdt.getMinute()).equals("0") ? "00" : String.valueOf(cdt.getMinute());
			String atm = String.valueOf(cat.getMinute()).equals("0") ? "00" : String.valueOf(cat.getMinute());
			String ddts = "   " + cdt.getDayOfWeek() + ", " + cdt.getMonth() + " " + cdt.getDayOfMonth() + " " + cdt.getHour() + ":" + dtm;
			String adts = "   " + cat.getDayOfWeek() + ", " + cdt.getMonth() + " " + cat.getDayOfMonth() + " " + cat.getHour() + ":" + atm;
			
			JLabel l1 = new JLabel((i + 1) + ". " + airlines.get(flight.getAirlineCode()) + " Flight "  + flight.getFlightNumber());
			JLabel l2 = new JLabel("   " + airports.get(flight.getDepartureCode()) + " to " + airports.get(flight.getArrivalCode()));
			JLabel l3 = new JLabel("   Departure Time:" + ddts);
			JLabel l4 = new JLabel("   Arrival Time:" + adts);
			JLabel l5 = new JLabel("   Business Class Ticket: $" + flight.getBusinessTicket() + "0");
			JLabel l6 = new JLabel("   Economy Class Ticket: $" + flight.getEconomyTicket() + "0");
			JLabel l7 = new JLabel("   ");
			JButton l8 = new JButton("Delete");
			JLabel l9 = new JLabel("   ");
			panel.add(l1);
			panel.add(l2);
			panel.add(l3);
			panel.add(l4);
			panel.add(l5);
			panel.add(l6);
			panel.add(l7);
			panel.add(l8);
			panel.add(l9);
			container.add(panel);
			
			l8.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						deleteFlight(flight.getFlightNumber());
						dispose();
					}
				}
			);
		}
		pack();
		setVisible(true);
	}
	
	private static void deleteFlight(String flightNumber) {
		FlightMgr flightMgr = new FlightMgr();
		try {
			flightMgr.deleteFlight(flightNumber);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
