package com.solab.bench;
/** A very simple item that producers produce, and consumers... well, consume.
 *
 * @author Enrique Zamudio
 */
public class Item {
	private java.util.Date date = new java.util.Date();

	public Item() {
		//Do something computationally expensive, to keep the producer busy
		for (int i = 2; i < 1000; i++) {
			for (int j = 1; j < i; j++) {
				int mod = i%j;
			}
		}
	}

	public java.util.Date getDate() {
		return date;
	}

}
