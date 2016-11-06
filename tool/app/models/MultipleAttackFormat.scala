package models

import model.Element
import model.skill.{MultipleAttackSkill, SkillType}
import play.api.libs.json.Json

case class MultipleAttackFormat(id: Identifier,
                                name: Name,
                                prerequisites: Set[Identifier],
                                types: Set[SkillType],
                                elements: Set[Element],
                                power: AttackLevelFormat,
                                tp: AttackLevelFormat,
                                hit: AttackLevelFormat,
                                range: model.Range,
                                target: model.Target) extends ModelFormat[MultipleAttackSkill] with AttackSkillFormat {
  def asModel: MultipleAttackSkill = {
    MultipleAttackSkill().update(_.skill := asAttackSkill, _.hit := hit.asModel, _.range := range, _.target := target)
  }
}

object MultipleAttackFormat {
  implicit val format = Json.format[MultipleAttackFormat]
}
