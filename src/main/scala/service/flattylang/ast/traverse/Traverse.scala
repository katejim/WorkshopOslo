package service.flattylang.ast.traverse

import service.flattylang.ast._

/**
  * Created by kate on 
  * 22.04.16.
  */
object Traverse {
  def depthFirst(node: AbstractNode): Iterator[AbstractNode] = new DFSIterator(node)
}
