package com.boxoffice.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.boxoffice.core.Movie;
import com.boxoffice.core.SeatConfiguration;
import com.boxoffice.core.ShowInfo;
import com.boxoffice.core.ShowManager;
import com.boxoffice.core.Theatre;
import com.boxoffice.reservation.BookingInfo;
import com.boxoffice.reservation.ReservationManager;
import com.boxoffice.types.Language;
import com.boxoffice.types.Range;
import com.boxoffice.types.SeatType;

/**
 * BoxOfficeController renders the CUI
 * Provides options to make and cancel movie reservations
 * @author viramasu
 */
public class BoxOfficeController {

	private static Scanner scanner = new Scanner(System.in);
	
	public BoxOfficeController() {
		addTestData();
	}
	
	private void bookTickets() throws IOException {
		String movieName = movieSelection();
		ShowInfo showInfo = showSelection(movieName);
		SeatType seatType = seatSelection();
		int numSeats = 0;
		
		do {
			System.out.print("\nNumber of seats : ");
			numSeats = scanner.nextInt();
		} while(numSeats < 0 || numSeats > 20);
		
		BookingInfo bookingInfo = ReservationManager.getInstance().reserveTickets(showInfo, seatType, numSeats);
		
		if(bookingInfo == null) {
			System.out.println("Unable to book tickets. Please check availability!");
		} else {
			System.out.println("Booking successful!");
			System.out.println(bookingInfo);
		}
		
		displayMenu();
	}
	
	private String movieSelection() {
		System.out.println("Select Movie : ");
		List<String> movies = new ArrayList<String>(ShowManager.getInstance().getAllMovies()); 
		
		for(int i = 0; i < movies.size(); i++) {
			System.out.println("(" + i + ") " + movies.get(i).toString());
		}
		
		int input = scanner.nextInt();
		
		if(input < 0 || input > movies.size()-1) {
			return movieSelection();
		}
		
		return movies.get(input);
	}
	
	private ShowInfo showSelection(String movieName) {
		System.out.println("Select Show : ");
		
		List<ShowInfo> shows = ShowManager.getInstance().getShows(movieName); 
		
		for(int i = 0; i < shows.size(); i++) {
			System.out.println("(" + i + ") " + shows.get(i).toString());
		}
		
		int input = scanner.nextInt();
		
		if(input < 0 || input > shows.size()-1) {
			return showSelection(movieName);
		}
		
		return shows.get(input);
	}
	
	private SeatType seatSelection() {
		System.out.println("Select Seat Type : ");
		
		List<SeatType> seatTypes = new ArrayList<SeatType>();
		seatTypes.add(SeatType.GOLD);
		seatTypes.add(SeatType.SILVER);
		seatTypes.add(SeatType.PLATINUM);
		
		for(int i = 0; i < seatTypes.size(); i++) {
			System.out.println("(" + i + ") " + seatTypes.get(i).toString());
		}
		
		int input = scanner.nextInt();
		
		if(input < 0 || input > seatTypes.size()-1) {
			return seatSelection();
		}
		
		return seatTypes.get(input);
	}
	
	private void cancelTickets() throws IOException {
		System.out.println("Enter Booking# to cancel : ");
		int input = scanner.nextInt();
		
		if(ReservationManager.getInstance().cancelTickets(input)) {
			System.out.println("Tickets cancelled successfully!");
		} else {
			System.out.println("Booking not found!");
		}
		
		displayMenu();
	}

	private void checkAvailability() throws IOException {
		String movieName = movieSelection();
		ShowInfo show = showSelection(movieName);
		SeatType seatType = seatSelection();
		int numSeats = 0;
		
		do {
			System.out.print("\nNumber of seats : ");
			numSeats = scanner.nextInt();
		} while(numSeats < 0 || numSeats > 20);
		
		if(ReservationManager.getInstance().checkAvailability(show, seatType, numSeats)) {
			System.out.println(seatType + " Tickets are available for " + show);
		} else {
			System.out.println("Sorry, " + seatType + " Tickets are Not available for " + show);
		}
		
		displayMenu();
	}
	
	private void displayMenu() throws IOException {
		int input = 0;
		System.out.println("\n\nWelcome to BoxOffice");
		System.out.println("(1) Book tickets");
		System.out.println("(2) Check Availability");
		System.out.println("(3) Cancel tickets");
		System.out.println("(4) Exit");
		input = scanner.nextInt();
		switch(input) {
		case 1:
			bookTickets();
			break;
		case 2:
			checkAvailability();
			break;
		case 3:
			cancelTickets();
			break;
		case 4:
			//Exit
			return;
		default:
			displayMenu();
		}
	}
	
