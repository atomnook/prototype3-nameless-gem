package browser.crud

import browser.arbitrary.ArbitraryArmor
import controllers.ReverseArmorController
import domain.service.Crud
import model.item.Armor
import play.api.mvc.Call

class ArmorCrudSpec extends CrudSpec[Armor, ReverseArmorController] {
  override protected[this] def list: Call = reverseController.list()

  override protected[this] def get(id: String): Call = reverseController.get(id)

  override protected[this] def fill(a: Armor, hasIdField: Boolean): Unit = {
    val i = a.getItem

    if (hasIdField) {
      textField("id").value = i.getId.id
    }

    textField("name").value = i.name
    numberField("price").value = i.price.toString

    numberField("def").value = a.`def`.toString
    numberField("mdf").value = a.mdf.toString
    singleSel("armor-type").value = a.`type`.value.toString
  }

  override protected[this] def id(a: Armor): String = a.getItem.getId.id

  override protected[this] def create(): Armor = ArbitraryArmor.arbitrary

  override protected[this] def crud: Crud[Armor] = service.armors()

  override protected[this] def update(id: Armor, data: Armor): Armor = data.update(_.item.id := id.getItem.getId)

  override protected[this] def reverseController: ReverseArmorController = controllers.routes.ArmorController
}
