package controllers

import javax.inject.Inject

import controllers.CharaController.{AttributesViewArgs, EquipmentsViewArgs, GetViewArgs}
import domain.service.ClassService.ClassNotFoundException
import domain.service.RaceService.RaceNotFoundException
import domain.service.{CharaService, Crud, DatabaseService}
import model._
import model.item._
import models.ModelHelper
import models.core.Identifier
import models.request.{CreateChara, Equip, GainExp}
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc.Result

import scala.concurrent.Future

class CharaController @Inject() (service: DatabaseService) extends CrudController[Chara, CreateChara] {
  implicit val helper = new ModelHelper(service)

  private[this] val charaService = new CharaService(service)

  override protected[this] val crud: Crud[Chara] = service.characters()

  override protected[this] def identity(a: Chara): Identifier = Identifier(a.getId.id)

  override protected[this] def identity(id: Identifier): Chara = Chara().update(_.id.id := id.id)

  override protected[this] def listPage(a: List[Chara]): Result = {
    val races = service.races().read()
    val classes = service.classes().read()
    Ok(views.html.CharaController.list(a, races, classes))
  }

  override protected[this] def getPage(id: String, a: Option[Chara]): Future[Result] = {
    val races = service.races().read()
    val classes = service.classes().read()
    val weapons = service.weapons().read()
    val armors = service.armors().read()
    val accessories = service.accessories().read()

    for {
      attributes <- a.map(charaService.attributes).
        map(_.map(attributes => AttributesViewArgs(attributes))).
        getOrElse(Future.successful(AttributesViewArgs(""))).
        recover {
          case RaceNotFoundException(r) => AttributesViewArgs(s"race ${r.id} not found")
          case ClassNotFoundException(c) => AttributesViewArgs(s"class ${c.id} not found")
        }
    } yield {
      val equipments = EquipmentsViewArgs(weapons, armors, accessories)
      val args = GetViewArgs(id, a, races, classes, attributes, equipments)
      Ok(views.html.CharaController.get(args))
    }
  }

  def gainXp(id: String) = json[GainExp] { request =>
    service.characters().read().find(_.getId.id == id) match {
      case Some(chara) =>
        import domain.formula.GainXp._
        val res = service.characters().update(chara.gainXp(request.body.xp.xp))

        if (res) ok() else notFound(Identifier(id))

      case None => notFound(Identifier(id))
    }
  }

  def equip(id: String) = json[Equip] { request =>
    service.characters().read().find(_.getId.id == id) match {
      case Some(chara) =>
        val res = service.characters().update(chara.update(_.equipments := request.body.asModel))

        if (res) ok() else notFound(Identifier(id))

      case None => notFound(Identifier(id))
    }
  }
}

object CharaController {
  case class AttributesViewArgs(attributes: Option[Attributes], alert: String)

  object AttributesViewArgs {
    def apply(attributes: Attributes): AttributesViewArgs = AttributesViewArgs(Some(attributes), "")

    def apply(alert: String): AttributesViewArgs = AttributesViewArgs(None, alert)
  }

  case class EquipmentsViewArgs(weapons: List[Weapon],
                                armors: List[Armor],
                                accessories: List[Accessory]) {
    def typedArmors(types: Seq[ArmorType]): List[Item] = armors.filter(a => types.contains(a.`type`)).map(_.getItem)
  }

  case class GetViewArgs(id: String,
                         chara: Option[Chara],
                         races: List[Race],
                         classes: List[Class],
                         attributes: AttributesViewArgs,
                         equipments: EquipmentsViewArgs)
}
