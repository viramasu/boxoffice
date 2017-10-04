package com.boxoffice.types;

/**
 * Represent a integer range
 * @author viramasu
 */
public class Range {
	private int start;
	private int end;
	
	public Range(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Gets the number of items in the range
	 * @return number of items in the range
	 */
	public int getCount( ) {
		return this.end - this.start;
	}
	
}
