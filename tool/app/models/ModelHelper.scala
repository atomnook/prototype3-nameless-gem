package models

import domain.service.DatabaseService
import model.{ClassId, RaceId}
import model.skill.{Skill, SkillId}

class ModelHelper(service: DatabaseService) {
  private[this] val undefined = "\\xE2\\x9A\\xA0"

  private[this] def skills(): Map[SkillId, Skill] = {
    val all =
      service.multipleAttackSkills().read().map(_.getSkill.getSkill) ++
        service.chainAttackSkills().read().map(_.getSkill.getSkill)

    all.map(s => (s.getId, s)).toMap
  }

  implicit class SkillIdResolver(id: SkillId) {
    def name: String = skills().get(id).map(_.name).getOrElse(undefined)
  }

  implicit class RaceIdResolver(id: RaceId) {
    def name: String = service.races().read().find(_.getId == id).map(_.name).getOrElse(undefined)
  }

  implicit class ClassIdResolver(id: ClassId) {
    def name: String = service.classes().read().find(_.getId == id).map(_.name).getOrElse(undefined)
  }
}
