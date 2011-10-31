package com.solab.bench;

import java.util.concurrent.*;

public class LinkedBlockingTest extends Colas {

	private final LinkedBlockingQueue<Item> queue = new LinkedBlockingQueue<Item>();

	protected Consumer createConsumer() {
		return new Consumer(){
			protected Item consumeItem() {
				try {
					return queue.take();
				} catch (InterruptedException ex) {
					throw new IllegalStateException(ex);
				}
			}
		};
	}

	protected Producer createProducer(int count) {
		return new Producer(){
			protected void queueItem(Item item) {
				queue.add(item);
			}
		};
	}

}
