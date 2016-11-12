package models.core

import model.skill.AttackLevel
import play.api.libs.json.Json

case class AsAttackLevel(base: Long, increasing: Long) {
  def asAttackLevel = AttackLevel().update(_.base := base, _.increasingPct := increasing)
}

object AsAttackLevel {
  implicit val format = Json.format[AsAttackLevel]
}
