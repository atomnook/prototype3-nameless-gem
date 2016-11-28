package models.core

import model.{BodyPart, Element}
import model.skill.{AttackSkill, SkillType}

trait AsAttackSkill extends AsSkill {
  val types: Set[SkillType]
  val elements: Set[Element]
  val power: AsAttackLevel
  val tp: AsAttackLevel
  val part: BodyPart

  def asAttackSkill: AttackSkill = {
    AttackSkill().update(
      _.skill := asSkill,
      _.types := types.toList.sortBy(_.value),
      _.elements := elements.toList.sortBy(_.value),
      _.power := power.asAttackLevel,
      _.tpCost := tp.asAttackLevel,
      _.usedPart := part)
  }
}
