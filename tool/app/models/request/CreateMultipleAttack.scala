package models.request

import model.{BodyPart, Element}
import model.skill.{MultipleAttackSkill, SkillType}
import models.core._
import play.api.libs.json.{Json, OFormat}

case class CreateMultipleAttack(id: Identifier,
                                name: Name,
                                prerequisites: Set[Identifier],
                                types: Set[SkillType],
                                elements: Set[Element],
                                power: AsAttackLevel,
                                tp: AsAttackLevel,
                                part: BodyPart,
                                debuff: Option[AsDebuffLevel],
                                hit: AsAttackLevel,
                                range: model.Range,
                                target: model.Target) extends AsModel[MultipleAttackSkill] with AsAttackSkill {
  def asModel: MultipleAttackSkill = {
    MultipleAttackSkill().update(_.skill := asAttackSkill, _.hit := hit.asAttackLevel, _.range := range, _.target := target)
  }
}

object CreateMultipleAttack {
  implicit val format: OFormat[CreateMultipleAttack] = Json.format[CreateMultipleAttack]
}
