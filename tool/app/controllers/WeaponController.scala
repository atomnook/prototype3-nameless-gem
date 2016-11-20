package controllers

import javax.inject.Inject

import domain.service.{Crud, DatabaseService}
import model.item.Weapon
import models.ModelHelper
import models.core.Identifier
import models.request.CreateWeapon
import play.api.mvc.Result

import scala.concurrent.Future

class WeaponController @Inject() (service: DatabaseService) extends CrudController[Weapon, CreateWeapon] {
  private[this] implicit val helper = new ModelHelper(service)

  override protected[this] val crud: Crud[Weapon] = service.weapons()

  override protected[this] def identity(a: Weapon): Identifier = Identifier(a.getItem.getId.id)

  override protected[this] def identity(id: Identifier): Weapon = Weapon().update(_.item.id.id := id.id)

  override protected[this] def listPage(a: List[Weapon]): Result = {
    Ok(views.html.WeaponController.list(a))
  }

  override protected[this] def getPage(id: String, a: Option[Weapon]): Future[Result] = {
    Future.successful(Ok(views.html.WeaponController.get(id, a)))
  }
}
