package controllers

import javax.inject.Inject

import domain.service.{Crud, DatabaseService}
import models.core.Identifier
import models.request.CreateClass
import models.{ModelHelper, SkillAggregator}
import play.api.mvc.Result

import scala.concurrent.Future

class ClassController @Inject() (service: DatabaseService)
  extends CrudController[model.Class, CreateClass] with SkillAggregator {

  private[this] val skills = skillAggregator(service)

  private[this] implicit val helper = new ModelHelper(service)

  override protected[this] val crud: Crud[model.Class] = service.classes()

  override protected[this] def identity(a: model.Class): Identifier = Identifier(a.getId.id)

  override protected[this] def identity(id: Identifier): model.Class = model.Class().update(_.id.id := id.id)

  override protected[this] def listPage(a: List[model.Class]): Result = Ok(views.html.ClassController.list(a, skills.all))

  override protected[this] def getPage(id: String, a: Option[model.Class]): Future[Result] = {
    Future.successful(Ok(views.html.ClassController.get(id, a, skills.all)))
  }
}
