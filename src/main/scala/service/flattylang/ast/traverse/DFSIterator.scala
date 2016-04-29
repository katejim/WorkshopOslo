package service.flattylang.ast.traverse

import service.flattylang.ast._
import service.flattylang.ast.arifoperations.BinaryExpression

import scala.collection.mutable

/**
  * Created by kate on 
  * 22.04.16.
  */

class DFSIterator(node: AbstractNode) extends Iterator[AbstractNode] {
  private val stack = mutable.Stack[AbstractNode](node)

  def hasNext = stack.nonEmpty

  def next() = {
    val element = stack.pop()
    pushChildren(element)
    element
  }

  def pushChildren(node: AbstractNode) {
    node.children.foreach(stack.push)
  }
}
