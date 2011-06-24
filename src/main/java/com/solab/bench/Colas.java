package com.solab.bench;
/** Una prueba muy simple para probar los tiempos de distintas implementaciones de colas (y anillos).
 *
 * @author Enrique Zamudio
 */
public abstract class Colas {

	protected abstract Producer createProducer(int count);
	protected abstract Consumer createConsumer();

	public TestResult runTest(int howMany) {
		Consumer cons = createConsumer();
		Producer prod = createProducer(howMany);
		prod.setCuantos(howMany);
		Thread t = new Thread(cons, "Consumer");
		t.start();
		prod.benchmarkQueue();
		while (t.isAlive()) {
			try { Thread.sleep(1000); } catch (InterruptedException ex){ /*don't try this at home*/ }
		}
		return new TestResult(cons.getStartTime(), cons.getFinishTime(), prod.getStartTime(), prod.getFinishTime());
	}

}
