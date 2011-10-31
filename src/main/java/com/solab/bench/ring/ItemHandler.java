package com.solab.bench.ring;

import com.lmax.disruptor.*;
import com.solab.bench.*;

public class ItemHandler extends BenchConsumer implements BatchHandler<ItemEntry> {

	private final Object lock = new Object();

	protected Item consumeItem() {
		return null;
	}

	public void onAvailable(final ItemEntry entry) throws Exception {
		if (t0 == 0)
			t0 = System.currentTimeMillis();
		else if (entry.getItem().getDate().getTime() == 0) {
			t1 = System.currentTimeMillis();
			synchronized(lock) { lock.notify(); }
		}
	}

	public void run() {
		synchronized(lock) {
			try { lock.wait(); } catch (InterruptedException ex){}
		}
	}

	public void onCompletion() {}
	public void onEndOfBatch() {}
}
