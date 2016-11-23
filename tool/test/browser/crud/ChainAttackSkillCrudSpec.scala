package browser.crud

import browser.arbitrary.{ArbitraryChainAttackSkill, ArbitraryMultipleAttackSkill}
import browser.helper.{CrudSpec, ImplicitId}
import browser.helper.fill.FillAttackSkill
import domain.service.Crud
import model.skill.ChainAttackSkill
import play.api.mvc.Call

class ChainAttackSkillCrudSpec extends CrudSpec[ChainAttackSkill] with FillAttackSkill with ImplicitId {
  override protected[this] def list: Call = controllers.routes.ChainAttackController.list()

  override protected[this] def get(id: String): Call = controllers.routes.ChainAttackController.get(id)

  override protected[this] def fill(a: ChainAttackSkill, hasIdField: Boolean): Unit = {
    fill(a.getSkill, hasIdField)

    multiSel("follow").values = a.follow.map(_.value.toString)
    attackLevelField("base-chain", "increasing-chain") := a.getChain
    singleSel("range").value = a.range.value.toString
  }

  override protected[this] def id(a: ChainAttackSkill): String = a.id().id

  override protected[this] def create(): ChainAttackSkill = {
    val prerequisite = ArbitraryMultipleAttackSkill.arbitrary(Nil)
    service.multipleAttackSkills().create(prerequisite)
    ArbitraryChainAttackSkill.arbitrary(prerequisite.id() :: Nil)
  }

  override protected[this] def crud: Crud[ChainAttackSkill] = service.chainAttackSkills()

  override protected[this] def update(id: ChainAttackSkill, data: ChainAttackSkill): ChainAttackSkill = {
    data.update(_.skill.skill.id := id.id())
  }
}
