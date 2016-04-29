package service.flattylang.simplifications

import service.flattylang.ast.variable.BooleanValue
import service.flattylang.ast.{AbstractNode, EmptyNode, If, While}
import service.flattylang.exprevaluator.ExprEvaluator

/**
  * Created by kate on 
  * 29.04.16.
  */
class AlwaysTrueOrFalse extends AbstractSimplification{
  override def transform(node: AbstractNode): AbstractNode = {
    node match {
      case While(condition, body) =>
        ExprEvaluator.evaluate(condition) match {
          case BooleanValue(value) if !value => EmptyNode()
          case _ => node
        }
      case If(condition, ifBody, elseBody) =>
        ExprEvaluator.evaluate(condition) match {
          case BooleanValue(value) => if (value) ifBody else elseBody
          case _ => node
        }
      case _ => node //nothing should change
    }
  }
}
