package com.pd.service;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;

import com.pd.dao.CardTxRepo;
import com.pd.dao.InMemoryCardTxRepo;
import com.pd.entities.SmartCard;
import com.pd.entities.SmartCardTx;
import com.pd.entities.Station;
import com.pd.entities.TxType;
import com.pd.exception.InsufficientCardBalanceException;
import com.pd.exception.MimimumCardBalanceException;
import com.pd.util.FareCalculator;
import com.pd.util.MetroConstants;

public class MetroServiceImpl implements MetroService{
	protected CardTxRepo cardTxRepo = new InMemoryCardTxRepo();
	protected FareCalculator fareCalculator = new FareCalculator();

	@Override
	public void swipeIn(SmartCard card, Station source, LocalDateTime time) {
		if(card.getBalance() < MetroConstants.MIN_BALANCE){
			throw new MimimumCardBalanceException(MessageFormat.format("Minimum balance of Rs %s is required at Swipe In", MetroConstants.MIN_BALANCE));
		}
		
		SmartCardTx swipeInTx = new SmartCardTx();
		swipeInTx.setCard(card);
		swipeInTx.setInTime(time);
		swipeInTx.setSource(source);
		swipeInTx.setTxType(TxType.COMMUTE);
		cardTxRepo.addCardSwipeInTransaction(swipeInTx);
		cardTxRepo.addFootFall(source);
	}

	@Override
	public void swipeOut(SmartCard card, Station dest, LocalDateTime time) {
		SmartCardTx swipeInTx = cardTxRepo.getCardSwipeInTransaction(card);
		double fare = fareCalculator.getFare(swipeInTx.getSource(), dest, time);
		if(fare > card.getBalance()){
			throw new InsufficientCardBalanceException(MessageFormat.format("Card Balance is insufficient balance is : %s, fare is: %s",card.getBalance(), fare));
		}
		
		double prevBalance = card.getBalance();
		double newBalance = card.getBalance() - fare;
		swipeInTx.setChange(newBalance - prevBalance);
		swipeInTx.setFare(fare);
		swipeInTx.setDestination(dest);
		swipeInTx.setOutTime(time);
		swipeInTx.setDistance(dest.getDistance(swipeInTx.getSource()));
		swipeInTx.setPreviousBalance(newBalance);
		swipeInTx.setNewBalance(newBalance);
		card.setBalance(newBalance);

		cardTxRepo.addCardTransaction(swipeInTx);
		
		cardTxRepo.addFootFall(dest);
		
	}

	@Override
	public double checkBalance(SmartCard card) {
		return card.getBalance();
		
	}

	@Override
	public void rechargeCard(SmartCard card, double amount) {
		System.out.println("recharging card with amt:  " + amount);
		System.out.println("card previous balance: " + card.getBalance());
		SmartCardTx tx = new SmartCardTx();
		double prevBalance = card.getBalance();
		double newBalance = card.getBalance() + amount;
		tx.setTxType(TxType.RECHARGE);
		tx.setChange(amount);
		tx.setPreviousBalance(prevBalance);
		tx.setNewBalance(newBalance);
		card.setBalance(newBalance);
		System.out.println("card new balance: " + card.getBalance());
	}

	@Override
	public long countFootFall(Station station) {
		return cardTxRepo.getFootFall(station);
	}

	@Override
	public List<SmartCardTx> getCardHistory(SmartCard card) {
		return cardTxRepo.getCardTransactions(card);
	}
	
}
