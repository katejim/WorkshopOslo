package service.flattylang.simplifications

import service.flattylang.ast.AbstractNode

/**
  * Created by kate on 
  * 22.04.16.
  */

// TODO: create new Simplification
// 1. create an instance of AbstractSimplification
// 2. implement TrueOrFalse simplification that will remove unused blocks of code.
// E.g. if (true) 1 else 2  will convert to  1

trait AbstractSimplification {
  def transform(node: AbstractNode): AbstractNode
}