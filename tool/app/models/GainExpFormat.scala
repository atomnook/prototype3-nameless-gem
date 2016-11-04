package models

import play.api.libs.json.Json

case class GainExpFormat(xp: Xp)

object GainExpFormat {
  implicit val format = Json.format[GainExpFormat]
}
