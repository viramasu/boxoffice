package com.boxoffice.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.boxoffice.types.ShowCriteria;

/**
 * Manages the Movie shows
 * Singleton class
 * @author viramasu
 */
public class ShowManager {
	private static ShowManager instance = null;
	private Map<ShowCriteria, List<ShowInfo>> showMap;
	private Map<String, List<ShowInfo>> movieMap;
	
	
	private ShowManager() {
		showMap = new HashMap<ShowCriteria, List<ShowInfo>>();
		movieMap = new HashMap<String, List<ShowInfo>>();
	}
	
	/**
	 * Gets the Singleton instance of ShowManager
	 * @return Singleton ShowManager object
	 */
	public static ShowManager getInstance() {
		if(instance == null) {
			instance = new ShowManager();
		}
		return instance;
	}
	
	/**
	 * Adds a new show to the collection
	 * @param info Show Information
	 */
	public void addShow(ShowInfo info) {
		// Add to ShowMap
		List<ShowInfo> showInfoList = null;
		ShowCriteria criteria = new ShowCriteria(info.getStartTime(), info.getMovie());
		if(showMap.get(criteria) == null) {
			showMap.put(criteria, new ArrayList<>()); 
		}
		showInfoList = showMap.get(criteria);
		showInfoList.add(info);
		
		// Add to MovieMap
		if(movieMap.get(info.getMovie().getName()) == null) {
			movieMap.put(info.getMovie().getName(), new ArrayList<>()); 
		}
		showInfoList = movieMap.get(info.getMovie().getName());
		showInfoList.add(info);
	}
	
	/**
	 * Gets all the movies available
	 * @return Set of movies
	 */
	public Set<String> getAllMovies() {
		return movieMap.keySet();
	}
	
	/**
	 * Gets all the shows available for the given movie
	 * @param movieName Name of the movie
	 * @return List of shows
	 */
	public List<ShowInfo> getShows(String movieName) {
		return movieMap.get(movieName);
	}
}
