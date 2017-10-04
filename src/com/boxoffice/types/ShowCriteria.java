package com.boxoffice.types;

import java.util.Date;

import com.boxoffice.core.Movie;
/**
 * Search criteria to query shows
 * @author viramasu
 */
public class ShowCriteria {
	public Date date;
	public Movie movie;
	
	public ShowCriteria(Date date, Movie movie) {
		this.date = date;
		this.movie = movie;
	}

	@Override
	public int hashCode() {
		return date.hashCode() * movie.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.date.equals(((ShowCriteria)obj).date) && this.movie.equals(((ShowCriteria)obj).movie);
	}
}
