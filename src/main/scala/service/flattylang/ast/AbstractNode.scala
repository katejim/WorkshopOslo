package service.flattylang.ast

/**
  * Created by kate on 
  * 19.04.16.
  */
trait AbstractNode {
  this: Product =>

  def children: Iterator[AbstractNode] = productIterator.collect {
    case node: AbstractNode => node
  }
}
