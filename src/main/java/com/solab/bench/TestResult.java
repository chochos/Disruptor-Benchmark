package com.solab.bench;
public class TestResult {
	private final long ct0;
	private final long ct1;
	private final long pt0;
	private final long pt1;
	private final long sum;

	public TestResult(long consumerStartTime, long consumerEndTime, long producerStartTime, long producerEndTime, long sum) {
		ct0 = consumerStartTime;
		ct1 = consumerEndTime;
		pt0 = producerStartTime;
		pt1 = producerEndTime;
		this.sum=sum;
	}

	public long getConsumerStartTime() { return ct0; }
	public long getConsumerEndTime() { return ct1; }
	public long getProducerStartTime() { return pt0; }
	public long getProducerEndTime() { return pt1; }

	public long getConsumerRunningTime() { return ct1 - ct0; }
	public long getProducerRunningTime() { return pt1 - pt0; }

	public long getIndexSum() { return sum; }

	public static TestResult average(TestResult... tests) {
		long ct1=0, pt1=0,sum=0;
		for (TestResult t : tests) {
			ct1+=(t.getConsumerEndTime()-t.getConsumerStartTime());
			pt1+=(t.getProducerEndTime()-t.getProducerStartTime());
			sum+=t.getIndexSum();
		}
		return new TestResult(0, ct1/tests.length, 0, pt1/tests.length, sum/tests.length);
	}

}
