package controllers

import javax.inject.Inject

import domain.service.{Crud, DatabaseService}
import model.skill.AttributeBoostSkill
import models.{ModelHelper, SkillAggregator}
import models.core.Identifier
import models.request.CreateAttributeBoostSkill
import play.api.mvc.Result

import scala.concurrent.Future

class AttributeBoostController @Inject() (service: DatabaseService)
  extends CrudController[AttributeBoostSkill, CreateAttributeBoostSkill] with SkillAggregator {

  private[this] val skills = skillAggregator(service)

  private[this] implicit val helper = new ModelHelper(service)

  override protected[this] val crud: Crud[AttributeBoostSkill] = service.attributeBoostSkills()

  override protected[this] def identity(a: AttributeBoostSkill): Identifier = Identifier(a.getSkill.getId.id)

  override protected[this] def identity(id: Identifier): AttributeBoostSkill = {
    AttributeBoostSkill().update(_.skill.id.id := id.id)
  }

  override protected[this] def listPage(a: List[AttributeBoostSkill]): Result = {
    Ok(views.html.AttributeBoostController.list(a, skills.all))
  }

  override protected[this] def getPage(id: String, a: Option[AttributeBoostSkill]): Future[Result] = {
    Future.successful(Ok(views.html.AttributeBoostController.get(id, a, skills.all)))
  }
}
