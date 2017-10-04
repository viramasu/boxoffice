package com.boxoffice.reservation;

import java.util.HashMap;
import java.util.Map;

import com.boxoffice.core.ShowInfo;
import com.boxoffice.types.SeatType;

/**
 * Manages reservations
 * Singleton class
 * @author viramasu
 */
public class ReservationManager {
	private static ReservationManager instance = null;
	private Map<Integer, BookingInfo> reservations;
	
	private ReservationManager() {
		this.reservations = new HashMap<Integer, BookingInfo>();
	}
	
	/**
	 * Gets the Singleton instance of ReservationManager
	 * @return ReservationManager instance
	 */
	public static ReservationManager getInstance() {
		if(instance == null) {
			instance = new ReservationManager();
		}
		return instance;
	}
	
	/**
	 * Reserves tickets for the specified show
	 * @param showInfo Show information
	 * @param seatType Type of seat
	 * @param seatCount Number of seats
	 * @return Booking Information
	 */
	public BookingInfo reserveTickets(ShowInfo showInfo, SeatType seatType, int seatCount) {
		if(!showInfo.isSeatsAvailable(seatType, seatCount)) {
			return null;
		}
		
		int price = showInfo.getPriceMap().get(seatType);
		int totalCost = price * seatCount; 
		
		BookingInfo bookingInfo = new BookingInfo();
		bookingInfo.setShow(showInfo);
		bookingInfo.setSeatType(seatType);
		bookingInfo.setSeatCount(seatCount);
		bookingInfo.setTotalCost(totalCost);
		
		reservations.put(bookingInfo.getBookingId(), bookingInfo);
		showInfo.addReservation(seatType, seatCount);
		
		return bookingInfo;
	}
	
	/**
	 * Checks seat availability for a show
	 * @param showInfo Show information
	 * @param seatType Type of seat
	 * @param seatCount Number of seats
	 * @return true if the required seats are available; false otherwise
	 */
	public boolean checkAvailability(ShowInfo showInfo, SeatType seatType, int seatCount) {
		return showInfo.isSeatsAvailable(seatType, seatCount);
	}
	
	/**
	 * Cancels a ticket
	 * @param bookingId Booking ID
	 * @return true if the cancellation is successful; false otherwise
	 */
	public boolean cancelTickets(int bookingId) {
		if(!this.reservations.containsKey(bookingId)) {
			return false;
		}
		
		BookingInfo bookingInfo = this.reservations.get(bookingId);
		bookingInfo.getShow().cancelReservation(bookingInfo.getSeatType(), bookingInfo.getSeatCount());
		this.reservations.remove(bookingId);
		return true;
	}
}
