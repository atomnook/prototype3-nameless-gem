package models

import model.Element
import model.skill.{MultipleAttackSkill, SkillId, SkillType}
import play.api.libs.json.Json

case class MultipleAttackFormat(id: Identifier,
                                name: String,
                                prerequisites: Set[Identifier],
                                types: Set[SkillType],
                                elements: Set[Element],
                                power: AttackLevelFormat,
                                tp: AttackLevelFormat,
                                hit: AttackLevelFormat,
                                range: model.Range,
                                target: model.Target) {
  def asModel: MultipleAttackSkill = {
    MultipleAttackSkill().update(
      _.skill.skill.id.id := id.id,
      _.skill.skill.name := name,
      _.skill.skill.prerequisites := prerequisites.map(id => SkillId(id.id)).toList,
      _.skill.types := types.toSeq,
      _.skill.elements := elements.toSeq,
      _.skill.power := power.asModel,
      _.skill.tpCost := tp.asModel,
      _.hit := hit.asModel,
      _.range := range,
      _.target := target)
  }
}

object MultipleAttackFormat {
  implicit val format = Json.format[MultipleAttackFormat]
}
