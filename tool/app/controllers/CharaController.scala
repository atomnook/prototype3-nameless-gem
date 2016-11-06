package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import model.Chara
import models.{CharaFormat, GainExpFormat, Identifier, ModelHelper}
import play.api.mvc.{Action, Controller}

class CharaController @Inject() (service: DatabaseService) extends Controller with JsonApiController {
  implicit val helper = new ModelHelper(service)

  val list = Action { request =>
    val characters = service.characters().read().toList
    val races = service.races().read().toList
    val classes = service.classes().read().toList
    Ok(views.html.CharaController.list(characters, races, classes))
  }

  def get(id: String) = Action { request =>
    val chara = service.characters().read().find(_.getId.id == id)
    Ok(views.html.CharaController.get(id, chara))
  }

  val create = json[CharaFormat] { request =>
    val chara = request.body.asModel
    val res = service.characters().create(chara)

    if (res) created() else alreadyExists(request.body.id)
  }

  def gainXp(id: String) = json[GainExpFormat] { request =>
    service.characters().read().find(_.getId.id == id) match {
      case Some(chara) =>
        import domain.formula.GainXp._
        val res = service.characters().update(chara.gainXp(request.body.xp.xp))

        if (res) ok() else notFound(Identifier(id))

      case None => notFound(Identifier(id))
    }
  }

  def delete(id: String) = Action { request =>
    val res = service.characters().delete(Chara().update(_.id.id := id))

    if (res) ok() else notFound(Identifier(id))
  }
}
