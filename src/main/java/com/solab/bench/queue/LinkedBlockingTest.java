package com.solab.bench.queue;

import java.util.concurrent.*;
import com.solab.bench.*;

public class LinkedBlockingTest extends Colas {

	private final LinkedBlockingQueue<Item> queue = new LinkedBlockingQueue<Item>();

	protected BenchConsumer createConsumer() {
		return new LinkedConsumer();
	}

	protected Producer createProducer(int count) {
		return new LinkedProducer();
	}

	private class LinkedConsumer extends BenchConsumer {
		protected Item consumeItem() {
			try {
				return queue.take();
			} catch (InterruptedException ex) {
				throw new IllegalStateException(ex);
			}
		}
	}

	private class LinkedProducer extends Producer {
		protected void queueItem(Item item) {
			queue.add(item);
		}
	}

}
