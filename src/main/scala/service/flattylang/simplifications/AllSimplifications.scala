package service.flattylang.simplifications

import service.flattylang.ast._
import service.flattylang.ast.arifoperations.BinaryExpression
import service.flattylang.ast.traverse.Traverse
import service.flattylang.ast.variable.{BooleanValue, IntValue, StringValue, Variable}
import service.flattylang.exprevaluator.ExprEvaluator

/**
  * Created by kate on 
  * 22.04.16.
  */

object AllSimplifications {

  // TODO: Create seq of simplifications
  val simplifications: Seq[AbstractSimplification] = ???

  def applyAll(node: AbstractNode): AbstractNode =
    simplifications.foldLeft(node)((value: AbstractNode, simplification) => simplification.transform(value))

  // TODO: Implement update children function
  def updateChildren(node: AbstractNode): AbstractNode = ???

  def applyRecursively(node: AbstractNode): AbstractNode = applyAll(updateChildren(node))

  def run(node: AbstractNode): AbstractNode = {
    ExprEvaluator.withAllVariablesFrom(node) {
      applyRecursively(node)
    }
  }
}
