package com.solab.multitest

import com.lmax.disruptor._
import java.util.Date

class ItemHandler(kind:Int) extends EventHandler[ItemHolder] with LifecycleAware {

  val stopcount=5000*(kind-1)

  def onEvent(entry:ItemHolder, seq:Long, endOfBatch:Boolean) {
    if (seq % 10000 == stopcount) {
      println("PROC%d: Sleeping for 100ms @ seq ".format(kind, seq))
      Thread.sleep(100)
    }
    if (entry.key.kind == kind) {
      if (entry.item.dateProcessed == null) {
        println("PROC%d: PROCESS item %s of our own kind %d".format(kind, entry.key.value, kind))
        entry.item.dateProcessed=new Date
        if (seq % 10000 == stopcount) {
          entry.item.result=1
        }
      } else {
        //Just checking; this actually never happens
        println("PROC%d: Item was already PROCESSED".format(kind))
      }
	} else {
      println("PROC%d: Ignoring item %s of kind %d".format(kind, entry.key.value, entry.key.kind))
    }
  }

  def onShutdown()=println("PROC%d SHUTDOWN".format(kind))

  def onStart()=println("PROC%d START".format(kind))
}
