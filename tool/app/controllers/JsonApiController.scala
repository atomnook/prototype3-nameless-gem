package controllers

import play.api.libs.json.{JsError, JsSuccess, Reads}
import play.api.mvc.{Action, Controller, Request, Result}

trait JsonApiController extends Controller {
  def json[A : Reads](f: Request[A] => Result) = Action(parse.json) { request =>
    request.body.validate[A] match {
      case JsSuccess(a, _) => f(request.map(_ => a))
      case JsError(e) => BadRequest(JsError.toJson(e))
    }
  }
}