	public static void main(String[] args) throws IOException {
		BoxOfficeController controller = new BoxOfficeController();
		controller.displayMenu();
	}
	
	@SuppressWarnings("deprecation")
	public void addTestData() {
		/// Create Movies
		Movie spiderManMovie = new Movie("SpiderMan", 120, Language.ENGLISH);
		Movie batManMovie = new Movie("BatMan", 100, Language.ENGLISH);
		Movie spyDerMovie = new Movie("Spyder", 125, Language.TAMIL);
		Movie newtonMovie = new Movie("Newton", 137, Language.HINDI);
		
		
		/// Create SeatConfiguration
		Map<SeatType, Range> seatMap1 = new HashMap<SeatType, Range>();
		seatMap1.put(SeatType.SILVER, new Range(0, 30));
		seatMap1.put(SeatType.GOLD, new Range(31, 80));
		seatMap1.put(SeatType.PLATINUM, new Range(81, 100));
		
		Map<SeatType, Range> seatMap2 = new HashMap<SeatType, Range>();
		seatMap2.put(SeatType.SILVER, new Range(0, 20));
		seatMap2.put(SeatType.GOLD, new Range(21, 70));
		seatMap2.put(SeatType.PLATINUM, new Range(71, 100));
		
		SeatConfiguration seatConfig1 = new SeatConfiguration(10, 10, seatMap1);
		SeatConfiguration seatConfig2 = new SeatConfiguration(10, 10, seatMap2);
		
		
		/// Create Theatres
		Theatre pvrTheatre = new Theatre("PVR", seatConfig1);
		Theatre vrTheatre = new Theatre("VR Bengaluru", seatConfig1);
		Theatre inoxTheatre = new Theatre("INOX", seatConfig2);
		Theatre imaxTheatre = new Theatre("IMAX", seatConfig2);
		
		
		/// Create Shows
		Map<SeatType, Integer> priceMapDay = new HashMap<SeatType, Integer>();
		priceMapDay.put(SeatType.SILVER, 150);
		priceMapDay.put(SeatType.GOLD, 250);
		priceMapDay.put(SeatType.PLATINUM, 350);
		
		Map<SeatType, Integer> priceMapNight = new HashMap<SeatType, Integer>();
		priceMapNight.put(SeatType.SILVER, 100);
		priceMapNight.put(SeatType.GOLD, 150);
		priceMapNight.put(SeatType.PLATINUM, 250);
																				// (Year - 1900), [0-11], [1-31], [0-23], [0-59]
		ShowManager.getInstance().addShow(new ShowInfo(pvrTheatre, spiderManMovie, new Date(117, 9, 5, 10, 30), priceMapDay));
		ShowManager.getInstance().addShow(new ShowInfo(pvrTheatre, batManMovie, new Date(117, 9, 5, 14, 30), priceMapDay));
		ShowManager.getInstance().addShow(new ShowInfo(pvrTheatre, newtonMovie, new Date(117, 9, 5, 20, 30), priceMapNight));
		
		ShowManager.getInstance().addShow(new ShowInfo(vrTheatre, newtonMovie, new Date(117, 9, 5, 11, 30), priceMapDay));
		ShowManager.getInstance().addShow(new ShowInfo(vrTheatre, spyDerMovie, new Date(117, 9, 5, 20, 30), priceMapNight));
		
		ShowManager.getInstance().addShow(new ShowInfo(inoxTheatre, spiderManMovie, new Date(117, 9, 5, 10, 30), priceMapDay));
		ShowManager.getInstance().addShow(new ShowInfo(inoxTheatre, batManMovie, new Date(117, 9, 5, 14, 30), priceMapDay));
		ShowManager.getInstance().addShow(new ShowInfo(inoxTheatre, newtonMovie, new Date(117, 9, 5, 20, 30), priceMapNight));
		
		ShowManager.getInstance().addShow(new ShowInfo(imaxTheatre, newtonMovie, new Date(117, 9, 5, 11, 30), priceMapDay));
		ShowManager.getInstance().addShow(new ShowInfo(imaxTheatre, batManMovie, new Date(117, 9, 5, 20, 30), priceMapNight));
	}
}
