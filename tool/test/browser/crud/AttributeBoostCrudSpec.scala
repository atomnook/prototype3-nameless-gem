package browser.crud

import browser.arbitrary.{ArbitraryAttributeBoostSkill, ArbitraryMasterSkill}
import browser.helper.{CrudSpec, ImplicitId}
import browser.helper.fill.FillSkill
import domain.service.Crud
import model.skill.AttributeBoostSkill
import play.api.mvc.Call

class AttributeBoostCrudSpec extends CrudSpec[AttributeBoostSkill] with FillSkill with ImplicitId {
  override protected[this] def list: Call = controllers.routes.AttributeBoostController.list()

  override protected[this] def get(id: String): Call = controllers.routes.AttributeBoostController.get(id)

  override protected[this] def fill(a: AttributeBoostSkill, hasIdField: Boolean): Unit = {
    fill(a.getSkill, hasIdField)

    singleSel("attribute").value = a.attribute.value.toString
  }

  override protected[this] def id(a: AttributeBoostSkill): String = a.getSkill.getId.id

  override protected[this] def create(): AttributeBoostSkill = {
    val prerequisite = ArbitraryMasterSkill.arbitrary(Nil)
    service.masterySkills().create(prerequisite)
    ArbitraryAttributeBoostSkill.arbitrary(prerequisite.id() :: Nil)
  }

  override protected[this] def crud: Crud[AttributeBoostSkill] = service.attributeBoostSkills()

  override protected[this] def update(id: AttributeBoostSkill, data: AttributeBoostSkill): AttributeBoostSkill = {
    data.update(_.skill.id := id.getSkill.getId)
  }
}
