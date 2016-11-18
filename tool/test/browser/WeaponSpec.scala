package browser

import browser.arbitrary.ArbitraryWeapon
import controllers.ReverseWeaponController
import domain.service.Crud
import model.item.Weapon
import play.api.mvc.Call

class WeaponSpec extends CrudSpec[Weapon, ReverseWeaponController] {
  override protected[this] def list: Call = reverseController.list()

  override protected[this] def get(id: String): Call = reverseController.get(id)

  override protected[this] def fill(a: Weapon, hasIdField: Boolean): Unit = {
    val i = a.getItem

    if (hasIdField) {
      textField("id").value = i.getId.id
    }

    textField("name").value = i.name
    numberField("price").value = i.price.toString

    numberField("atk").value = a.atk.toString
    numberField("mat").value = a.mat.toString
    singleSel("weapon-type").value = a.`type`.value.toString
  }

  override protected[this] def id(a: Weapon): String = a.getItem.getId.id

  override protected[this] def create(): Weapon = ArbitraryWeapon.arbitrary

  override protected[this] def crud: Crud[Weapon] = service.weapons()

  override protected[this] def update(id: Weapon, data: Weapon): Weapon = data.update(_.item.id := id.getItem.getId)

  override protected[this] def reverseController: ReverseWeaponController = controllers.routes.WeaponController
}
