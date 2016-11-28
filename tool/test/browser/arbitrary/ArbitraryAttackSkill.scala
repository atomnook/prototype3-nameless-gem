package browser.arbitrary

import model.{BodyPart, Element}
import model.skill.{AttackSkill, SkillId, SkillType}

object ArbitraryAttackSkill {
  private[this] val skill = ArbitrarySkill

  private[this] val enums = ArbitraryNonEmptyEnums

  private[this] val attackLevel = ArbitraryAttackLevel

  private[this] val enum = ArbitraryEnum

  def arbitrary(prerequisites: Seq[SkillId]): AttackSkill = {
    AttackSkill().update(
      _.skill := skill.arbitrary(prerequisites),
      _.types := enums.arbitrary(SkillType),
      _.elements := enums.arbitrary(Element),
      _.power := attackLevel.arbitrary,
      _.tpCost := attackLevel.arbitrary,
      _.usedPart := enum.arbitrary(BodyPart))
  }
}
