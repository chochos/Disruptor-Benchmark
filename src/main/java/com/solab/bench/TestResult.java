package com.solab.bench;
public class TestResult {
	private final long ct0;
	private final long ct1;
	private final long pt0;
	private final long pt1;

	public TestResult(long consumerStartTime, long consumerEndTime, long producerStartTime, long producerEndTime) {
		ct0 = consumerStartTime;
		ct1 = consumerEndTime;
		pt0 = producerStartTime;
		pt1 = producerEndTime;
	}

	public long getConsumerStartTime() { return ct0; }
	public long getConsumerEndTime() { return ct1; }
	public long getProducerStartTime() { return pt0; }
	public long getProducerEndTime() { return pt1; }

	public long getConsumerRunningTime() { return ct1 - ct0; }
	public long getProducerRunningTime() { return pt1 - pt0; }

}
