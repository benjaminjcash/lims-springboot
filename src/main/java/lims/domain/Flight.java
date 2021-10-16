package com.mycompany.frs_maven.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="flights")
public class Flight implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* Default Constructor */
	public Flight() {}
	
	/* Properties */
	@Id
	@Column(name="flightNumber", 
			length=4, 
			nullable=false, 
			unique=true)
	private String flightNumber;
	
	@Column(name="airlineCode",
			length=2,
			nullable=false)
	private String airlineCode;
	
	@Column(name="departureCode",
			length=3,
			nullable=false)
	private String departureCode;
	
	@Column(name="departureTime",
			nullable=false)
	private LocalDateTime departureTime;
	
	@Column(name="arrivalCode",
			length=3,
			nullable=false)
	private String arrivalCode;
	
	@Column(name="arrivalTime",
			nullable=false)
	private LocalDateTime arrivalTime;
	
	@Column(name="businessTicket",
			nullable=false)
	private double businessTicket;
	
	@Column(name="economyTicket",
			nullable=false)
	private double economyTicket;
	
	/* Getters and Setters */
	public String getFlightNumber() {
		return this.flightNumber;
	}
	public void setFlightNumber(String num) {
		this.flightNumber = num;
	}
	
	public String getAirlineCode() {
		return this.airlineCode;
	}
	public void setAirlineCode(String code) {
		this.airlineCode = code;
	}
	
	public String getDepartureCode() {
		return this.departureCode;
	}
	public void setDepartureCode(String code) {
		this.departureCode = code;
	}
	
	public LocalDateTime getDepartureTime() {
		return this.departureTime;
	}
	public void setDepartureTime(LocalDateTime time) {
		this.departureTime = time;
	}
	
	public String getArrivalCode() {
		return this.arrivalCode;
	}
	public void setArrivalCode(String code) {
		this.arrivalCode = code;
	}
	
	public LocalDateTime getArrivalTime() {
		return this.arrivalTime;
	}
	public void setArrivalTime(LocalDateTime time) {
		this.arrivalTime = time;
	}
	
	public double getBusinessTicket() {
		return this.businessTicket;
	}
	public void setBusinessTicket(double cost) {
		this.businessTicket = cost;
	}
	
	public double getEconomyTicket() {
		return this.economyTicket;
	}
	public void setEconomyTicket(double cost) {
		this.economyTicket = cost;
	}
	
	/* Equals */
	public boolean equals(Flight flight) {
		String fn = flight.getFlightNumber();
		if(fn.equals(this.flightNumber)) {
			return true;
		} else {
			return false;
		}
	}
	
	/* Validate */
	public boolean validate() {
		boolean valid = true;
		
		if(this.airlineCode == null || this.flightNumber == null || this.departureCode == null || this.departureTime == null ||
			this.arrivalCode == null || this.arrivalTime == null) {
			valid = false;
		}
		
		if(this.flightNumber.length() != 3) {
			valid = false;
		}
		
		return valid;
	}
}
