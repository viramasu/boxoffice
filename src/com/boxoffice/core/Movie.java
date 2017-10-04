package com.boxoffice.core;

import java.util.HashMap;
import java.util.Map;

import com.boxoffice.types.Language;

/**
 * Represents a movie
 * @author viramasu
 */
public class Movie {
	private String name;
	private int duration;
	private Map<String, String> movieCast;
	private Language language;
	
	public Movie(String name, int duration, Language language) {
		this.name = name;
		this.duration = duration;
		this.language = language;
		this.movieCast = new HashMap<String, String>();
	}

	public String getName() {
		return name;
	}
	
	@Override
	public int hashCode() {
		return getName().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return getName().equals(((Movie)obj).getName());
	}

}
