package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import models.{ClassFormat, Identifier}
import play.api.mvc.{Action, Controller}

class ClassController @Inject() (service: DatabaseService) extends Controller with JsonApiController {
  val list = Action {
    Ok(views.html.ClassController.list(service.classes().read().toList))
  }

  def get(id: String) = Action {
    val found = service.classes().read().find(_.getId.id == id)
    Ok(views.html.ClassController.get(id, found))
  }

  val create = json[ClassFormat] { request =>
    val cls = request.body.asModel
    val res = service.classes().create(cls)

    if (res) created() else alreadyExists(request.body.id)
  }

  def delete(id: String) = Action { request =>
    val res = service.classes().delete(model.Class().update(_.id.id := id))

    if (res) ok() else notFound(Identifier(id))
  }
}
