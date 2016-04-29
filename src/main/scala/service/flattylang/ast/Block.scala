package service.flattylang.ast

/**
  * Created by kate on 
  * 19.04.16.
  */
case class Block(block: Seq[AbstractNode]) extends AbstractNode {
  override def children: Iterator[AbstractNode] = block.toIterator
}
