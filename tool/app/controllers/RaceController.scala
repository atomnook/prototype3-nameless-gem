package controllers

import javax.inject.Inject

import domain.service.DatabaseService
import domain.service.DatabaseService.Crud
import model.Race
import models.{Identifier, RaceFormat}
import play.api.mvc.Result

class RaceController @Inject() (service: DatabaseService) extends CrudController[Race, RaceFormat] {
  override protected[this] val crud: Crud[Race] = service.races()

  override protected[this] def identity(a: Race): Identifier = Identifier(a.getId.id)

  override protected[this] def identity(id: Identifier): Race = Race().update(_.id.id := id.id)

  override protected[this] def listPage(a: List[Race]): Result = Ok(views.html.RaceController.list(a))

  override protected[this] def getPage(id: String, a: Option[Race]): Result = Ok(views.html.RaceController.get(id, a))
}
