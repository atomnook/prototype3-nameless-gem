package models.request

import model.item.Accessory
import models.core.{AsItem, AsModel, Identifier, Name}
import play.api.libs.json.{Json, OFormat}

case class CreateAccessory(id: Identifier, name: Name, price: Long) extends AsModel[Accessory] with AsItem {
  override def asModel: Accessory = Accessory().update(_.item := asItem)
}

object CreateAccessory {
  implicit val format: OFormat[CreateAccessory] = Json.format[CreateAccessory]
}
