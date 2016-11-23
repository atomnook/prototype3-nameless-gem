package browser.crud

import browser.arbitrary.ArbitraryWeapon
import browser.helper.{CrudSpec, ImplicitId}
import browser.helper.fill.FillItem
import domain.service.Crud
import model.item.Weapon
import play.api.mvc.Call

class WeaponCrudSpec extends CrudSpec[Weapon] with FillItem with ImplicitId {
  override protected[this] def list: Call = controllers.routes.WeaponController.list()

  override protected[this] def get(id: String): Call = controllers.routes.WeaponController.get(id)

  override protected[this] def fill(a: Weapon, hasIdField: Boolean): Unit = {
    fill(a.getItem, hasIdField)

    numberField("atk").value = a.atk.toString
    numberField("mat").value = a.mat.toString
    singleSel("weapon-type").value = a.`type`.value.toString
  }

  override protected[this] def id(a: Weapon): String = a.id().id

  override protected[this] def create(): Weapon = ArbitraryWeapon.arbitrary

  override protected[this] def crud: Crud[Weapon] = service.weapons()

  override protected[this] def update(id: Weapon, data: Weapon): Weapon = data.update(_.item.id := id.id())
}
