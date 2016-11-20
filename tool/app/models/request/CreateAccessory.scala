package models.request

import model.item.Accessory
import models.core.{AsItem, AsModel, Identifier, Name}
import play.api.libs.json.Json

case class CreateAccessory(id: Identifier, name: Name, price: Long) extends AsModel[Accessory] with AsItem {
  override def asModel: Accessory = Accessory().update(_.item := asItem)
}

object CreateAccessory {
  implicit val format = Json.format[CreateAccessory]
}
