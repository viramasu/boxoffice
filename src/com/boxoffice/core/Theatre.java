package com.boxoffice.core;

import java.util.Map;

import com.boxoffice.types.Range;
import com.boxoffice.types.SeatType;

/**
 * Represents a theatre
 * @author viramasu
 */
public class Theatre {
	private String name;
	private SeatConfiguration seatConfig;
	
	public Theatre(String name, SeatConfiguration seatConfig) {
		this.name = name;
		this.seatConfig = seatConfig;
	}
	
	public String getName() {
		return name;
	}

	/**
	 * Gets the seat map of the theatre
	 * @return SeatMap
	 */
	public Map<SeatType, Range> getSeatMap() {
		return seatConfig.getSeatMap();
	}

	@Override
	public String toString() {
		return this.name;
	}
}
