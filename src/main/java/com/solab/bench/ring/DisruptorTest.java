package com.solab.bench.ring;

import com.lmax.disruptor.*;
import com.solab.bench.*;

public class DisruptorTest extends Colas {

	private RingBuffer<ItemEntry> rb;
	private BatchEventProcessor<ItemEntry> cons;
	private final ItemHandler handler = new ItemHandler();

	protected BenchConsumer createConsumer() {
		return handler;
	}

	public int pow2(int size) {
		int mask = 0xff;
		for (int i = 0; i < 4; i++) {
			if ((size & mask) > 0) size|=mask;
			mask = mask << 8;
		}
		return size+1;
	}

	protected Producer createProducer(int count) {
		int size = pow2(Math.max(32, count/10));
		System.out.printf("Init ring size %d%n", size);
		rb = new RingBuffer<ItemEntry>(ItemEntry.EVENT_FACTORY, size, ClaimStrategy.Option.SINGLE_THREADED,
			WaitStrategy.Option.YIELDING);
		SequenceBarrier consumerBarrier = rb.newBarrier();
		cons = new BatchEventProcessor<ItemEntry>(rb, consumerBarrier, handler);
		rb.setGatingSequences(cons.getSequence());
		new Thread(cons, "batchcons").start();
		Producer prod = new RingProducer();
		return prod;
	}

	private class RingProducer extends Producer {
		protected void queueItem(Item item) {
			long seq = rb.next();
			ItemEntry entry = rb.get(seq);
			entry.setItem(item);
			rb.publish(seq);
		}
	}

}

