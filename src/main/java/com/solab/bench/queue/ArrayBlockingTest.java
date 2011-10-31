package com.solab.bench.queue;

import java.util.concurrent.*;
import com.solab.bench.*;

public class ArrayBlockingTest extends Colas {

	private final ArrayBlockingQueue<Item> queue;
	private final BenchConsumer consumer = new BenchConsumer() {
		protected Item consumeItem() {
			try {
				return queue.take();
			} catch (InterruptedException ex) {
				throw new IllegalStateException(ex);
			}
		}
	};
	private final Producer producer = new Producer() {
		protected void queueItem(Item item) {
			queue.add(item);
		}
	};

	public ArrayBlockingTest(int count) {
		super(count);
		queue = new ArrayBlockingQueue<Item>(Math.max(32, count+1));
	}

	protected BenchConsumer createConsumer() {
		return consumer;
	}

	protected Producer createProducer(int count) {
		queue.clear();
		return producer;
	}

}
