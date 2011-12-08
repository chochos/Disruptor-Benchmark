package com.solab.multitest

import com.lmax.disruptor._

object Test extends App {

  val ring = new RingBuffer[ItemHolder](ItemHolderFactory, new MultiThreadedClaimStrategy(65536), new YieldingWaitStrategy)

  val prod1 = new Thread(new Producer(1, ring))
  val prod2 = new Thread(new Producer(2, ring))

  val consumerBarrier = ring.newBarrier()
  val handler1 = new ItemHandler(1)
  val handler2 = new ItemHandler(2)
  val consumer1 = new BatchEventProcessor[ItemHolder](ring, consumerBarrier, handler1)
  val consumer2 = new BatchEventProcessor[ItemHolder](ring, consumerBarrier, handler2)
  ring.setGatingSequences(consumer1.getSequence, consumer2.getSequence)

  println("Starting consumers")
  val tc1 = new Thread(consumer1)
  val tc2 = new Thread(consumer2)
  tc1.start()
  tc2.start()
  println("Starting producers")
  prod1.start()
  prod2.start()

  prod1.join()
  prod2.join()
  tc1.join()
  tc2.join()
  sys.exit()
}
