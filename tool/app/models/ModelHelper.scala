package models

import domain.service.DatabaseService
import model.skill.{Skill, SkillId}

class ModelHelper(service: DatabaseService) {
  private[this] def skills(): Map[SkillId, Skill] = {
    val all =
      service.multipleAttackSkills().read().map(_.getSkill.getSkill) ++
        service.chainAttackSkills().read().map(_.getSkill.getSkill)

    all.map(s => (s.getId, s)).toMap
  }

  implicit class SkillIdResolver(id: SkillId) {
    def name: String = skills().get(id).map(_.name).getOrElse("undefined")
  }
}
