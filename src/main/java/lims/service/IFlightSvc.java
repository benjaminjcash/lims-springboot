package com.mycompany.frs_maven.service;
import java.io.IOException;
import java.util.ArrayList;

import com.mycompany.frs_maven.exceptions.RecordNotFoundException;
import com.mycompany.frs_maven.domain.Flight;

public interface IFlightSvc extends IService {
	
	public final String NAME = "IFlightSvc";
	
	public Flight fetchFlight(String flightNumber);
	
	public boolean publishFlight(Flight flight);
	
	public boolean deleteFlight(String flightNumber);
	
	public boolean updateFlight (Flight flight) throws RecordNotFoundException;
	
	public ArrayList<Flight> fetchAllFlights();
	
	public void printAllFlights() throws IOException, ClassNotFoundException, RecordNotFoundException;
}
