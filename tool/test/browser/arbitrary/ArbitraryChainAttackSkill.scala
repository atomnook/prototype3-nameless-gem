package browser.arbitrary

import model.Element
import model.skill.{ChainAttackSkill, SkillId}

object ArbitraryChainAttackSkill {
  private[this] val attackSkill = ArbitraryAttackSkill

  private[this] val attackLevel = ArbitraryAttackLevel

  private[this] val enums = ArbitraryNonEmptyEnums

  private[this] val enum = ArbitraryEnum

  def arbitrary(prerequisites: Seq[SkillId]): ChainAttackSkill = {
    ChainAttackSkill().update(
      _.skill := attackSkill.arbitrary(prerequisites),
      _.follow := enums.arbitrary(Element),
      _.chain := attackLevel.arbitrary,
      _.range := enum.arbitrary(model.Range))
  }
}
