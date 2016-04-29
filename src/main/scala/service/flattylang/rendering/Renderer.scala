package service.flattylang.rendering

import service.flattylang.ast._
import service.flattylang.ast.arifoperations.BinaryExpression
import service.flattylang.ast.variable.{BooleanValue, IntValue, StringValue, Variable}

/**
  * Created by kate on 
  * 22.04.16.
  */
object Renderer {
  def wrapInHtmlIndex(text: String, after: String) = {
    def style: String =
      "<style>table {width: 30%;}td {padding: 5px;vertical-align: top;}</style>"

    "<html><head></head>" + style + "<body><h1>Welcome to RESTful Flatty server</h1><table><tr><td>" + text + "</td><td>" + after + "</td></tr></table></body></html> "
  }

  def render(node: AbstractNode): String = {
    node match {
      case Block(block) => block.map(statement => s"${renderToHtmlHelper(statement)}").mkString(newLine)
      case _ => s"${renderToHtmlHelper(node)}"
    }
  }

  private def renderToHtmlHelper(node: AbstractNode, indent: Int = 0): String = {
    def withSpaces(s: Any): String = s"${spaces(1)}$s${spaces(1)}"

    node match {
      case If(condition, body, elseBody) =>
        val ifBranch = s"if$openParenth${renderToHtmlHelper(condition, indent)}$closeParenth${renderToHtmlHelper(body, indent)}"
        if (elseBody != null) {
          ifBranch + s"else${renderToHtmlHelper(elseBody, indent)}"
        }
        else ifBranch
      case Block(data: Seq[AbstractNode]) =>
        val exprs = data.map(node => s"${spaces(indent + 1)}${renderToHtmlHelper(node, indent + 1)}$newLine").mkString
        s"$openCurlyBracket$newLine$exprs${spaces(indent)}$closeCurlyBracket"
      case While(condition, body) =>
        s"while$openParenth${renderToHtmlHelper(condition, indent)}$closeParenth${renderToHtmlHelper(body, indent)}"
      case Assignement(name, value) =>
        s"val${withSpaces(name)} = ${renderToHtmlHelper(value, indent)}"
      case StringValue(value) => withSpaces(value)
      case IntValue(value) => withSpaces(value)
      case BooleanValue(value) => withSpaces(value)
      case BinaryExpression(left, right, op) => s"${renderToHtmlHelper(left, indent)}${withSpaces(op)}${renderToHtmlHelper(right, indent)}"
      case Variable(name) => s"$name${spaces(1)}"
      case _ =>
        node.toString + "not implemented"

    }
  }

  def newLine: String = "<br>"

  def spaces(n: Int) = "&nbsp" * n

  def openParenth = " ( "

  def closeParenth = " ) "

  def openCurlyBracket = " { "

  def closeCurlyBracket = " } "
}
