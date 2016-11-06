package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import play.api.mvc.{Action, Controller}

class SkillController @Inject() (service: DatabaseService) extends Controller with BasicSkillController {
  val list = Action { request =>
    val skills = allSkills(service)
    Ok(views.html.SkillController.list(skills))
  }

  def redirect(id: String) = Action { request =>
    if (service.multipleAttackSkills().read().exists(_.getSkill.getSkill.getId.id == id)) {
      Redirect(routes.MultipleAttackController.get(id))
    } else {
      throw new RuntimeException(s"$id not found")
    }
  }
}
