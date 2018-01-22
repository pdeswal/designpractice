package com.pd.service;

import java.time.LocalDateTime;
import java.util.List;

import com.pd.entities.SmartCard;
import com.pd.entities.SmartCardTx;
import com.pd.entities.Station;

public interface MetroService {
	public void swipeIn(SmartCard card, Station source, LocalDateTime time);
	public void swipeOut(SmartCard card, Station dest, LocalDateTime time);
	public double checkBalance(SmartCard card);
	public void rechargeCard(SmartCard card, double amount);
	
	public long countFootFall(Station station);
	public List<SmartCardTx> getCardHistory(SmartCard card);
	
}
