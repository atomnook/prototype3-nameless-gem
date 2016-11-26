package browser.crud

import browser.arbitrary.{ArbitraryAttributeBoostSkill, ArbitraryMasterSkill}
import browser.helper.{CrudSpec, ImplicitId}
import browser.helper.fill.FillSkill
import domain.service.Crud
import model.skill.MasterySkill
import play.api.mvc.Call

class MasteryCrudSpec extends CrudSpec[MasterySkill] with FillSkill with ImplicitId {
  override protected[this] def list: Call = controllers.routes.MasteryController.list()

  override protected[this] def get(id: String): Call = controllers.routes.MasteryController.get(id)

  override protected[this] def fill(a: MasterySkill, hasIdField: Boolean): Unit = {
    fill(a.getSkill, hasIdField)

    singleSel("mastery").value = a.mastery.value.toString
  }

  override protected[this] def id(a: MasterySkill): String = a.getSkill.getId.id

  override protected[this] def create(): MasterySkill = {
    val prerequisite = ArbitraryAttributeBoostSkill.arbitrary(Nil)
    service.attributeBoostSkills().create(prerequisite)
    ArbitraryMasterSkill.arbitrary(prerequisite.id() :: Nil)
  }

  override protected[this] def crud: Crud[MasterySkill] = service.masterySkills()

  override protected[this] def update(id: MasterySkill, data: MasterySkill): MasterySkill = {
    data.update(_.skill.id := id.getSkill.getId)
  }
}
