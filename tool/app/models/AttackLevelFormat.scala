package models

import model.skill.AttackLevel
import play.api.libs.json.Json

case class AttackLevelFormat(base: Long, increasing: Long) {
  def asModel = AttackLevel().update(_.base := base, _.increasingPct := increasing)
}

object AttackLevelFormat {
  implicit val format = Json.format[AttackLevelFormat]
}
