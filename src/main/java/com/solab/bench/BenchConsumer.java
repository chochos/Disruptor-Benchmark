package com.solab.bench;
/** A very simple consumer, which should be run on its own thread.
 *
 * @author Enrique Zamudio
 */
public abstract class BenchConsumer implements Runnable {

	protected long t0;
	protected long t1;

	protected abstract Item consumeItem();

	public long getStartTime() { return t0; }
	public long getFinishTime() { return t1; }

	public void run() {
		boolean cont = true;
		t0 = System.currentTimeMillis();
		while (cont) {
			Item item = consumeItem();
			cont = item.getDate().getTime() != 0;
		}
		t1 = System.currentTimeMillis();
	}
}
