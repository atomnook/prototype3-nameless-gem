package controllers

import domain.service.DatabaseService.Crud
import models.core.{Identifier, AsModel}
import play.api.libs.json.Reads
import play.api.mvc.{Action, Controller, Result}

abstract class CrudController[A, B <: AsModel[A]](implicit reads: Reads[B]) extends Controller with JsonApiController {
  protected[this] val crud: Crud[A]

  protected[this] def identity(a: A): Identifier

  protected[this] def identity(id: Identifier): A

  protected[this] def listPage(a: List[A]): Result

  protected[this] def getPage(id: String, a: Option[A]): Result

  val list = Action(_ => listPage(crud.read().toList))

  def get(id: String) = Action(_ => getPage(id, crud.read().find(a => identity(a).id == id)))

  val create = json[B] { request =>
    val a = request.body.asModel
    if (crud.create(a)) created() else alreadyExists(identity(a))
  }

  def delete(id: String) = Action { _ =>
    val identifier = Identifier(id)
    val res = crud.delete(identity(identifier))

    if (res) ok() else notFound(identifier)
  }

  protected[this] def update(id: String)(f: A => A) = {
    val identifier = Identifier(id)
    val found = crud.read().find(identity(_) == identifier)
    if (found.map(f).exists(crud.update)) ok() else notFound(identifier)
  }
}
