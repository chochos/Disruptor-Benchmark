package com.solab.bench.ring;

import com.lmax.disruptor.EventFactory;
import com.solab.bench.Item;

public final class ItemEntry {

	private Item item;

	public void setItem(Item value) {
		item = value;
	}
	public Item getItem() {
		return item;
	}

	public static final EventFactory<ItemEntry> EVENT_FACTORY = new EventFactory<ItemEntry>(){

		public ItemEntry newInstance() { return new ItemEntry(); }

	};

}
