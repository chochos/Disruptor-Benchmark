package com.solab.bench;

import com.lmax.disruptor.*;

public class ItemHandler extends Consumer implements BatchHandler<ItemEntry> {

	private Object lock = new Object();
	private Item item;

	protected Item consumeItem() {
		Item _item = item;
		if (_item == null) {
			synchronized(lock) {
				try { lock.wait();} catch (InterruptedException ex){}
				_item = item;
				item = null;
			}
		}
		return _item;
	}

	public void onAvailable(final ItemEntry entry) throws Exception {
		synchronized(lock) {
			item = entry.getItem();
			lock.notify();
		}
	}

	public void onCompletion() {}
	public void onEndOfBatch() {}
}
