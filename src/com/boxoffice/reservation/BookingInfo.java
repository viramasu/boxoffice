package com.boxoffice.reservation;

import com.boxoffice.core.ShowInfo;
import com.boxoffice.types.SeatType;

/**
 * Captures booking information
 * @author viramasu
 */
public class BookingInfo {
	private int bookingId;
	private ShowInfo show;
	private SeatType seatType;
	private int seatCount;
	private int totalCost;
	private static int NextBookingId = 100;
	
	public BookingInfo() {
		this.bookingId = NextBookingId++;
	}
	
	public int getBookingId() {
		return bookingId;
	}
	
	public ShowInfo getShow() {
		return show;
	}
	
	public void setShow(ShowInfo show) {
		this.show = show;
	}
	
	public SeatType getSeatType() {
		return seatType;
	}
	
	public void setSeatType(SeatType seatType) {
		this.seatType = seatType;
	}
	
	public int getSeatCount() {
		return seatCount;
	}
	
	public void setSeatCount(int seatCount) {
		this.seatCount = seatCount;
	}
	
	public int getTotalCost() {
		return totalCost;
	}
	
	public void setTotalCost(int totalCost) {
		this.totalCost = totalCost;
	}
	
	@Override
	public String toString() {
		return "Booking# " + bookingId + " - " + seatCount + " " + this.seatType + " seats - Rs " + totalCost + "\n" + show.toString();
	}
}