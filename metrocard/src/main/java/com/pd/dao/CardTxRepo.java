package com.pd.dao;

import java.util.List;

import com.pd.entities.SmartCard;
import com.pd.entities.SmartCardTx;
import com.pd.entities.Station;

public interface CardTxRepo {
	public long getFootFall(Station station);
	public void addFootFall(Station station);
	
	public List<SmartCardTx> getCardTransactions(SmartCard card);
	public SmartCardTx getCardSwipeInTransaction(SmartCard card);
	public void addCardSwipeInTransaction(SmartCardTx tx);
	public void addCardTransaction(SmartCardTx tx);
}
