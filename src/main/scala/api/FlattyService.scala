package api
import service.flattylang.ast.AbstractNode
import service.flattylang.simplifications.AllSimplifications

/**
  * Created by kate on 
  * 29.04.16.
  */
trait FlattyService {

  def simplifyAst(ast: AbstractNode): AbstractNode = {
    AllSimplifications.run(ast)
  }
}
