package com.solab.bench.ring;

import com.lmax.disruptor.*;
import com.solab.bench.*;

public class ItemHandler extends BenchConsumer implements EventHandler<ItemEntry> {

	private Item item;
	private Object lock = new Object();

	public Item consumeItem() {
		return item;
	}

	public void onEvent(final ItemEntry entry, final long sequence, final boolean endOfBatch) throws Exception {
		item = entry.getItem();
		sum += item.getIndex();
		if (item == LastItem.instance) {
			synchronized (lock) { lock.notifyAll();} 
		}
	}

	public void run() {
		sum = 0;
		t0 = System.currentTimeMillis();
		try {
			synchronized(lock) { lock.wait(); }
		} catch (InterruptedException ex) {
			System.out.println("OUCH interrupted!");
		}
		t1 = System.currentTimeMillis();
	}

}
