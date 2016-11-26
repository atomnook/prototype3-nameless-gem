package models.request

import model.{Chara, ClassLevel}
import models.core.{AsModel, Identifier, Name}
import play.api.libs.json.{Json, OFormat}

case class CreateChara(id: Identifier, name: Name, race: Identifier, classes: Seq[Identifier]) extends AsModel[Chara] {
  def asModel: Chara = {
    Chara().update(
      _.id.id := id.id,
      _.name := name.name,
      _.race.id := race.id,
      _.classes := classes.map(c => ClassLevel().update(_.id.id := c.id)))
  }
}

object CreateChara {
  implicit val format: OFormat[CreateChara] = Json.format[CreateChara]
}
