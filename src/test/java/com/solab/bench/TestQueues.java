package com.solab.bench;

import org.junit.*;

public class TestQueues {

	private int count = 50000;
	private int testCount = 10;

	@BeforeClass
	public static void warmup() {
		Item.setDelay(900);
	}

	@Test
	public void testArrayQueue() throws InterruptedException {
		Colas x = new com.solab.bench.queue.ArrayBlockingTest(count);
		x.runTest();
		printResult(x);
	}

	@Test
	public void testLinkedQueue() throws InterruptedException {
		Colas x = new com.solab.bench.queue.LinkedBlockingTest(count);
		x.runTest();
		printResult(x);
	}

	@Test
	public void testDisruptor() throws InterruptedException {
		Colas x = new com.solab.bench.ring.DisruptorTest(count);
		x.runTest();
		printResult(x);
	}

	public void printResult(Colas queueTest) throws InterruptedException {
		System.out.printf("Testing %s %d times with %d items%n", queueTest.getClass().getSimpleName(), testCount, count);
		TestResult tr[] = new TestResult[testCount];
		for (int i = 0; i < testCount; i++) {
			tr[i] = queueTest.runTest();
		}
		TestResult avg = TestResult.average(tr);
		System.out.printf("Consumer ran in %8d milliseconds%n", avg.getConsumerRunningTime());
		System.out.printf("Producer ran in %8d milliseconds%n", avg.getProducerRunningTime());
		System.out.printf("SUM(index) == %d%n", avg.getIndexSum());
	}

}
