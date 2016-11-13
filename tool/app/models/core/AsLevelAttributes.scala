package models.core

import model.LevelAttributes
import play.api.libs.json.Json

case class AsLevelAttributes(base: AsAttributes, increasing: AsAttributes) {
  def asLevelAttributes: LevelAttributes = {
    LevelAttributes().update(_.base := base.asAttributes, _.increasing := increasing.asAttributes)
  }
}

object AsLevelAttributes {
  implicit val format = Json.format[AsLevelAttributes]
}
