package models

import model.Element
import model.skill.{AttackSkill, SkillType}

trait AttackSkillFormat extends SkillFormat {
  val types: Set[SkillType]
  val elements: Set[Element]
  val power: AttackLevelFormat
  val tp: AttackLevelFormat

  def asAttackSkill: AttackSkill = {
    AttackSkill().update(
      _.skill := asSkill,
      _.types := types.toSeq,
      _.elements := elements.toSeq,
      _.power := power.asModel,
      _.tpCost := tp.asModel)
  }
}
