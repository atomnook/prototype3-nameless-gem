package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import model.skill.MultipleAttackSkill
import models.{Identifier, ModelHelper, MultipleAttackFormat}
import play.api.mvc.{Action, Controller}

class SkillController @Inject() (service: DatabaseService) extends Controller with JsonApiController {
  implicit val helper = new ModelHelper(service)

  private[this] def all = service.multipleAttackSkills().read().map(_.getSkill.getSkill)

  val list = Action { request =>
    val skills = all
    Ok(views.html.SkillController.list(skills))
  }

  def redirect(id: String) = Action { request =>
    if (service.multipleAttackSkills().read().exists(_.getSkill.getSkill.getId.id == id)) {
      Redirect(routes.SkillController.getMultipleAttack(id))
    } else {
      throw new RuntimeException(s"$id not found")
    }
  }

  val multipleAttackList = Action { request =>
    val skills = service.multipleAttackSkills().read()
    val prerequisites = all
    Ok(views.html.SkillController.multipleAttackList(skills, prerequisites))
  }

  def getMultipleAttack(id: String) = Action { request =>
    val skill = service.multipleAttackSkills().read().find(_.getSkill.getSkill.getId.id == id)
    Ok(views.html.SkillController.getMultipleAttack(id, skill))
  }

  val createMultipleAttack = json[MultipleAttackFormat] { request =>
    val skill = request.body.asModel
    val res = service.multipleAttackSkills().create(skill)

    if (res) created() else alreadyExists(request.body.id)
  }

  def deleteMultipleAttack(id: String) = Action { request =>
    val res = service.multipleAttackSkills().delete(MultipleAttackSkill().update(_.skill.skill.id.id := id))

    if (res) ok() else notFound(Identifier(id))
  }

  val chainAttackList = TODO
}
