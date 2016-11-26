package models.core

import model.skill.AttackLevel
import play.api.libs.json.{Json, OFormat}

case class AsAttackLevel(base: Long, increasing: Long) {
  def asAttackLevel: AttackLevel = AttackLevel().update(_.base := base, _.increasingPct := increasing)
}

object AsAttackLevel {
  implicit val format: OFormat[AsAttackLevel] = Json.format[AsAttackLevel]
}
