package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import domain.service.DatabaseService.Crud
import model.Chara
import models.{CharaFormat, GainExpFormat, Identifier, ModelHelper}
import play.api.mvc.Result

class CharaController @Inject() (service: DatabaseService) extends CrudController[Chara, CharaFormat] {
  implicit val helper = new ModelHelper(service)

  override protected[this] val crud: Crud[Chara] = service.characters()

  override protected[this] def identity(a: Chara): Identifier = Identifier(a.getId.id)

  override protected[this] def identity(id: Identifier): Chara = Chara().update(_.id.id := id.id)

  override protected[this] def listPage(a: List[Chara]): Result = {
    val races = service.races().read().toList
    val classes = service.classes().read().toList
    Ok(views.html.CharaController.list(a, races, classes))
  }

  override protected[this] def getPage(id: String, a: Option[Chara]): Result = {
    Ok(views.html.CharaController.get(id, a))
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
}
