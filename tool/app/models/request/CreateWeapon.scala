package models.request

import model.item.{Weapon, WeaponType}
import models.core._
import play.api.libs.json.Json

case class CreateWeapon(id: Identifier, name: Name, price: Long, atk: Long, mat: Long, `type`: WeaponType) extends AsModel[Weapon] with AsItem {
  override def asModel: Weapon = Weapon().update(_.item := asItem, _.atk := atk, _.mat := mat, _.`type` := `type`)
}

object CreateWeapon {
  implicit val format = Json.format[CreateWeapon]
}
