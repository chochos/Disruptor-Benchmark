/** A very simple consumer, which should be run on its own thread.
 *
 * @author Enrique Zamudio
 */
public abstract class Consumer implements Runnable {

	private long t0;
	private long t1;

	public abstract Item consumeItem();

	public long getStartTime() { return t0; }
	public long getFinishTime() { return t1; }

	public void run() {
		boolean cont = true;
		t0 = System.currentTimeMillis();
		while (cont) {
			Item item = consumeItem();
			run = item.getDate().getTime() != 0;
		}
		t1 = System.currentTimeMillis();
	}
}
