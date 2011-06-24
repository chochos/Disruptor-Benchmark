package com.solab.bench;

import org.junit.*;
public class TestQueues {

	private int count = 10000;

	@Before
	public void warmup() {
		Item.setDelay(600);
		Colas x = new ArrayBlockingTest();
		x.runTest(32);
		x = new LinkedBlockingTest();
		x.runTest(32);
		x = new DisruptorTest();
		x.runTest(32);
	}

	@Test
	public void testArrayQueue() {
		printResult(new ArrayBlockingTest());
	}

	@Test
	public void testLinkedQueue() {
		printResult(new LinkedBlockingTest());
	}

	@Test
	public void testDisruptor() {
		printResult(new DisruptorTest());
	}

	public void printResult(Colas queueTest) {
		System.out.printf("Testing %s%n", queueTest.getClass().getName());
		TestResult tr = queueTest.runTest(count);
		System.out.printf("Consumer ran in %8d milliseconds%n", tr.getConsumerRunningTime());
		System.out.printf("Producer ran in %8d milliseconds%n", tr.getProducerRunningTime());
	}

}
