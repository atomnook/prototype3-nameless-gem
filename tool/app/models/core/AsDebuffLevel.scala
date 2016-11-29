package models.core

import model.Debuff
import model.skill.DebuffLevel
import play.api.libs.json.{Json, OFormat}

case class AsDebuffLevel(debuff: Debuff, level: AsAttackLevel, duration: AsAttackLevel) {
  def asDebuffLevel: DebuffLevel = {
    DebuffLevel().update(_.debuff := debuff, _.level := level.asAttackLevel, _.duration := duration.asAttackLevel)
  }
}

object AsDebuffLevel {
  implicit val format: OFormat[AsDebuffLevel] = Json.format[AsDebuffLevel]
}
