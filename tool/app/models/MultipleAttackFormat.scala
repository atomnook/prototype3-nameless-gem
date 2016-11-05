package models

import model.Element
import model.skill.{MultipleAttackSkill, SkillId, SkillType}
import play.api.libs.json.Json

case class MultipleAttackFormat(id: Identifier,
                                name: String,
                                prerequisites: Set[Identifier],
                                types: Set[SkillType],
                                elements: Set[Element],
                                power: BaseAndIncreasing,
                                tp: BaseAndIncreasing,
                                times: BaseAndIncreasing,
                                range: model.Range,
                                target: model.Target) {
  def asModel: MultipleAttackSkill = {
    MultipleAttackSkill().update(
      _.skill.skill.id.id := id.id,
      _.skill.skill.name := name,
      _.skill.skill.prerequisites := prerequisites.map(id => SkillId(id.id)).toList,
      _.skill.types := types.toSeq,
      _.skill.elements := elements.toSeq,
      _.skill.basePower := power.base,
      _.skill.increasingPower := power.increasing,
      _.skill.baseTpCost := tp.base,
      _.skill.increasingTpCost := tp.increasing,
      _.baseTimes := times.base,
      _.increasingTimes := times.increasing,
      _.range := range,
      _.target := target)
  }
}

object MultipleAttackFormat {
  implicit val format = Json.format[MultipleAttackFormat]
}
