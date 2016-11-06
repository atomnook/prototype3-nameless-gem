package models

import model.{Chara, ClassLevel}
import play.api.libs.json.Json

case class CharaFormat(id: Identifier, name: Name, race: Identifier, classes: Seq[Identifier]) extends ModelFormat[Chara] {
  def asModel: Chara = {
    Chara().update(
      _.id.id := id.id,
      _.name := name.name,
      _.race.id := race.id,
      _.classes := classes.map(c => ClassLevel().update(_.id.id := c.id)))
  }
}

object CharaFormat {
  implicit val format = Json.format[CharaFormat]
}
