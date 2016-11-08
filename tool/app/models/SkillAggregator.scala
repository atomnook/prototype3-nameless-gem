package models

import domain.service.DatabaseService
import model.skill.{Skill, SkillId}
import models.SkillAggregator.Aggregator

trait SkillAggregator {
  def skillAggregator(service: DatabaseService): Aggregator = new Aggregator(service)
}

object SkillAggregator {
  class Aggregator(service: DatabaseService) {
    private[this] def aggregate = {
      (service.multipleAttackSkills().read(), service.chainAttackSkills().read())
    }

    def all: List[Skill] = {
      val (s1, s2) = aggregate
      s1.map(_.getSkill.getSkill).toList ++ s2.map(_.getSkill.getSkill).toList
    }

    def categorized[A](id: SkillId)(f: PartialFunction[Any, A]): Option[A] = {
      val (s1, s2) = aggregate
      s1.find(_.getSkill.getSkill.getId == id).map(f).
        orElse(s2.find(_.getSkill.getSkill.getId == id).map(f))
    }
  }
}
