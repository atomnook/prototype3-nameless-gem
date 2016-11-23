package browser.crud

import controllers.ReverseClassController
import domain.service.Crud
import model.Class
import play.api.mvc.Call

class ClassCrudSpec extends  CrudSpec[Class, ReverseClassController] {
  override protected[this] def list: Call = reverseController.list()

  override protected[this] def get(id: String): Call = reverseController.get(id)

  override protected[this] def fill(a: Class, hasIdField: Boolean): Unit = {
    if (hasIdField) {
      textField("id").value = a.getId.id
    }

    textField("name").value = a.name
    multiSel("skills").values = a.skillTree.map(_.id)

    fill("level-attributes", a.getAttributes)
  }

  override protected[this] def id(a: Class): String = a.getId.id

  override protected[this] def create(): Class = {
    val prerequisite = arbMultipleAttackSkill.arbitrary(Nil)
    service.multipleAttackSkills().create(prerequisite)
    arbClass.arbitrary.update(_.skillTree := prerequisite.id() :: Nil)
  }

  override protected[this] def crud: Crud[Class] = service.classes()

  override protected[this] def update(id: Class, data: Class): Class = data.update(_.id := id.getId)

  override protected[this] def reverseController: ReverseClassController = controllers.routes.ClassController
}
