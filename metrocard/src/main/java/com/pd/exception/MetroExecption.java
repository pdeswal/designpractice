package com.pd.exception;

public class MetroExecption extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6159669875212219017L;
	public String message;
	public MetroExecption(String message){
		this.message = message;
	}
}
