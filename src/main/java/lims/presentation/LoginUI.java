package com.mycompany.frs_maven.presentation;

import javax.swing.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mycompany.frs_maven.business.LoginMgr;
import com.mycompany.frs_maven.business.TravelerMgr;
import com.mycompany.frs_maven.domain.Traveler;
import com.mycompany.frs_maven.exceptions.RecordNotFoundException;
import com.mycompany.frs_maven.exceptions.ServiceLoadException;
import com.mycompany.frs_maven.exceptions.WrongPasswordException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JInternalFrame {
	static private Logger logger = LogManager.getLogger();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel usernameLbl = new JLabel("Username");
	private JLabel passwordLbl = new JLabel("Password");
	private JTextField usernameFld = new JTextField(10);
	private JPasswordField passwordFld = new JPasswordField(10);
	private JButton loginBtn = new JButton("Login");
	private JButton createProfileBtn = new JButton("Create Profile");
	
	public LoginUI() {
		super("Login", false, true);
		
		Container container = getContentPane();
		GridLayout layout = new GridLayout(3, 2);
		container.setLayout(layout);
		container.add(usernameLbl);
		container.add(usernameFld);
		container.add(passwordLbl);
		container.add(passwordFld);
		container.add(createProfileBtn);
		container.add(loginBtn);
		
		loginBtn.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					LoginMgr loginMgr = new LoginMgr();
					TravelerMgr travelerMgr = new TravelerMgr();
					Traveler user = new Traveler();
					String usrname = usernameFld.getText();
					String pswrd = new String(passwordFld.getPassword());
					boolean success = false;

					try { success = loginMgr.login(usrname, pswrd); }
					catch (RecordNotFoundException e) { logger.error(e.getMessage()); }
					catch (WrongPasswordException e) { logger.error(e.getMessage()); }
					catch (Exception e) { logger.error(e.getMessage()); }
					
					if(success) {
						try { user = travelerMgr.fetchProfile(usrname); }
						catch (ServiceLoadException e) { logger.error(e.getMessage()); }
						
						System.out.println("Welcome back " + user.getName() + ".");
						dispose();
					} else {
						System.out.println("Authentication failed, unable to login.");
					}
				}
			}
		);
		createProfileBtn.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						MainUI.openCreateTravelerUI();
						dispose();
					}
				}
		);
		
		
		pack();
		setVisible(true);
	}
}
