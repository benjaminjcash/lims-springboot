package com.mycompany.frs_maven.presentation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MainUI extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static JDesktopPane theDesktop = new JDesktopPane();
	
	public MainUI() {
		super("Flight Reservation System");
		
		// Menu //
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		getContentPane().add(theDesktop);
		
			JMenu fileMenu = new JMenu("File");
			menuBar.add(fileMenu);
				JMenu newMenu = new JMenu("New");
					JMenuItem newFlight = new JMenuItem("Flight");
					JMenuItem newTraveler = new JMenuItem("Traveler");
					newMenu.add(newFlight);
					newMenu.add(newTraveler);
					fileMenu.add(newMenu);
				JSeparator sep1 = new JSeparator();
				fileMenu.add(sep1);
				JMenuItem exit = new JMenuItem("Exit");
				fileMenu.add(exit);
			
			JMenu viewMenu = new JMenu("View");
			menuBar.add(viewMenu);
				JMenuItem allFlights = new JMenuItem("All Flights");
				viewMenu.add(allFlights);
				JMenuItem allTravelers = new JMenuItem("All Travelers");
				viewMenu.add(allTravelers);
			
			JMenu userMenu = new JMenu("User");
			menuBar.add(userMenu);
				JMenuItem login = new JMenuItem("Login");
				userMenu.add(login);
				JMenuItem logout = new JMenuItem("Logout");
				userMenu.add(logout);
			
		// Action Listeners //
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openLoginUI();
			}
		});
		newFlight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openCreateFlightUI();
			}
		});
		newTraveler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openCreateTravelerUI();
			}
		});
		allFlights.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openViewFlightsUI();
			}
		});
		allTravelers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				openViewTravelersUI();
			}
		});
		
		pack();
		setVisible(true);	
	}
	
	public static void openCreateFlightUI() {
		CreateFlightUI createFlightUI = new CreateFlightUI();
		createFlightUI.setVisible(true);
		theDesktop.add(createFlightUI);
	}
	
	public static void openCreateTravelerUI() {
		CreateTravelerUI createTravelerUI = new CreateTravelerUI();
		createTravelerUI.setVisible(true);
		theDesktop.add(createTravelerUI);
	}
	
	public static void openLoginUI() {
		LoginUI loginUI = new LoginUI();
		loginUI.setVisible(true);
		theDesktop.add(loginUI);
		Dimension desktopSize = theDesktop.getSize();
		Dimension jInternalFrameSize = loginUI.getSize();
		loginUI.setLocation((desktopSize.width - jInternalFrameSize.width)/2,
			    (desktopSize.height- jInternalFrameSize.height)/2);
	}
	
	public static void openViewFlightsUI() {
		ViewFlightsUI allFlightsUI = new ViewFlightsUI();
		allFlightsUI.setVisible(true);
		theDesktop.add(allFlightsUI);
	}
	
	public static void openViewTravelersUI() {
		ViewTravelersUI allTravelersUI = new ViewTravelersUI();
		allTravelersUI.setVisible(true);
		theDesktop.add(allTravelersUI);
	}
 	
	public static void main(String[] args) {
		MainUI mainUI = new MainUI();
		mainUI.setExtendedState(JFrame.MAXIMIZED_BOTH);
		mainUI.setVisible(true);
		openLoginUI();
	}
}
 