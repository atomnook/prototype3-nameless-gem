package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import domain.service.DatabaseService.Crud
import model.skill.{MultipleAttackSkill, Skill}
import models.{Identifier, ModelHelper, MultipleAttackFormat}
import play.api.mvc.Result

class MultipleAttackController @Inject() (service: DatabaseService)
  extends CrudController[MultipleAttackSkill, MultipleAttackFormat] with BasicSkillController {

  implicit val helper = new ModelHelper(service)

  private[this] def all: List[Skill] = service.multipleAttackSkills().read().map(_.getSkill.getSkill).toList

  override protected[this] val crud: Crud[MultipleAttackSkill] = service.multipleAttackSkills()

  override protected[this] def identity(a: MultipleAttackSkill): Identifier = Identifier(a.getSkill.getSkill.getId.id)

  override protected[this] def identity(id: Identifier): MultipleAttackSkill = MultipleAttackSkill().update(_.skill.skill.id.id := id.id)

  override protected[this] def listPage(a: List[MultipleAttackSkill]): Result = {
    Ok(views.html.SkillController.MultipleAttackController.list(a, allSkills(service)))
  }

  override protected[this] def getPage(id: String, a: Option[MultipleAttackSkill]): Result = {
    Ok(views.html.SkillController.MultipleAttackController.get(id, a))
  }
}
