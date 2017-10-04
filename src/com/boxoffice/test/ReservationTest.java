package com.boxoffice.test;

import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boxoffice.controller.BoxOfficeController;
import com.boxoffice.core.ShowInfo;
import com.boxoffice.core.ShowManager;
import com.boxoffice.reservation.BookingInfo;
import com.boxoffice.reservation.ReservationManager;
import com.boxoffice.types.SeatType;

/**
 * Test cases for Movie reservation system
 * @author viramasu
 */
public class ReservationTest {
	
	private static BoxOfficeController controller;
	
	/**
	 * Creates the required test data
	 */
	@BeforeClass
	public static void initializeTestData() {
		controller = new BoxOfficeController();
	}
	
	/**
	 * Tests seat availability
	 */
	@Test
	public void checkSuccessfulSeatAvailability() {
		ShowInfo showInfo = ShowManager.getInstance().getShows("Spyder").get(0);		
		boolean isAvailable = ReservationManager.getInstance().checkAvailability(showInfo, SeatType.GOLD, 5);
		Assert.assertTrue(isAvailable);
	}
	
	/**
	 * Tests availability exceeding the seat limit for a particular seat type
	 */
	@Test
	public void checkUnsuccessfulSeatAvailability() {
		ShowInfo showInfo = ShowManager.getInstance().getShows("Newton").get(0);		
		boolean isAvailable = ReservationManager.getInstance().checkAvailability(showInfo, SeatType.PLATINUM, 50);
		Assert.assertFalse(isAvailable);
	}
	
	/**
	 * Tests a valid ticket booking
	 */
	@Test
	public void validTicketBooking() {
		ShowInfo showInfo = ShowManager.getInstance().getShows("Newton").get(0);
		BookingInfo bookingInfo = ReservationManager.getInstance().reserveTickets(showInfo, SeatType.SILVER, 6);
		Assert.assertNotNull("Booking was not successful!", bookingInfo);
	}
	
	/**
	 * Tests booking more than available seats in a particular seat type
	 */
	@Test
	public void exceedingSeatLimitBooking() {
		ShowInfo showInfo = ShowManager.getInstance().getShows("BatMan").get(0);
		BookingInfo bookingInfo = ReservationManager.getInstance().reserveTickets(showInfo, SeatType.GOLD, 60);
		Assert.assertNull("Booking is successful!", bookingInfo);
	}
	
	/**
	 * Tests valid cancellation request
	 */
	@Test
	public void validBookingIdCancellationRequest() {
		ShowInfo showInfo = ShowManager.getInstance().getShows("BatMan").get(0);
		BookingInfo bookingInfo = ReservationManager.getInstance().reserveTickets(showInfo, SeatType.SILVER, 6);
		boolean isSuccess = ReservationManager.getInstance().cancelTickets(bookingInfo.getBookingId());
		Assert.assertTrue(isSuccess);
	}
	
	/**
	 * Tests failing Cancellation request by providing invalid booking id
	 */
	@Test
	public void nonExistingBookingIdCancellation() {
		ShowInfo showInfo = ShowManager.getInstance().getShows("Newton").get(0);
		BookingInfo bookingInfo = ReservationManager.getInstance().reserveTickets(showInfo, SeatType.SILVER, 6);
		boolean isSuccess = ReservationManager.getInstance().cancelTickets(bookingInfo.getBookingId() + 50);
		Assert.assertFalse(isSuccess);
	}
	
	/**
	 * Tests finding a Movie which is not running in any of the theaters
	 */
	@Test
	public void nonExistingMovieSelection() {
		List<ShowInfo> showInfoList = ShowManager.getInstance().getShows("Bahubali");
		Assert.assertNull(showInfoList);
	}
	
	/**
	 * Tests partial availability of tickets for booking. 
	 * After booking 10 seats, remaining seats available is < 30
	 */
	@Test
	public void partialAvailabilityForBooking() {
		ShowInfo showInfo = ShowManager.getInstance().getShows("Newton").get(0);
		BookingInfo bookingInfo1 = ReservationManager.getInstance().reserveTickets(showInfo, SeatType.SILVER, 10);
		Assert.assertNotNull("Booking was not successful!", bookingInfo1);
		BookingInfo bookingInfo2 = ReservationManager.getInstance().reserveTickets(showInfo, SeatType.SILVER, 30);
		Assert.assertNull("Booking is successful!", bookingInfo2);
	}
}
