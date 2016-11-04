package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import model.Race
import models.RaceFormat
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

class RaceController @Inject() (service: DatabaseService) extends Controller with JsonApiController {
  val list = Action {
    Ok(views.html.RaceController.list(service.races().read().toList))
  }

  def get(id: String) = Action {
    val found = service.races().read().find(_.getId.id == id)
    Ok(views.html.RaceController.get(id, found))
  }

  val create = json[RaceFormat] { request =>
    val race = request.body.asModel
    val res = service.races().create(race)
    if (res) {
      Ok(Json.obj())
    } else {
      Conflict(Json.obj("msg" -> s"id:${race.getId.id} already exists"))
    }
  }

  def delete(id: String) = Action { request =>
    val res = service.races().delete(Race().update(_.id.id := id))
    if (res) {
      Ok(Json.obj())
    } else {
      NotFound(Json.obj("mes" -> s"id:$id not found"))
    }
  }
}
