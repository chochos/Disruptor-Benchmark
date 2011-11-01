package com.solab.bench.ring;

import com.lmax.disruptor.*;
import com.solab.bench.*;

public class DisruptorTest extends Colas {

	private final RingBuffer<ItemEntry> rb;
	private final ItemHandler handler = new ItemHandler();
	private final Producer prod = new Producer(){
		protected void queueItem(Item item) {
			long seq = rb.next();
			ItemEntry entry = rb.get(seq);
			entry.setItem(item);
			rb.publish(seq);
		}
	};

	public DisruptorTest(int count) {
		super(count);
		int size = (int)Math.pow(2,32-Integer.numberOfLeadingZeros(Math.max(32, count)));
		System.out.printf("Init ring size %d%n", size);
		rb = new RingBuffer<ItemEntry>(ItemEntry.EVENT_FACTORY, size, ClaimStrategy.Option.SINGLE_THREADED,
			WaitStrategy.Option.YIELDING);
		SequenceBarrier consumerBarrier = rb.newBarrier();
		BatchEventProcessor<ItemEntry> cons = new BatchEventProcessor<ItemEntry>(rb, consumerBarrier, handler);
		rb.setGatingSequences(cons.getSequence());
		new Thread(cons, "batchcons").start();
	}

	protected BenchConsumer createConsumer() {
		return handler;
	}

	protected Producer createProducer(int count) {
		return prod;
	}

}

