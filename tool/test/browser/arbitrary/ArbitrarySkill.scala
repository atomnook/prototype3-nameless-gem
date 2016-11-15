package browser.arbitrary

import model.skill.{Skill, SkillId}

object ArbitrarySkill {
  private[this] val id = ArbitraryId

  private[this] val name = ArbitraryName

  private[this] val enum = ArbitraryEnum

  def arbitrary(prerequisites: Seq[SkillId]): Skill = {
    Skill().update(_.id.id := id.arbitrary, _.name := name.arbitrary, _.prerequisites := prerequisites)
  }
}
