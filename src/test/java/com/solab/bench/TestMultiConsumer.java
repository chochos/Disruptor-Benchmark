package com.solab.bench;

import java.util.concurrent.Executors;
import com.lmax.disruptor.*;
import com.lmax.disruptor.dsl.Disruptor;
import org.junit.Test;
import com.solab.bench.ring.ItemEntry;

public class TestMultiConsumer {

	private final Disruptor<ItemEntry> disruptor = new Disruptor<ItemEntry>(ItemEntry.EVENT_FACTORY,
		Executors.newCachedThreadPool(), new SingleThreadedClaimStrategy(256), new YieldingWaitStrategy());

	private final EventHandler<ItemEntry> h1=new EventHandler<ItemEntry>(){
		public void onEvent(final ItemEntry entry, final long seq, final boolean eob) throws Exception {
			if (entry == null) {
				System.out.println("HANDLER1 null entry");
			} else if (entry.getItem() == null) {
				System.out.println("HANDLER1 null item");
			} else {
				System.out.printf("HANDLER1 Processing item %d%n", entry.getItem().getIndex());
			}
		}
	};
	private final EventHandler<ItemEntry> h2=new EventHandler<ItemEntry>(){
		public void onEvent(final ItemEntry entry, final long seq, final boolean eob) throws Exception {
			if (entry == null) {
				System.out.println("HANDLER2 null entry");
			} else if (entry.getItem() == null) {
				System.out.println("HANDLER2 null item");
			} else {
				System.out.printf("HANDLER2 Processing item %d%n", entry.getItem().getIndex());
			}
		}
	};
	private final EventTranslator<ItemEntry> xlate = new EventTranslator<ItemEntry>(){
		public ItemEntry translateTo(final ItemEntry entry, final long seq) {
			System.out.printf("Translating entry %s with item %s seq %d%n", entry, entry.getItem(), seq);
			entry.setItem(new Item());
			return entry;
		}
	};

	@Test
	public void test() throws InterruptedException {
		disruptor.handleEventsWith(h2, h1);
		final RingBuffer<ItemEntry> rb = disruptor.start();
		disruptor.publishEvent(xlate);
		disruptor.publishEvent(xlate);
		Thread.sleep(500);
	}

}
