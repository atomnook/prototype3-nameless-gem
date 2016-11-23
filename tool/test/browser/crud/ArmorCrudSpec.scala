package browser.crud

import browser.arbitrary.ArbitraryArmor
import browser.helper.{CrudSpec, ImplicitId}
import browser.helper.fill.FillItem
import domain.service.Crud
import model.item.Armor
import play.api.mvc.Call

class ArmorCrudSpec extends CrudSpec[Armor] with FillItem with ImplicitId {
  override protected[this] def list: Call = controllers.routes.ArmorController.list()

  override protected[this] def get(id: String): Call = controllers.routes.ArmorController.get(id)

  override protected[this] def fill(a: Armor, hasIdField: Boolean): Unit = {
    fill(a.getItem, hasIdField)

    numberField("def").value = a.`def`.toString
    numberField("mdf").value = a.mdf.toString
    singleSel("armor-type").value = a.`type`.value.toString
  }

  override protected[this] def id(a: Armor): String = a.id().id

  override protected[this] def create(): Armor = ArbitraryArmor.arbitrary

  override protected[this] def crud: Crud[Armor] = service.armors()

  override protected[this] def update(id: Armor, data: Armor): Armor = {
    data.update(_.item.id := id.id())
  }
}
