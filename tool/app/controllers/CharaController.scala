package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import model.Chara
import models.{CharaFormat, GainExpFormat}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

class CharaController @Inject() (service: DatabaseService) extends Controller with JsonApiController {
  val list = Action { request =>
    val characters = service.characters().read().toList
    val races = service.races().read().map(r => (r.getId, r)).toMap
    val classes = service.classes().read().map(c => (c.getId, c)).toMap
    Ok(views.html.CharaController.list(characters, races, classes))
  }

  def get(id: String) = Action { request =>
    val chara = service.characters().read().find(_.getId.id == id)
    val races = service.races().read().map(r => (r.getId, r)).toMap
    val classes = service.classes().read().map(c => (c.getId, c)).toMap
    Ok(views.html.CharaController.get(id, chara, races, classes))
  }

  val create = json[CharaFormat] { request =>
    val chara = request.body.asModel
    val res = service.characters().create(chara)
    if (res) {
      Ok(Json.obj())
    } else {
      Conflict(Json.obj("msg" -> s"id:${chara.getId.id} already exists"))
    }
  }

  def gainXp(id: String) = json[GainExpFormat] { request =>
    service.characters().read().find(_.getId.id == id) match {
      case Some(chara) =>
        import domain.formula.GainXp._
        val res = service.characters().update(chara.gainXp(request.body.xp.xp))
        if (res) {
          Ok(Json.obj())
        } else {
          NotFound(Json.obj("msg" -> s"id:$id not found"))
        }

      case None => NotFound(Json.obj("msg" -> s"id:$id not found"))
    }
  }

  def delete(id: String) = Action { request =>
    val res = service.characters().delete(Chara().update(_.id.id := id))
    if (res) {
      Ok(Json.obj())
    } else {
      NotFound(Json.obj("msg" -> s"id:$id not found"))
    }
  }
}
