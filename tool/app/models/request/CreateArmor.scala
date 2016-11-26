package models.request

import model.item.{Armor, ArmorType}
import models.core._
import play.api.libs.json.{Json, OFormat}

case class CreateArmor(id: Identifier, name: Name, price: Long, `def`: Long, mdf: Long, `type`: ArmorType) extends AsModel[Armor] with AsItem {
  override def asModel: Armor = Armor().update(_.item := asItem, _.`def` := `def`, _.mdf := mdf, _.`type` := `type`)
}

object CreateArmor {
  implicit val format: OFormat[CreateArmor] = Json.format[CreateArmor]
}
