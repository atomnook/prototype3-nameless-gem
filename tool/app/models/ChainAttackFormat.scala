package models

import model.Element
import model.skill.{ChainAttackSkill, SkillType}
import play.api.libs.json.Json

case class ChainAttackFormat(id: Identifier,
                             name: Name,
                             prerequisites: Set[Identifier],
                             types: Set[SkillType],
                             elements: Set[Element],
                             power: AttackLevelFormat,
                             tp: AttackLevelFormat,
                             follow: Set[Element],
                             chain: AttackLevelFormat,
                             range: model.Range) extends ModelFormat[ChainAttackSkill] with AttackSkillFormat {
  override def asModel: ChainAttackSkill = {
    ChainAttackSkill().update(_.skill := asAttackSkill, _.follow := follow.toList, _.chain := chain.asModel, _.range := range)
  }
}

object ChainAttackFormat {
  implicit val format = Json.format[ChainAttackFormat]
}
