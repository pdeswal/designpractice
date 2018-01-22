package com.pd.entities;

public enum Station {
	A1,A2,A3,A4,A5,A6,A7,A8,A9,A10;
	
	public int getDistance(Station other){
		return Math.abs(this.ordinal() - other.ordinal()); 
	}
}
