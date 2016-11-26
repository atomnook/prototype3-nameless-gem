package models.request

import model.skill.{MasterySkill, SkillType}
import models.core._
import play.api.libs.json.{Json, OFormat}

case class CreateMasterySkill(id: Identifier,
                              name: Name,
                              prerequisites: Set[Identifier],
                              mastery: SkillType) extends AsModel[MasterySkill] with AsSkill {
  override def asModel: MasterySkill = {
    MasterySkill().update(_.skill := asSkill, _.mastery := mastery)
  }
}

object CreateMasterySkill {
  implicit val format: OFormat[CreateMasterySkill] = Json.format[CreateMasterySkill]
}
