package com.mycompany.frs_maven.presentation;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.frs_maven.business.TravelerMgr;
import com.mycompany.frs_maven.domain.Traveler;

public class ViewTravelersUI extends JInternalFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private Logger logger = LogManager.getLogger();

	public ViewTravelersUI() {
		super("All Travelers", false, true);
		TravelerMgr travelerMgr = new TravelerMgr();
		ArrayList<Traveler> travelers = new ArrayList<Traveler>();
		try {
			travelers = travelerMgr.fetchAllProfiles();
			
		}
		catch(Exception e) {
			logger.error(e.getMessage());
		}
		
		Container container = getContentPane();
		GridLayout layout = new GridLayout(travelers.size(), 1);
		container.setLayout(layout);
		
		for(Integer i = 0; i < travelers.size(); i++) {
			Traveler traveler = travelers.get(i);
			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
			
			String hiddenPassword = "";
			for(int j = 0; j < traveler.getPassword().length(); j++) {
				if(j < 2) {
					hiddenPassword += traveler.getPassword().charAt(j);
				} else {
					hiddenPassword += "*";
				}
			}
			boolean ccOnFile = traveler.getCreditCardNumber().length() > 0 ? true : false;
			
			JLabel l1 = new JLabel((i + 1) + ". " + "" + traveler.getName());
			JLabel l2 = new JLabel("   Address: " + traveler.getAddress());
			JLabel l3 = new JLabel("   Username: " + traveler.getUsername());
			JLabel l4 = new JLabel("   Password: " + hiddenPassword);
			JLabel l5 = new JLabel("   CC on File: " + ccOnFile);
			JLabel l6 = new JLabel("   ");
			JButton l7 = new JButton("Delete");
			JLabel l8 = new JLabel("   ");
			panel.add(l1);
			panel.add(l2);
			panel.add(l3);
			panel.add(l4);
			panel.add(l5);
			panel.add(l6);
			panel.add(l7);
			panel.add(l8);
			container.add(panel);
			
			l7.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						deleteProfile(traveler.getUsername());
						dispose();
					}
				}
			);
		}
		pack();
		setVisible(true);
	}
	
	private static void deleteProfile(String username) {
		TravelerMgr travelerMgr = new TravelerMgr();
		try {
			travelerMgr.deleteProfile(username);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
}
