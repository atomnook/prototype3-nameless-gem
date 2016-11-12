package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import domain.service.DatabaseService.Crud
import model.skill.ChainAttackSkill
import models.core.Identifier
import models.request.CreateChainAttack
import models.{ModelHelper, SkillAggregator}
import play.api.mvc.Result

class ChainAttackController @Inject() (service: DatabaseService)
  extends CrudController[ChainAttackSkill, CreateChainAttack] with SkillAggregator {

  private[this] val skills = skillAggregator(service)

  private[this] implicit val helper = new ModelHelper(service)

  override protected[this] val crud: Crud[ChainAttackSkill] = service.chainAttackSkills()

  override protected[this] def identity(a: ChainAttackSkill): Identifier = Identifier(a.getSkill.getSkill.getId.id)

  override protected[this] def identity(id: Identifier): ChainAttackSkill = ChainAttackSkill().update(_.skill.skill.id.id := id.id)

  override protected[this] def listPage(a: List[ChainAttackSkill]): Result = {
    Ok(views.html.SkillController.ChainAttackController.list(a, skills.all))
  }

  override protected[this] def getPage(id: String, a: Option[ChainAttackSkill]): Result = {
    Ok(views.html.SkillController.ChainAttackController.get(id, a))
  }
}
