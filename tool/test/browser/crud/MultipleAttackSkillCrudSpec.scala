package browser.crud

import browser.arbitrary.{ArbitraryChainAttackSkill, ArbitraryMultipleAttackSkill}
import browser.helper.fill.FillAttackSkill
import browser.helper.{CrudSpec, ImplicitId}
import browser.helper.field.AttackLevelField
import domain.service.Crud
import model.skill.MultipleAttackSkill
import play.api.mvc.Call

class MultipleAttackSkillCrudSpec
  extends CrudSpec[MultipleAttackSkill] with FillAttackSkill with AttackLevelField with ImplicitId {

  override protected[this] def list: Call = controllers.routes.MultipleAttackController.list()

  override protected[this] def get(id: String): Call = controllers.routes.MultipleAttackController.get(id)

  override protected[this] def fill(a: MultipleAttackSkill, hasIdField: Boolean): Unit = {
    fill(a.getSkill, hasIdField)

    attackLevelField("base-hit", "increasing-hit") := a.getHit
    singleSel("range").value = a.range.value.toString
    singleSel("target").value = a.target.value.toString
  }

  override protected[this] def id(a: MultipleAttackSkill): String = a.id().id

  override protected[this] def create(): MultipleAttackSkill = {
    val prerequisite = ArbitraryChainAttackSkill.arbitrary(Nil)
    service.chainAttackSkills().create(prerequisite)
    ArbitraryMultipleAttackSkill.arbitrary(prerequisite.id() :: Nil)
  }

  override protected[this] def crud: Crud[MultipleAttackSkill] = service.multipleAttackSkills()

  override protected[this] def update(id: MultipleAttackSkill, data: MultipleAttackSkill): MultipleAttackSkill = {
    data.update(_.skill.skill.id := id.id())
  }
}
