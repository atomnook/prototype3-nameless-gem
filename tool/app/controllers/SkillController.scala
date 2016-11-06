package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import models.{ModelHelper, MultipleAttackFormat}
import play.api.mvc.{Action, Controller}

class SkillController @Inject() (service: DatabaseService) extends Controller with JsonApiController {
  implicit val helper = new ModelHelper(service)

  private[this] def all = service.multipleAttackSkills().read().map(_.getSkill.getSkill)

  val list = Action { request =>
    val skills = all
    Ok(views.html.SkillController.list(skills))
  }

  val multipleAttackList = Action { request =>
    val skills = service.multipleAttackSkills().read()
    val prerequisites = all
    Ok(views.html.SkillController.multipleAttackList(skills, prerequisites))
  }

  val createMultipleAttack = json[MultipleAttackFormat] { request =>
    val skill = request.body.asModel
    val res = service.multipleAttackSkills().create(skill)

    if (res) created() else alreadyExists(request.body.id)
  }

  val chainAttackList = TODO
}
