package controllers

import javax.inject.Inject

import domain.service.{Crud, DatabaseService}
import model.item.Armor
import models.ModelHelper
import models.core.Identifier
import models.request.CreateArmor
import play.api.mvc.Result

import scala.concurrent.Future

class ArmorController @Inject() (service: DatabaseService) extends CrudController[Armor, CreateArmor] {
  private[this] implicit val helper = new ModelHelper(service)

  override protected[this] val crud: Crud[Armor] = service.armors()

  override protected[this] def identity(a: Armor): Identifier = Identifier(a.getItem.getId.id)

  override protected[this] def identity(id: Identifier): Armor = Armor().update(_.item.id.id := id.id)

  override protected[this] def listPage(a: List[Armor]): Result = {
    Ok(views.html.ArmorController.list(a))
  }

  override protected[this] def getPage(id: String, a: Option[Armor]): Future[Result] = {
    Future.successful(Ok(views.html.ArmorController.get(id, a)))
  }
}
