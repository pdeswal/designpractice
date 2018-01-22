package com.pd.entities;

import java.time.LocalDateTime;

public class SmartCardTx {
	private int id;
	private SmartCard card;
	private Station source;
	private Station destination;
	private int distance;
	
	private TxType txType;
	private double fare;
	private double change;
	private double previousBalance;
	private double newBalance;
	private LocalDateTime inTime;
	private LocalDateTime outTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public SmartCard getCard() {
		return card;
	}
	public void setCard(SmartCard card) {
		this.card = card;
	}
	public Station getSource() {
		return source;
	}
	public void setSource(Station source) {
		this.source = source;
	}
	public Station getDestination() {
		return destination;
	}
	public void setDestination(Station destination) {
		this.destination = destination;
	}
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public double getFare() {
		return fare;
	}
	public void setFare(double fare) {
		this.fare = fare;
	}
	
	public double getChange() {
		return change;
	}
	public void setChange(double change) {
		this.change = change;
	}
	public double getPreviousBalance() {
		return previousBalance;
	}
	public void setPreviousBalance(double previousBalance) {
		this.previousBalance = previousBalance;
	}
	public double getNewBalance() {
		return newBalance;
	}
	public void setNewBalance(double newBalance) {
		this.newBalance = newBalance;
	}
	public LocalDateTime getInTime() {
		return inTime;
	}
	public void setInTime(LocalDateTime inTime) {
		this.inTime = inTime;
	}
	public LocalDateTime getOutTime() {
		return outTime;
	}
	public void setOutTime(LocalDateTime outTime) {
		this.outTime = outTime;
	}
	public TxType getTxType() {
		return txType;
	}
	public void setTxType(TxType txType) {
		this.txType = txType;
	}
	@Override
	public String toString() {
		return "SmartCardTx [id=" + id + ", card=" + card + ", source="
				+ source + ", destination=" + destination + ", distance="
				+ distance + ", txType=" + txType + ", fare=" + fare
				+ ", change=" + change + ", previousBalance=" + previousBalance
				+ ", newBalance=" + newBalance + ", inTime=" + inTime
				+ ", outTime=" + outTime + "]";
	}

}
