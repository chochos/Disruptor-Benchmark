package com.solab.bench;

import com.lmax.disruptor.*;

public class ItemEntry extends AbstractEntry {

	private Item item;

	public void setItem(Item value) {
		item = value;
	}
	public Item getItem() {
		return item;
	}
}
