package models.request

import models.core.Xp
import play.api.libs.json.Json

case class GainExp(xp: Xp)

object GainExp {
  implicit val format = Json.format[GainExp]
}
