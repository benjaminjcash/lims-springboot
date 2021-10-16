package com.mycompany.frs_maven.service;

import com.mycompany.frs_maven.exceptions.RecordNotFoundException;
import com.mycompany.frs_maven.exceptions.ServiceLoadException;
import com.mycompany.frs_maven.exceptions.WrongPasswordException;

public interface ILoginSvc extends IService {
	
	public final String NAME = "ILoginSvc";
	
	public boolean login(String username, String password) throws  RecordNotFoundException, WrongPasswordException, ServiceLoadException;
}
