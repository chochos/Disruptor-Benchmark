package com.solab.bench;
/** A very simple abstract producer. */
public abstract class Producer {

	private int cuantos = 1000000;
	private long t0;
	private long t1;

	public long getStartTime() { return t0; }
	public long getFinishTime() { return t1; }

	public void setCuantos(int value) {
		cuantos = value;
	}

	protected abstract void queueItem(Item item);

	public void benchmarkQueue() {
		t0 = System.currentTimeMillis();
		for (int i = 0; i < cuantos; i++) {
			queueItem(new Item());
			//try { Thread.sleep(10);} catch(InterruptedException ex){}
		}
		queueItem(new LastItem());
		t1 = System.currentTimeMillis();
	}

}
