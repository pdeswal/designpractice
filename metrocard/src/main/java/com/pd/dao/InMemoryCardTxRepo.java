package com.pd.dao;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

import com.pd.entities.SmartCard;
import com.pd.entities.SmartCardTx;
import com.pd.entities.Station;

public class InMemoryCardTxRepo implements CardTxRepo{

	ConcurrentMap<Station, AtomicLong> stationCounter = new ConcurrentHashMap<>();
	ConcurrentMap<SmartCard, List<SmartCardTx>> completedTx = new ConcurrentHashMap<>();
	ConcurrentMap<SmartCard, SmartCardTx> swipeInTx = new ConcurrentHashMap<>();
	
	@Override
	public List<SmartCardTx> getCardTransactions(SmartCard card) {
		return completedTx.getOrDefault(card, Collections.<SmartCardTx>emptyList());
	}

	@Override
	public SmartCardTx getCardSwipeInTransaction(SmartCard card) {
		return swipeInTx.remove(card);
	}

	@Override
	public void addCardSwipeInTransaction(SmartCardTx tx) {
		swipeInTx.put(tx.getCard(), tx);
	}

	@Override
	public void addCardTransaction(SmartCardTx tx) {
		completedTx.putIfAbsent(tx.getCard(), new LinkedList<SmartCardTx>());
		completedTx.get(tx.getCard()).add(tx);
	}

	@Override
	public long getFootFall(Station station) {
		return stationCounter.getOrDefault(station, new AtomicLong(0)).get();
	}

	@Override
	public void addFootFall(Station station) {
		stationCounter.putIfAbsent(station, new AtomicLong(0));
		stationCounter.get(station).incrementAndGet();
	}

}
