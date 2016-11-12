package models

import domain.service.DatabaseService
import model.skill.{ChainAttackSkill, MultipleAttackSkill, Skill, SkillId}
import model.{ClassId, RaceId}

class ModelHelper(service: DatabaseService) extends SkillAggregator {
  private[this] val skills = skillAggregator(service)

  private[this] val undefined = "⚠"

  implicit class SkillIdResolver(id: SkillId) {
    private[this] def find: Option[Skill] = skills.all.find(_.getId == id)

    def name: String = find.map(_.name).getOrElse(undefined)

    def prerequisites: Seq[SkillId] = find.map(_.prerequisites).getOrElse(Seq.empty)

    def category: String = {
      skills.categorized(id) {
        case s: MultipleAttackSkill => "multiple-attack"
        case s: ChainAttackSkill => "chain-attack"
      }.getOrElse("undefined")
    }
  }

  implicit class RaceIdResolver(id: RaceId) {
    def name: String = service.races().read().find(_.getId == id).map(_.name).getOrElse(undefined)
  }

  implicit class ClassIdResolver(id: ClassId) {
    def name: String = service.classes().read().find(_.getId == id).map(_.name).getOrElse(undefined)
  }
}
