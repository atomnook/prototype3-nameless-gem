package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import domain.service.DatabaseService.Crud
import model.skill.MultipleAttackSkill
import models.core.Identifier
import models.request.CreateMultipleAttack
import models.{ModelHelper, SkillAggregator}
import play.api.mvc.Result

import scala.concurrent.Future

class MultipleAttackController @Inject() (service: DatabaseService)
  extends CrudController[MultipleAttackSkill, CreateMultipleAttack] with SkillAggregator {

  private[this] val skills = skillAggregator(service)

  private[this] implicit val helper = new ModelHelper(service)

  override protected[this] val crud: Crud[MultipleAttackSkill] = service.multipleAttackSkills()

  override protected[this] def identity(a: MultipleAttackSkill): Identifier = Identifier(a.getSkill.getSkill.getId.id)

  override protected[this] def identity(id: Identifier): MultipleAttackSkill = MultipleAttackSkill().update(_.skill.skill.id.id := id.id)

  override protected[this] def listPage(a: List[MultipleAttackSkill]): Result = {
    Ok(views.html.SkillController.MultipleAttackController.list(a, skills.all))
  }

  override protected[this] def getPage(id: String, a: Option[MultipleAttackSkill]): Future[Result] = {
    Future.successful(Ok(views.html.SkillController.MultipleAttackController.get(id, a, skills.all)))
  }
}
