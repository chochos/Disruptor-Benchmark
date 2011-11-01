package com.solab.bench.ring;

import java.util.concurrent.CountDownLatch;
import com.lmax.disruptor.*;
import com.solab.bench.*;

public class ItemHandler extends BenchConsumer implements EventHandler<ItemEntry> {

	private Item item;
	private CountDownLatch latch;

	public Item consumeItem() {
		return item;
	}

	public void onEvent(final ItemEntry entry, final long sequence, final boolean endOfBatch) throws Exception {
		item = entry.getItem();
		sum += item.getIndex();
		if (item == LastItem.instance) {
			latch.countDown();
		}
	}

	public void run() {
		sum = 0;
		latch = new CountDownLatch(1);
		t0 = System.currentTimeMillis();
		try {
			latch.await();
		} catch (InterruptedException ex) {
			System.out.println("OUCH interrupted!");
		}
		t1 = System.currentTimeMillis();
	}

}
