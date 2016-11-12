package models.core

import model.skill.{Skill, SkillId}

trait AsSkill {
  val id: Identifier
  val name: Name
  val prerequisites: Set[Identifier]

  def asSkill: Skill = {
    Skill().update(
      _.id.id := id.id,
      _.name := name.name,
      _.prerequisites := prerequisites.map(s => SkillId(s.id)).toList)
  }
}
