package browser.crud

import browser.arbitrary.{ArbitraryClass, ArbitraryMultipleAttackSkill}
import browser.helper.{CrudSpec, ImplicitId}
import browser.helper.field.LevelAttributesField
import domain.service.Crud
import model.Class
import play.api.mvc.Call

class ClassCrudSpec extends CrudSpec[Class] with LevelAttributesField with ImplicitId {
  override protected[this] def list: Call = controllers.routes.ClassController.list()

  override protected[this] def get(id: String): Call = controllers.routes.ClassController.get(id)

  override protected[this] def fill(a: Class, hasIdField: Boolean): Unit = {
    if (hasIdField) {
      textField("id").value = a.getId.id
    }

    textField("name").value = a.name
    multiSel("skills").values = a.skillTree.map(_.id)

    levelAttributesField("level-attributes") := a.getAttributes
  }

  override protected[this] def id(a: Class): String = a.getId.id

  override protected[this] def create(): Class = {
    val prerequisite = ArbitraryMultipleAttackSkill.arbitrary(Nil)
    service.multipleAttackSkills().create(prerequisite)
    ArbitraryClass.arbitrary.update(_.skillTree := prerequisite.id() :: Nil)
  }

  override protected[this] def crud: Crud[Class] = service.classes()

  override protected[this] def update(id: Class, data: Class): Class = data.update(_.id := id.getId)
}
