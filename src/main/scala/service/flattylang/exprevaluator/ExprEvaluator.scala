package service.flattylang.exprevaluator


import service.flattylang.ast._
import service.flattylang.ast.arifoperations._
import service.flattylang.ast.variable._

import scala.collection.mutable

/**
  * Created by kate on 
  * 25.04.16.
  */
object ExprEvaluator {
  private val context = mutable.HashMap[String, AbstractNode]()

  private def collectVariables(node: AbstractNode): Unit = {
    node match {
      case Assignement(name, value) =>
        context += ((name, value))
      case _ =>
    }
    node.children.foreach(collectVariables)
  }

  private def clearContext() = context.clear()

  def withAllVariablesFrom[T](node: AbstractNode)(body: => T) = {
    collectVariables(node)
    val result = body
    clearContext()
    result
  }

  def evaluate(node: AbstractNode): AbstractNode = {
    val onePass = node match {
      case int@IntValue(value) => int
      case bool@BooleanValue(value) => bool
      case str@StringValue(value) => str
      case BinaryExpression(left, right, op) =>
        val leftEvaluate = evaluate(left)
        val rightEvaluate = evaluate(right)
        (leftEvaluate, rightEvaluate) match {
          case (StringValue(l), StringValue(r)) =>
            op match {
              case "==" => BooleanValue(l == r)
              case "!=" => BooleanValue(l != r)
              case "+" => StringValue(l + r)
              case _ => BinaryExpression(leftEvaluate, rightEvaluate, op)
            }
          case (BooleanValue(l), BooleanValue(r)) =>
            op match {
              case "==" => BooleanValue(l == r)
              case "!=" => BooleanValue(l != r)
              case _ => BinaryExpression(leftEvaluate, rightEvaluate, op)
            }
          case (IntValue(l), IntValue(r)) =>
            op match {
              case "==" => BooleanValue(l == r)
              case "!=" => BooleanValue(l != r)
              case "+" => IntValue(l + r)
              case "-" => IntValue(l - r)
              case _ => BinaryExpression(leftEvaluate, rightEvaluate, op)
            }
          case _ => node
        }
      case Variable(name) => context.getOrElse(name, node)
      case Assignement(name, value) => Assignement(name, evaluate(value))
      case Block(nodes) => Block(nodes.map(evaluate))
      case If(condition, ifBody, elseBody) => If(evaluate(condition), evaluate(ifBody).asInstanceOf[Block], evaluate(elseBody).asInstanceOf[Block])
      case While(condition, body) => While(evaluate(condition), evaluate(body).asInstanceOf[Block])
      case _ => node
    }
    if (onePass != node) evaluate(onePass)
    else onePass
  }
}
