package models.core

import model.Attributes
import play.api.libs.json.Json

case class AsAttributes(hp: Long, tp: Long, str: Long, vit: Long, int: Long, wis: Long, agi: Long, luc: Long) {
  def asAttributes: Attributes = {
    Attributes().update(
      _.hp := hp,
      _.tp := tp,
      _.str := str,
      _.vit := vit,
      _.int := int,
      _.wis := wis,
      _.agi := agi,
      _.luc := luc)
  }
}

object AsAttributes {
  implicit val format = Json.format[AsAttributes]
}
