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
		if (item.getDate().getTime() == 0) {
			synchronized (lock) { lock.notify();} 
		}
	}

	public void run() {
		t0 = System.currentTimeMillis();
		try {
			synchronized(lock) { lock.wait(); }
		} catch (InterruptedException ex) {
			System.out.println("OUCH interrupted!");
		}
		t1 = System.currentTimeMillis();
	}

}
