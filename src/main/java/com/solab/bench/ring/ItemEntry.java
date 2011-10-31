package com.solab.bench.ring;

import com.lmax.disruptor.*;
import com.solab.bench.Item;

public class ItemEntry extends AbstractEntry {

	private Item item;

	public void setItem(Item value) {
		item = value;
	}
	public Item getItem() {
		return item;
	}
}
