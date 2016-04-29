package service.flattylang

/**
  * Created by kate on
  * 22.04.16.
  */
sealed abstract class JsonKeyWords(val key: String) {
  override def toString: String = key
}

object JsonKeyWords {
  implicit def toKeyWord(string: String): JsonKeyWords = JsonKeyWords(string)

  def apply(key: String): JsonKeyWords = {
    key match {
      case "if" => KIf
      case "while" => KWhile
      case "val" => KAssignment
      case "boolean" => KBoolean
      case "int" => KInt
      case "string" => KString
      case "binary" => KBinary
      case "block" => KBlock
      case "valCall" => KVariable
      case "empty" => KEmpty
    }
  }
}

case object KIf extends JsonKeyWords("if")

case object KWhile extends JsonKeyWords("while")

case object KAssignment extends JsonKeyWords("valDef")

case object KBoolean extends JsonKeyWords("boolean")

case object KInt extends JsonKeyWords("int")

case object KString extends JsonKeyWords("string")

case object KBlock extends JsonKeyWords("block")

case object KBinary extends JsonKeyWords("binary")

case object KVariable extends JsonKeyWords("valCall")

case object KEmpty extends JsonKeyWords("empty")