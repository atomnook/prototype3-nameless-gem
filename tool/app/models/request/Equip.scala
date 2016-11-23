package models.request

import com.trueaccord.lenses.Lens
import model.Equipments
import model.item.ItemId
import models.core.{AsModel, Identifier}
import play.api.libs.json.Json

case class Equip(primary: Option[Identifier],
                 secondary: Option[Identifier],
                 head: Option[Identifier],
                 arm: Option[Identifier],
                 body: Option[Identifier],
                 foot: Option[Identifier],
                 accessory1: Option[Identifier],
                 accessory2: Option[Identifier],
                 accessory3: Option[Identifier])
  extends AsModel[Equipments] {

  override def asModel: Equipments = {
    def update(f: Lens[Equipments, Equipments] => Lens[Equipments, ItemId]): Lens[Equipments, Equipments] => Lens[Equipments, ItemId] = f

    val all = Seq(
      (primary, update(_.primary)),
      (secondary, update(_.secondary)),
      (head, update(_.head)),
      (arm, update(_.arm)),
      (body, update(_.body)),
      (foot, update(_.foot)),
      (accessory1, update(_.accessory1)),
      (accessory2, update(_.accessory2)),
      (accessory3, update(_.accessory3)))

    val flatten = all.flatMap { case (id, f) =>
      id.map((_, f))
    }

    flatten.foldLeft(Equipments()) { case (equipments, (id, f)) =>
      equipments.update(x => f(x).id := id.id)
    }
  }
}

object Equip {
  implicit val format = Json.format[Equip]
}
