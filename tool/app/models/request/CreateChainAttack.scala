package models.request

import model.Element
import model.skill.{ChainAttackSkill, SkillType}
import models.core._
import play.api.libs.json.{Json, OFormat}

case class CreateChainAttack(id: Identifier,
                             name: Name,
                             prerequisites: Set[Identifier],
                             types: Set[SkillType],
                             elements: Set[Element],
                             power: AsAttackLevel,
                             tp: AsAttackLevel,
                             follow: Set[Element],
                             chain: AsAttackLevel,
                             range: model.Range) extends AsModel[ChainAttackSkill] with AsAttackSkill {

  override def asModel: ChainAttackSkill = {
    ChainAttackSkill().update(
      _.skill := asAttackSkill,
      _.follow := follow.toList.sortBy(_.value),
      _.chain := chain.asAttackLevel,
      _.range := range)
  }
}

object CreateChainAttack {
  implicit val format: OFormat[CreateChainAttack] = Json.format[CreateChainAttack]
}
