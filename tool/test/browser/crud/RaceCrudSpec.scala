package browser.crud

import browser.arbitrary.{ArbitraryMultipleAttackSkill, ArbitraryRace}
import browser.helper.{CrudSpec, ImplicitId}
import browser.helper.field.LevelAttributesField
import domain.service.Crud
import model.Race
import play.api.mvc.Call

class RaceCrudSpec extends CrudSpec[Race] with LevelAttributesField with ImplicitId {
  override protected[this] def list: Call = controllers.routes.RaceController.list()

  override protected[this] def get(id: String): Call = controllers.routes.RaceController.get(id)

  override protected[this] def fill(a: Race, hasIdField: Boolean): Unit = {
    if (hasIdField) {
      textField("id").value = a.getId.id
    }

    textField("name").value = a.name
    multiSel("skills").values = a.skillTree.map(_.id)

    levelAttributesField("level-attributes") := a.getAttributes
  }

  override protected[this] def id(a: Race): String = a.getId.id

  override protected[this] def create(): Race = {
    val prerequisite = ArbitraryMultipleAttackSkill.arbitrary(Nil)
    service.multipleAttackSkills().create(prerequisite)
    ArbitraryRace.arbitrary.update(_.skillTree := prerequisite.id() :: Nil)
  }

  override protected[this] def crud: Crud[Race] = service.races()

  override protected[this] def update(id: Race, data: Race): Race = data.update(_.id := id.getId)
}
