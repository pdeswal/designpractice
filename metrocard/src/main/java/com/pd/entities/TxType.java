package com.pd.entities;

public enum TxType {
	COMMUTE("COMMUTE"), RECHARGE("RECHARGE");
	private final String type;
	private TxType(final String type){
		this.type = type;
	}
	@Override
	public String toString(){
		return type;
	}
}
