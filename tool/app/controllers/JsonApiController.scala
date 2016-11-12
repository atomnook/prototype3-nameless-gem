package controllers

import models.core.Identifier
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import play.api.mvc.{Action, Controller, Request, Result}

trait JsonApiController extends Controller {
  protected[this] def json[A : Reads](f: Request[A] => Result) = Action(parse.json) { request =>
    request.body.validate[A] match {
      case JsSuccess(a, _) => f(request.map(_ => a))
      case JsError(e) => BadRequest(JsError.toJson(e))
    }
  }

  protected[this] def created() = Created(Json.obj())

  protected[this] def ok() = Ok(Json.obj())

  protected[this] def alreadyExists(id: Identifier) = Conflict(Json.obj("msg" -> s"${id.id} already exists"))

  protected[this] def notFound(id: Identifier) = NotFound(Json.obj("msg" -> s"${id.id} not found"))
}
