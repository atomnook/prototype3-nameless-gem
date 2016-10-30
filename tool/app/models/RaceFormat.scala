package models

import model.Race
import play.api.libs.json.Json

case class RaceFormat(id: Identifier, name: String) {
  def asModel: Race = Race().update(_.id.id := id.id, _.name := name)
}

object RaceFormat {
  implicit val format = Json.format[RaceFormat]
}
