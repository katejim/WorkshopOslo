package api

/**
  * Created by kate on 
  * 16.04.16.
  */

import akka.actor.Actor
import service.flattylang.ast.AbstractNode
import service.flattylang.json.JsonProtocol._
import service.flattylang.rendering.Renderer
import spray.http._
import spray.httpx.SprayJsonSupport.sprayJsonUnmarshaller
import spray.routing._


trait FlattyApi extends HttpService with FlattyService {

  def successfulPost(before: String, after: String) = respondWithMediaType(MediaTypes.`text/html`) {
    complete(Renderer.wrapInHtmlIndex(before, after))
  }

  val route: Route = {
    path("Flatty") {
      post {
        entity(as[AbstractNode]) { ast =>
          // TODO: render input ast and updated ast
          successfulPost(Renderer.render(ast), Renderer.render(simplifyAst(ast)))
        }
      }
    }
  }
}

class FlattyApiServiceActor extends Actor with FlattyApi {

  def actorRefFactory = context

  def receive = runRoute(route)
}

