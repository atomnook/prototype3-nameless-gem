package controllers

import javax.inject.Inject

import domain.service.{Crud, DatabaseService}
import model.item.Accessory
import models.ModelHelper
import models.core.Identifier
import models.request.CreateAccessory
import play.api.mvc.Result

import scala.concurrent.Future

class AccessoryController @Inject() (service: DatabaseService) extends CrudController[Accessory, CreateAccessory] {
  private[this] implicit val helper = new ModelHelper(service)

  override protected[this] val crud: Crud[Accessory] = service.accessories()

  override protected[this] def identity(a: Accessory): Identifier = Identifier(a.getItem.getId.id)

  override protected[this] def identity(id: Identifier): Accessory = Accessory().update(_.item.id.id := id.id)

  override protected[this] def listPage(a: List[Accessory]): Result = {
    Ok(views.html.AccessoryController.list(a))
  }

  override protected[this] def getPage(id: String, a: Option[Accessory]): Future[Result] = {
    Future.successful(Ok(views.html.AccessoryController.get(id, a)))
  }
}
