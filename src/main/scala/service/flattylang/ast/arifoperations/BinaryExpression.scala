package service.flattylang.ast.arifoperations

import service.flattylang.ast.AbstractNode


/**
  * Created by kate on 
  * 22.04.16.
  */
case class BinaryExpression(left: AbstractNode,
                       right: AbstractNode, operation: String) extends AbstractNode
