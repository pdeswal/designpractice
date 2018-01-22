package com.pd;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.hamcrest.CustomMatcher;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.pd.entities.MetroCard;
import com.pd.entities.SmartCard;
import com.pd.entities.SmartCardTx;
import com.pd.entities.Station;
import com.pd.exception.InsufficientCardBalanceException;
import com.pd.exception.MimimumCardBalanceException;
import com.pd.service.MetroService;
import com.pd.service.MetroServiceImpl;


public class MetroServiceTest {
	
	MetroService metroService;
	SmartCard card;
	
	@BeforeClass
	public static void setUpBeforeAllTests(){
		
	}
	@Before
	public void setUpBeforeTest(){
		metroService = new MetroServiceImpl();
		card = new MetroCard();
		card.setBalance(100);
		card.setOwner("Pardeep");
	}
	@Test
	public void testCalculateFootFallForStation(){
		metroService.swipeIn(card, Station.A1, LocalDateTime.of(2016, Month.APRIL, 8, 18, 25));
		metroService.swipeOut(card, Station.A6, LocalDateTime.of(2016, Month.APRIL, 8, 19, 25));
		metroService.swipeIn(card, Station.A6, LocalDateTime.of(2016, Month.APRIL, 8, 19, 27));
		metroService.swipeOut(card, Station.A10, LocalDateTime.of(2016, Month.APRIL, 8, 20, 25));
		
		Assert.assertThat("footfall should be 2", metroService.countFootFall(Station.A6), Matchers.equalTo(2l));
		Assert.assertThat("footfall should be 1", metroService.countFootFall(Station.A1), Matchers.equalTo(1l));
		Assert.assertThat("footfall should be 1", metroService.countFootFall(Station.A10), Matchers.equalTo(1l));
	}
	@Test
	public void testCardReport(){
		metroService.swipeIn(card, Station.A1, LocalDateTime.of(2016, Month.APRIL, 8, 18, 25));
		metroService.swipeOut(card, Station.A6, LocalDateTime.of(2016, Month.APRIL, 8, 19, 25));
		metroService.swipeIn(card, Station.A6, LocalDateTime.of(2016, Month.APRIL, 10, 19, 27));
		metroService.swipeOut(card, Station.A10, LocalDateTime.of(2016, Month.APRIL, 10, 20, 25));
		
		List<SmartCardTx> tx = metroService.getCardHistory(card);
		Assert.assertThat("no of transaction on card should be 2", tx.size(), Matchers.equalTo(2));
		Assert.assertThat("", tx.toArray(new SmartCardTx[0]), Matchers.hasItemInArray(new CustomMatcher<SmartCardTx>("") {

			@Override
			public boolean matches(Object obj) {
				SmartCardTx tx = (SmartCardTx)obj;
				return tx.getFare() == 27.5;
			}
		}));
		
		Assert.assertThat("", tx.toArray(new SmartCardTx[0]), Matchers.hasItemInArray(new CustomMatcher<SmartCardTx>("") {

			@Override
			public boolean matches(Object obj) {
				SmartCardTx tx = (SmartCardTx)obj;
				return tx.getFare() == 22.0;
			}
		}));
		
	}
	@Test(expected=MimimumCardBalanceException.class)
	public void testMimimumBalanceException(){
		card.setBalance(1);
		metroService.swipeIn(card, Station.A1, LocalDateTime.of(2016, Month.APRIL, 8, 18, 25));
	}
	
	@Test(expected=InsufficientCardBalanceException.class)
	public void testInsufficientCardBalanceException(){
		card.setBalance(10);
		metroService.swipeIn(card, Station.A1, LocalDateTime.of(2016, Month.APRIL, 8, 18, 25));
		metroService.swipeOut(card, Station.A6, LocalDateTime.of(2016, Month.APRIL, 8, 19, 25));
	}
}
