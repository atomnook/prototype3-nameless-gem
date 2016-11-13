package browser.arbitrary

import model.Target
import model.skill.{MultipleAttackSkill, SkillId}

object ArbitraryMultipleAttackSkill {
  private[this] val attackSkill = ArbitraryAttackSkill

  private[this] val attackLevel = ArbitraryAttackLevel

  private[this] val enum = ArbitraryEnum

  def arbitrary(prerequisites: Seq[SkillId]): MultipleAttackSkill = {
    MultipleAttackSkill().update(
      _.skill := attackSkill.arbitrary(prerequisites),
      _.hit := attackLevel.arbitrary,
      _.range := enum.arbitrary(model.Range),
      _.target := enum.arbitrary(Target))
  }
}
