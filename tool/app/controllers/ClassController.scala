package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import models.ClassFormat
import play.api.libs.json.Json
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
    if (res) {
      Ok(Json.obj())
    } else {
      Conflict(Json.obj("msg" -> s"id:${cls.getId.id} already exists"))
    }
  }

  val update = json[ClassFormat] { request =>
    val cls = request.body.asModel
    val res = service.classes().update(cls)
    if (res) {
      Ok(Json.obj())
    } else {
      NotFound(Json.obj("mes" -> s"id:${cls.getId.id} not found"))
    }
  }

  def delete(id: String) = Action { request =>
    val res = service.classes().delete(model.Class().update(_.id.id := id))
    if (res) {
      Ok(Json.obj())
    } else {
      NotFound(Json.obj("mes" -> s"id:$id not found"))
    }
  }
}
