package service.flattylang.json

import org.scalatest.FunSuite
import service.flattylang.ast.variable.{BooleanValue, IntValue, StringValue, Variable}
import service.flattylang.ast._
import service.flattylang.json.JsonProtocol._
import spray.json._

/**
  * Created by kate on 
  * 27.04.16.
  */
class JsonProtocolWriting$Test extends FunSuite {

  test("testBoolFormat") {
    val booleanJson = "{\"value\":true}"
    assert(boolAst.toJson.toString == booleanJson)
  }

  test("testStringFormat") {
    assert(stringAst.toJson.toString == "{\"value\":\"string\"}")
  }

  test("testNumberFormat") {
    val intJson = "{\"value\":23}"
    assert(intAst.toJson.toString() == intJson)
  }

  test("testIfFormat") {
    val ifAst = If(boolAst, Block(Seq(intAst)), Block(Seq(intAst)))
    val ifJson = "{\"condition\":{\"boolean\":{\"value\":true}},\"ifBody\":{\"block\":[{\"int\":{\"value\":23}}]},\"elseBody\":{\"block\":[{\"int\":{\"value\":23}}]}}"
    assert(ifAst.toJson.toString == ifJson)
  }

  test("testVariableUseFormat") {
    val variableAst = Variable("name")
    val variableJson = "{\"value\":\"name\"}"
    assert(variableAst.toJson.toString == variableJson)
  }

  test("testValueDefFormat") {
    val valDef = Assignement("name", intAst)
    val valJson = "{\"name\":\"name\",\"value\":{\"int\":{\"value\":23}}}"
    assert(valDef.toJson.toString == valJson)
  }

  test("testBodyFormat") {
    val blockAst = Block(Seq(intAst, boolAst, stringAst))
    val blockJson = "{\"block\":[{\"int\":{\"value\":23}},{\"boolean\":{\"value\":true}},{\"string\":{\"value\":\"string\"}}]}"
    assert(blockAst.toJson.toString == blockJson)
  }

  test("testEmptyNode") {
    val emptyNodeAst = EmptyNode()
    val emptyNodeJson = "{}"
    assert(emptyNodeAst.toJson.toString == emptyNodeJson)
  }


  private def intAst: IntValue = IntValue(23)

  private def boolAst: BooleanValue = BooleanValue(true)

  private def stringAst: StringValue = StringValue("string")
}
