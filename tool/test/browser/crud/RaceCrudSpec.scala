package browser.crud

import controllers.ReverseRaceController
import domain.service.Crud
import model.Race
import play.api.mvc.Call

class RaceCrudSpec extends CrudSpec[Race, ReverseRaceController] {
  override protected[this] def list: Call = reverseController.list()

  override protected[this] def get(id: String): Call = reverseController.get(id)

  override protected[this] def fill(a: Race, hasIdField: Boolean): Unit = {
    if (hasIdField) {
      textField("id").value = a.getId.id
    }

    textField("name").value = a.name
    multiSel("skills").values = a.skillTree.map(_.id)

    fill("level-attributes", a.getAttributes)
  }

  override protected[this] def id(a: Race): String = a.getId.id

  override protected[this] def create(): Race = {
    val prerequisite = arbMultipleAttackSkill.arbitrary(Nil)
    service.multipleAttackSkills().create(prerequisite)
    arbRace.arbitrary.update(_.skillTree := prerequisite.id() :: Nil)
  }

  override protected[this] def crud: Crud[Race] = service.races()

  override protected[this] def update(id: Race, data: Race): Race = data.update(_.id := id.getId)

  override protected[this] def reverseController: ReverseRaceController = controllers.routes.RaceController
}
