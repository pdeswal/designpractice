package com.pd.entities;

public class MetroCard extends SmartCard{
	private final String cardProviderCompany = "SUICA";
	
	public String toString(){
		return "[" + cardProviderCompany + " " + super.toString() + "]";
	}
}
