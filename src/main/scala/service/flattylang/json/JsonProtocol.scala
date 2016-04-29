package service.flattylang.json

import service.flattylang._
import service.flattylang.ast._
import service.flattylang.ast.arifoperations._
import service.flattylang.ast.variable.{BooleanValue, IntValue, StringValue, Variable}
import spray.json._
import service.flattylang.JsonKeyWords._

/**
  * Created by kate on 
  * 19.04.16.
  */
object JsonProtocol extends DefaultJsonProtocol {

  implicit object AstJsonFormat extends RootJsonFormat[AbstractNode] {
    def write(c: AbstractNode) = {
      c match {
        case binaryExpression: BinaryExpression => JsObject((KBinary.toString, binaryExpression.toJson))
        case ifNode: If => JsObject((KIf.toString, ifNode.toJson))
        case valueDef: Assignement => JsObject((KAssignment.toString, valueDef.toJson))
        case variable: Variable => JsObject((KVariable.toString, variable.toJson))
        case whileDef: While => JsObject((KWhile.toString, whileDef.toJson))
        case block: Block => JsObject((KBlock.toString, block.toJson))
        case empty: EmptyNode => JsObject((KEmpty.toString, empty.toJson))
        case intValue: IntValue => JsObject((KInt.toString, intValue.toJson))
        case booleanValue: BooleanValue => JsObject((KBoolean.toString, booleanValue.toJson))
        case stringValue: StringValue => JsObject((KString.toString, stringValue.toJson))
        case _ => serializationError(s"can't serialize node ${c.toString}")
      }
    }

    // TODO: implement deserialization for AbstractNode
    // TODO: Test for deserialize
    def read(json: JsValue): AbstractNode = {
      def errorMsg(value: JsValue): String = s"can't parse value $value"

      def convertByKey(key: JsonKeyWords, value: JsValue): AbstractNode = {
        key match {
          case KIf => value.convertTo[If]
          case KWhile => value.convertTo[While]
          case KAssignment => value.convertTo[Assignement]
          case KBoolean => value.convertTo[BooleanValue]
          case KInt => value.convertTo[IntValue]
          case KString => value.convertTo[StringValue]
          case KBlock => value.convertTo[Block]
          case KBinary => value.convertTo[BinaryExpression]
          case KVariable => value.convertTo[Variable]
          case KEmpty => value.convertTo[EmptyNode]
          case _ => deserializationError(errorMsg(value))
        }
      }

      json match {
        case JsObject(fields) if fields.size == 1 =>
          val key = fields.keys.head
          val value = fields.values.head
          convertByKey(key, value.asJsObject(key))
        case _ => deserializationError(errorMsg(json))
      }
    }
  }

  implicit val numberFormat = jsonFormat1(IntValue)
  implicit val boolFormat = jsonFormat1(BooleanValue)
  implicit val stringFormat = jsonFormat1(StringValue)
  implicit val whileDefFormat: JsonFormat[While] = lazyFormat(jsonFormat2(While))
  implicit val bodyFormat = jsonFormat1(Block)
  implicit val ifFormat: JsonFormat[If] = lazyFormat(jsonFormat3(If))
  implicit val valueDefFormat: JsonFormat[Assignement] = lazyFormat(jsonFormat2(Assignement))
  implicit val binaryFormat: JsonFormat[BinaryExpression] = lazyFormat(jsonFormat3(BinaryExpression))
  implicit val variableUseFormat = jsonFormat1(Variable)
  // TODO: add json format for EmptyNode + test
  implicit val emptyNode: JsonFormat[EmptyNode] = jsonFormat0(EmptyNode)
}


