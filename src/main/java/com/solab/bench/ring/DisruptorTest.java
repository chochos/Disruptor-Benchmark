package com.solab.bench;

import com.lmax.disruptor.*;

public class DisruptorTest extends Colas {

	private RingBuffer rb;
	private BatchConsumer<ItemEntry> cons;
	private ProducerBarrier<ItemEntry> producerBarrier;
	private ItemHandler handler = new ItemHandler();

	protected Consumer createConsumer() {
		return handler;
	}

	protected Producer createProducer(int count) {
		EntryFactory<ItemEntry> ef = new EntryFactory<ItemEntry>(){
			public ItemEntry create() { return new ItemEntry(); }
		};
		rb = new RingBuffer(ef, Math.max(32, count/10), ClaimStrategy.Option.SINGLE_THREADED,
			WaitStrategy.Option.YIELDING);
		ConsumerBarrier<ItemEntry> consumerBarrier = rb.createConsumerBarrier();
		cons = new BatchConsumer<ItemEntry>(consumerBarrier, handler);
		producerBarrier = rb.createProducerBarrier(cons);
		new Thread(cons, "batchcons").start();
		Producer prod = new Producer(){
			protected void queueItem(Item item) {
				ItemEntry entry = producerBarrier.nextEntry();
				entry.setItem(item);
				producerBarrier.commit(entry);
			}
		};
		return prod;
	}

}

