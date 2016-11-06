package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import domain.service.DatabaseService.Crud
import models.{ClassFormat, Identifier}
import play.api.mvc.Result

class ClassController @Inject() (service: DatabaseService) extends CrudController[model.Class, ClassFormat] {
  override protected[this] val crud: Crud[model.Class] = service.classes()

  override protected[this] def identity(a: model.Class): Identifier = Identifier(a.getId.id)

  override protected[this] def identity(id: Identifier): model.Class = model.Class().update(_.id.id := id.id)

  override protected[this] def listPage(a: List[model.Class]): Result = Ok(views.html.ClassController.list(a))

  override protected[this] def getPage(id: String, a: Option[model.Class]): Result = Ok(views.html.ClassController.get(id, a))
}
