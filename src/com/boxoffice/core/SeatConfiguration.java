package com.boxoffice.core;

import java.util.Map;

import com.boxoffice.types.Range;
import com.boxoffice.types.SeatType;

/**
 * Represents the Seat configuration of theatre
 * @author viramasu
 */
public class SeatConfiguration {
	private int numRows;
	private int numSeatsPerRow;
	private Map<SeatType, Range> seatMap;
	
	public SeatConfiguration(int numRows, int numSeatsPerRow, Map<SeatType, Range> seatMap) {
		this.numRows = numRows;
		this.numSeatsPerRow = numSeatsPerRow;
		this.seatMap = seatMap;
	}

	public Map<SeatType, Range> getSeatMap() {
		return seatMap;
	}
}
