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
  val simplifications: Seq[AbstractSimplification] = Seq(new AlwaysTrueOrFalse)

  def applyAll(node: AbstractNode): AbstractNode =
    simplifications.foldLeft(node)((value: AbstractNode, simplification) => simplification.transform(value))

  // TODO: Implement update children function
  def updateChildren(node: AbstractNode): AbstractNode = {
    def updateBlockChildren(block: Block): Block =
      Block(block.block.map(applyRecursively).filter(_ != EmptyNode).flatMap { case b: Block => b.block case n => Seq(n) })

    node match {
      case Assignement(name, value) => Assignement(name, applyRecursively(value))
      case b: Block => updateBlockChildren(b)
      case If(condition, ifBody, elseBody) => If(applyRecursively(condition), updateBlockChildren(ifBody), updateBlockChildren(elseBody))
      case While(condition, wBody) => While(applyRecursively(condition), applyRecursively(wBody))
      case BinaryExpression(left, right, operation) => BinaryExpression(applyRecursively(left), applyRecursively(right), operation)
      case _ => node
    }
  }

  def applyRecursively(node: AbstractNode): AbstractNode = applyAll(updateChildren(node))

  def run(node: AbstractNode): AbstractNode = {
    ExprEvaluator.withAllVariablesFrom(node) {
      applyRecursively(node)
    }
  }
}
