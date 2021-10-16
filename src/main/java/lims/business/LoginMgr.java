package com.mycompany.frs_maven.business;

import com.mycompany.frs_maven.exceptions.RecordNotFoundException;
import com.mycompany.frs_maven.exceptions.ServiceLoadException;
import com.mycompany.frs_maven.exceptions.WrongPasswordException;
import com.mycompany.frs_maven.service.Factory;
import com.mycompany.frs_maven.service.ILoginSvc;

public class LoginMgr {
	private ILoginSvc loginSvc;
	private void setup() throws ServiceLoadException {
		Factory factory = Factory.getInstance();
		loginSvc = (ILoginSvc) factory.getService(ILoginSvc.NAME);
	}
	
	public boolean login(String username, String password) throws RecordNotFoundException, WrongPasswordException, ServiceLoadException {
		setup();
		return loginSvc.login(username, password);
	}
}
 