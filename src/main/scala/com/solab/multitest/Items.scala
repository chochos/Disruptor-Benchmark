package com.solab.multitest

import java.util.Date
import com.lmax.disruptor.EventFactory

/** A simple object containing some mutable and immutable properties. */
class Item(kind:Int, value:String) {
  val dateCreated=new Date
  var dateProcessed:Date=_
  var result:Int=0

  def key=ItemKey(kind, value)
}

/** A case class used to identify items based on some of their properties. */
case class ItemKey(kind:Int, value:String)

class ItemHolder {
  var key:ItemKey=_
  var item:Item=_
}

object ItemHolderFactory extends EventFactory[ItemHolder] {
  def newInstance=new ItemHolder
}
