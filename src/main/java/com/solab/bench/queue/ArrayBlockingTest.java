package com.solab.bench.queue;

import java.util.concurrent.*;
import com.solab.bench.*;

public class ArrayBlockingTest extends Colas {

	private ArrayBlockingQueue<Item> queue;

	protected BenchConsumer createConsumer() {
		return new ArrayConsumer();
	}

	protected Producer createProducer(int count) {
		queue = new ArrayBlockingQueue<Item>(Math.max(32, count / 10));
		return new ArrayProducer();
	}

	private class ArrayConsumer extends BenchConsumer {
		protected Item consumeItem() {
			try {
				return queue.take();
			} catch (InterruptedException ex) {
				throw new IllegalStateException(ex);
			}
		}
	}

	private class ArrayProducer extends Producer {
		protected void queueItem(Item item) {
			queue.add(item);
		}
	}

}
