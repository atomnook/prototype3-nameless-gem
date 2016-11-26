package browser.arbitrary

import model.Attribute
import model.skill.{AttributeBoostSkill, SkillId}

object ArbitraryAttributeBoostSkill {
  private[this] val skill = ArbitrarySkill

  private[this] val enum = ArbitraryEnum

  def arbitrary(prerequisites: Seq[SkillId]): AttributeBoostSkill = {
    AttributeBoostSkill().update(
      _.skill := skill.arbitrary(prerequisites),
      _.attribute := enum.arbitrary(Attribute))
  }
}
