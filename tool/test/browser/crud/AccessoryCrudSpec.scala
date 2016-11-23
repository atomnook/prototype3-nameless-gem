package browser.crud

import browser.arbitrary.ArbitraryAccessory
import controllers.ReverseAccessoryController
import domain.service.Crud
import model.item.Accessory
import play.api.mvc.Call

class AccessoryCrudSpec extends CrudSpec[Accessory, ReverseAccessoryController] {
  override protected[this] def list: Call = reverseController.list()

  override protected[this] def get(id: String): Call = reverseController.get(id)

  override protected[this] def fill(a: Accessory, hasIdField: Boolean): Unit = {
    val i = a.getItem

    if (hasIdField) {
      textField("id").value = i.getId.id
    }

    textField("name").value = i.name
    numberField("price").value = i.price.toString
  }

  override protected[this] def id(a: Accessory): String = a.getItem.getId.id

  override protected[this] def create(): Accessory = ArbitraryAccessory.arbitrary

  override protected[this] def crud: Crud[Accessory] = service.accessories()

  override protected[this] def update(id: Accessory, data: Accessory): Accessory = data.update(_.item.id := id.getItem.getId)

  override protected[this] def reverseController: ReverseAccessoryController = controllers.routes.AccessoryController
}
