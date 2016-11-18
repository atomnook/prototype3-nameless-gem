package models

import domain.service.DatabaseService
import model.skill.{Skill, SkillId}
import models.SkillAggregator.Aggregator

trait SkillAggregator {
  def skillAggregator(service: DatabaseService): Aggregator = new Aggregator(service)
}

object SkillAggregator {
  class Aggregator(service: DatabaseService) {
    def all: List[Skill] = service.skills()

    def categorized[A](id: SkillId)(f: PartialFunction[Any, A]): Option[A] = {
      val s1 = service.multipleAttackSkills().read()
      val s2 = service.chainAttackSkills().read()
      s1.find(_.getSkill.getSkill.getId == id).map(f).
        orElse(s2.find(_.getSkill.getSkill.getId == id).map(f))
    }
  }
}
