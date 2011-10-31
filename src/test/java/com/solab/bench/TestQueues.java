package com.solab.bench;

import org.junit.*;

public class TestQueues {

	private int count = 50000;

	@BeforeClass
	public static void warmup() {
		Item.setDelay(900);
		Colas x = new com.solab.bench.queue.ArrayBlockingTest();
		x.runTest(32);
		x = new com.solab.bench.queue.LinkedBlockingTest();
		x.runTest(32);
		x = new com.solab.bench.ring.DisruptorTest();
		x.runTest(32);
	}

	@Test
	public void testArrayQueue() {
		printResult(new com.solab.bench.queue.ArrayBlockingTest());
	}

	@Test
	public void testLinkedQueue() {
		printResult(new com.solab.bench.queue.LinkedBlockingTest());
	}

	@Test
	public void testDisruptor() {
		printResult(new com.solab.bench.ring.DisruptorTest());
	}

	public void printResult(Colas queueTest) {
		System.out.printf("Testing %s%n", queueTest.getClass().getName());
		TestResult tr = queueTest.runTest(count);
		System.out.printf("Consumer ran in %8d milliseconds%n", tr.getConsumerRunningTime());
		System.out.printf("Producer ran in %8d milliseconds%n", tr.getProducerRunningTime());
	}

}
