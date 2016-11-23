package browser.crud

import browser.helper.CrudSpec
import browser.helper.create.CreateChara
import domain.service.Crud
import model.Chara
import play.api.mvc.Call

class CharaCrudSpec extends CrudSpec[Chara] with CreateChara {
  override protected[this] def list: Call = controllers.routes.CharaController.list()

  override protected[this] def get(id: String): Call = controllers.routes.CharaController.get(id)

  override protected[this] def fill(a: Chara, hasIdField: Boolean): Unit = {
    if (hasIdField) {
      textField("id").value = a.getId.id
    }

    textField("name").value = a.name
    singleSel("race").value = a.getRace.id
    multiSel("classes").values = a.classes.map(_.getId.id)
  }

  override protected[this] def id(a: Chara): String = a.getId.id

  override protected[this] def crud: Crud[Chara] = service.characters()

  override protected[this] def update(id: Chara, data: Chara): Chara = data.update(_.id := id.getId)
}
