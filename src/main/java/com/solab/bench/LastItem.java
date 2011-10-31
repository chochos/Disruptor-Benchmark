package com.solab.bench;
/** A special kind of item to indicate the end of the test.
 * 
 * @author Enrique Zamudio
 */
public class LastItem extends Item {
	private final java.util.Date end = new java.util.Date(0);

	public java.util.Date getDate() {
		return end;
	}

}
