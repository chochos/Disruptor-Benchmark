package com.solab.multitest

import com.lmax.disruptor.RingBuffer

class Producer(kind:Int, ring:RingBuffer[ItemHolder]) extends Runnable {

  def run() {
    1 to 50000 map { i=>
      val item = new Item(kind, i.toString)
      val seq = ring.next()
      val holder = ring.get(seq)
      holder.item = item
      holder.key = item.key
      ring.publish(seq)
    }
    println("Producer %d done.".format(kind))
  }
}
