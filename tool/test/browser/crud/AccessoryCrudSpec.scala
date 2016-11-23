package browser.crud

import browser.arbitrary.ArbitraryAccessory
import browser.helper.{CrudSpec, ImplicitId}
import browser.helper.fill.FillItem
import domain.service.Crud
import model.item.Accessory
import play.api.mvc.Call

class AccessoryCrudSpec extends CrudSpec[Accessory] with FillItem with ImplicitId {
  override protected[this] def list: Call = controllers.routes.AccessoryController.list()

  override protected[this] def get(id: String): Call = controllers.routes.AccessoryController.get(id)

  override protected[this] def fill(a: Accessory, hasIdField: Boolean): Unit = {
    fill(a.getItem, hasIdField)
  }

  override protected[this] def id(a: Accessory): String = a.id().id

  override protected[this] def create(): Accessory = ArbitraryAccessory.arbitrary

  override protected[this] def crud: Crud[Accessory] = service.accessories()

  override protected[this] def update(id: Accessory, data: Accessory): Accessory = {
    data.update(_.item.id := id.id())
  }
}
