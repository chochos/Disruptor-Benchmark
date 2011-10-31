package com.solab.bench.queue;

import java.util.concurrent.*;
import com.solab.bench.*;

public class LinkedBlockingTest extends Colas {

	private final LinkedBlockingQueue<Item> queue = new LinkedBlockingQueue<Item>();
	private final Producer producer = new Producer(){
		protected void queueItem(Item item) {
			queue.add(item);
		}
	};
	private final BenchConsumer consumer = new BenchConsumer(){
		protected Item consumeItem() {
			try {
				return queue.take();
			} catch (InterruptedException ex) {
				throw new IllegalStateException(ex);
			}
		}
	};

	public LinkedBlockingTest(int count) { super(count); }

	protected BenchConsumer createConsumer() {
		return consumer;
	}

	protected Producer createProducer(int count) {
		queue.clear();
		return producer;
	}

}
