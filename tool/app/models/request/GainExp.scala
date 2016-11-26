package models.request

import models.core.Xp
import play.api.libs.json.{Json, OFormat}

case class GainExp(xp: Xp)

object GainExp {
  implicit val format: OFormat[GainExp] = Json.format[GainExp]
}
