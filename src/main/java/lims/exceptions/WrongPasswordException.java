package com.mycompany.frs_maven.exceptions;

public class WrongPasswordException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -527193734818408931L;

	public WrongPasswordException (String s) {
		super(s);	
	}
}
