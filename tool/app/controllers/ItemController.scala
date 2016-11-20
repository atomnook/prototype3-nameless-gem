package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import model.item.{Accessory, Armor, ItemId, Weapon}
import models.ModelHelper
import play.api.mvc.{Action, Controller, Result}

class ItemController @Inject() (service: DatabaseService) extends Controller {
  private[this] implicit val helper = new ModelHelper(service)

  val list = Action(_ => Ok(views.html.ItemController.list(service.items())))

  def redirect(id: String) = Action { request =>
    val weapons = service.weapons().read()
    val armors = service.armors().read()
    val accessories = service.accessories().read()
    val iid = ItemId(id)

    val f: PartialFunction[Any, Result] = {
      case _: Weapon => Redirect(routes.WeaponController.get(id))
      case _: Armor => Redirect(routes.ArmorController.get(id))
      case _: Accessory => Redirect(routes.AccessoryController.get(id))
    }

    weapons.find(_.getItem.getId == iid).flatMap(f.lift).
      orElse(armors.find(_.getItem.getId == iid).flatMap(f.lift)).
      orElse(accessories.find(_.getItem.getId == iid).flatMap(f.lift)).
      getOrElse(throw new RuntimeException(s"$id not found"))
  }
}
