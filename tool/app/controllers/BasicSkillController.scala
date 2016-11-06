package controllers

import domain.service.DatabaseService
import model.skill.Skill

trait BasicSkillController {
  protected[this] def allSkills(service: DatabaseService): List[Skill] = {
    service.multipleAttackSkills().read().map(_.getSkill.getSkill).toList ++
      service.chainAttackSkills().read().map(_.getSkill.getSkill).toList
  }
}
