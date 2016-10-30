package models

import play.api.libs.json.Json

case class ClassFormat(id: Identifier, name: String) {
  def asModel: model.Class = model.Class().update(_.id.id := id.id, _.name := name)
}

object ClassFormat {
  implicit val format = Json.format[ClassFormat]
}

