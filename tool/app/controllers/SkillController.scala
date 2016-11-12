package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import model.skill.{ChainAttackSkill, MultipleAttackSkill, SkillId}
import models.{ModelHelper, SkillAggregator}
import play.api.mvc.{Action, Controller}

class SkillController @Inject() (service: DatabaseService) extends Controller with SkillAggregator {
  private[this] val skills = skillAggregator(service)

  private[this] implicit val helper = new ModelHelper(service)

  val list = Action(_ => Ok(views.html.SkillController.list(skills.all)))

  def redirect(id: String) = Action { request =>
    skills.categorized(SkillId(id)) {
      case s: MultipleAttackSkill => Redirect(routes.MultipleAttackController.get(id))
      case s: ChainAttackSkill => Redirect(routes.ChainAttackController.get(id))
    }.getOrElse(throw new RuntimeException(s"$id not found"))
  }
}
