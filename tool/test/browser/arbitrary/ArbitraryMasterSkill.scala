package browser.arbitrary

import model.skill.{MasterySkill, SkillId, SkillType}

object ArbitraryMasterSkill {
  private[this] val skill = ArbitrarySkill

  private[this] val enum = ArbitraryEnum

  def arbitrary(prerequisites: Seq[SkillId]): MasterySkill = {
    MasterySkill().update(_.skill := skill.arbitrary(prerequisites), _.mastery := enum.arbitrary(SkillType))
  }
}
