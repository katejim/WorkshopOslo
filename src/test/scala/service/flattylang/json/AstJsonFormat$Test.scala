package service.flattylang.json

import org.scalatest.FunSuite
import service.flattylang.ast.{AbstractNode, Block, If}
import service.flattylang.ast.variable.{BooleanValue, IntValue, StringValue}
import service.flattylang.json.JsonProtocol._
import spray.json._
/**
  * Created by kate on 
  * 29.04.16.
  */
class AstJsonFormat$Test extends FunSuite {

  test("testRead") {
    val ast =
      If(BooleanValue(true), Block(Seq(IntValue(1))), Block(Seq(StringValue("we")))).asInstanceOf[AbstractNode]

    assert(ast == ast.toJson.toString.parseJson.convertTo[AbstractNode])
  }

}
