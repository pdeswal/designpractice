package com.pd.util;

import java.time.LocalDateTime;

import com.pd.entities.Station;

public class FareCalculator {
	public double getFare(Station source, Station dest, LocalDateTime time){
		return source.getDistance(dest) * 5.5; // we can implement different fare startegy here and discounts if we want.
	}
}
