package controllers

import javax.inject.Inject

import domain.service.{Crud, DatabaseService}
import model.skill.MasterySkill
import models.{ModelHelper, SkillAggregator}
import models.core.Identifier
import models.request.CreateMasterySkill
import play.api.mvc.Result

import scala.concurrent.Future

class MasteryController @Inject() (service: DatabaseService)
  extends CrudController[MasterySkill, CreateMasterySkill] with SkillAggregator {

  private[this] val skills = skillAggregator(service)

  private[this] implicit val helper = new ModelHelper(service)

  override protected[this] val crud: Crud[MasterySkill] = service.masterySkills()

  override protected[this] def identity(a: MasterySkill): Identifier = Identifier(a.getSkill.getId.id)

  override protected[this] def identity(id: Identifier): MasterySkill = MasterySkill().update(_.skill.id.id := id.id)

  override protected[this] def listPage(a: List[MasterySkill]): Result = {
    Ok(views.html.MasteryController.list(a, skills.all))
  }

  override protected[this] def getPage(id: String, a: Option[MasterySkill]): Future[Result] = {
    Future.successful(Ok(views.html.MasteryController.get(id, a, skills.all)))
  }
}
