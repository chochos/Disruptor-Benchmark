package com.solab.bench;

import java.util.concurrent.*;

public class ArrayBlockingTest extends Colas {

	private ArrayBlockingQueue<Item> queue;

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
		queue = new ArrayBlockingQueue<Item>(Math.min(32, count / 10));
		Producer prod = new Producer(){
			protected void queueItem(Item item) {
				queue.add(item);
			}
		};
		prod.setCuantos(count);
		return prod;
	}

}
