package com.solab.bench;
/** Una prueba muy simple para probar los tiempos de distintas implementaciones de colas (y anillos).
 *
 * @author Enrique Zamudio
 */
public abstract class Colas {

	private final int count;

	public Colas(int itemCount) {
		count = itemCount;
	}

	protected abstract Producer createProducer(int count);
	protected abstract BenchConsumer createConsumer();

	public TestResult runTest() throws InterruptedException {
		BenchConsumer cons = createConsumer();
		Producer prod = createProducer(count);
		prod.setCuantos(count);
		Thread t = new Thread(cons, "Consumer");
		t.start();
		prod.benchmarkQueue();
		t.join();
		return new TestResult(cons.getStartTime(), cons.getFinishTime(), prod.getStartTime(), prod.getFinishTime(), cons.getSum());
	}

}
