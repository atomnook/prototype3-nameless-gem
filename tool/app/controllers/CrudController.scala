package controllers

import domain.service.Crud
import models.core.{AsModel, Identifier}
import play.api.libs.json.Reads
import play.api.mvc.{Action, Controller, Result}

import scala.concurrent.Future

abstract class CrudController[A, B <: AsModel[A]](implicit reads: Reads[B]) extends Controller with JsonApiController {
  protected[this] val crud: Crud[A]

  protected[this] def identity(a: A): Identifier

  protected[this] def identity(id: Identifier): A

  protected[this] def listPage(a: List[A]): Result

  protected[this] def getPage(id: String, a: Option[A]): Future[Result]

  val list = Action(_ => listPage(crud.read()))

  def get(id: String) = Action.async(_ => getPage(id, crud.read().find(a => identity(a).id == id)))

  val create = json[B] { request =>
    val a = request.body.asModel

    if (crud.create(a)) created() else alreadyExists(identity(a))
  }

  def delete(id: String) = Action { _ =>
    val identifier = Identifier(id)
    crud.delete(identity(identifier))
    ok()
  }

  val update = json[B] { request =>
    val a = request.body.asModel

    if (crud.update(a)) ok() else notFound(identity(a))
  }
}
