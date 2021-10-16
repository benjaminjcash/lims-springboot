package com.mycompany.frs_maven.service;

import com.mycompany.frs_maven.exceptions.RecordNotFoundException;
import com.mycompany.frs_maven.exceptions.ServiceLoadException;
import com.mycompany.frs_maven.exceptions.WrongPasswordException;
import com.mycompany.frs_maven.domain.Traveler;

public class LoginSvcImpl implements ILoginSvc  {
	public boolean login(String username, String password) throws RecordNotFoundException, WrongPasswordException, ServiceLoadException {
		Factory factory = Factory.getInstance();
		ITravelerSvc travelerSvc = (ITravelerSvc)factory.getService(ITravelerSvc.NAME);
		
		Traveler profile = travelerSvc.fetchProfile(username);
		if(profile == null) {
			throw new RecordNotFoundException("No profile exists with that username");
		}
		String fetchedPassword = profile.getPassword();
		boolean authenticated;
		if(fetchedPassword.equals(password)) {
			authenticated = true;
		} else {
			throw new WrongPasswordException("The password you provided is incorrect");
		}
		return authenticated;
	}
}
