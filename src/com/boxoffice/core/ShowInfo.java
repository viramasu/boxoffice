package com.boxoffice.core;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.boxoffice.types.SeatType;

/**
 * Represents a Movie show
 * @author viramasu
 */
public class ShowInfo {
	private Theatre theatre;
	private Movie movie;
	private Date startTime; 
	private Map<SeatType, Integer> reservations;
	private Map<SeatType, Integer> priceMap;

	public ShowInfo(Theatre theatre, Movie movie, Date startTime, Map<SeatType, Integer> priceMap) {
		this.theatre = theatre;
		this.movie = movie;
		this.startTime = startTime;
		this.priceMap = priceMap;
		this.reservations = new HashMap<SeatType, Integer>();
		this.initializeReservations();
	}
	
	private void initializeReservations() {
		int silverSeatsCount = this.theatre.getSeatMap().get(SeatType.SILVER).getCount();
		int goldSeatsCount = this.theatre.getSeatMap().get(SeatType.GOLD).getCount();
		int platinumSeatsCount = this.theatre.getSeatMap().get(SeatType.PLATINUM).getCount();
		
		this.reservations.put(SeatType.SILVER, silverSeatsCount);
		this.reservations.put(SeatType.GOLD, goldSeatsCount);
		this.reservations.put(SeatType.PLATINUM, platinumSeatsCount);
	}
	
	/**
	 * Adds a new reservation
	 * @param seatType Type of seat
	 * @param count Number of seats
	 * @return true if the booking is successful; false otherwise
	 */
	public boolean addReservation(SeatType seatType, int count) {
		if(!this.isSeatsAvailable(seatType, count)) {
			return false;
		}
		else {
			int availableSeats = this.reservations.get(seatType);
			this.reservations.put(seatType, availableSeats - count);
			return true;
		}
	}
	
	/**
	 * Cancels the reservation
	 * @param seatType Type of seat
	 * @param count Number of seats
	 * @return true if the cancellation is successful; false otherwise
	 */
	public boolean cancelReservation(SeatType seatType, int count) {
		int availableSeats = this.reservations.get(seatType);
		this.reservations.put(seatType, availableSeats + count);
		return true;
	}
	
	/**
	 * Returns if the required seats are available
	 * @param seatType Type of seat
	 * @param count Number of seats
	 * @return true if the seats are available; false otherwise
	 */
	public boolean isSeatsAvailable(SeatType seatType, int count) {
		return this.reservations.get(seatType) >= count;
	}

	public Theatre getTheatre() {
		return theatre;
	}

	public Movie getMovie() {
		return movie;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Map<SeatType, Integer> getReservations() {
		return reservations;
	}

	public Map<SeatType, Integer> getPriceMap() {
		return priceMap;
	}
	
	@Override
	public String toString() {
		return "[" + this.getTheatre() + " Theatre] - " + this.getMovie().getName() + " @ " + new SimpleDateFormat("EEE dd-MMM HH:mm").format(this.startTime);
	}
	
}
