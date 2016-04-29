package service.flattylang.ast

/**
  * Created by kate on 
  * 19.04.16.
  */
case class If(condition: AbstractNode, ifBody: Block, elseBody: Block) extends AbstractNode
