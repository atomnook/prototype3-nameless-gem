package browser

import controllers.ReverseChainAttackController
import domain.service.DatabaseService.Crud
import model.skill.ChainAttackSkill
import play.api.mvc.Call

class ChainAttackSkillSpec extends CrudSpec[ChainAttackSkill, ReverseChainAttackController] {
  override protected[this] def reverseController: ReverseChainAttackController = controllers.routes.ChainAttackController

  override protected[this] def list: Call = reverseController.list()

  override protected[this] def get(id: String): Call = reverseController.get(id)

  override protected[this] def fill(a: ChainAttackSkill, hasIdField: Boolean): Unit = {
    val s = a.getSkill.getSkill
    val as = a.getSkill

    if (hasIdField) {
      textField("id").value = s.getId.id
    }

    textField("name").value = s.name
    multiSel("skills").values = s.prerequisites.map(_.id)

    multiSel("types").values = as.types.map(_.value.toString)
    multiSel("elements").values = as.elements.map(_.value.toString)
    textLevel("base-power", "increasing-power", as.getPower)
    textLevel("base-tp", "increasing-tp", as.getTpCost)

    multiSel("follow").values = a.follow.map(_.value.toString)
    textLevel("base-chain", "increasing-chain", a.getChain)
    singleSel("range").value = a.range.value.toString
  }

  override protected[this] def id(a: ChainAttackSkill): String = a.id().id

  override protected[this] def create(): ChainAttackSkill = {
    val prerequisite = arbMultipleAttackSkill.arbitrary(Nil)
    service.multipleAttackSkills().create(prerequisite)
    arbChainAttackSkill.arbitrary(prerequisite.id() :: Nil)
  }

  override protected[this] def crud: Crud[ChainAttackSkill] = service.chainAttackSkills()

  override protected[this] def update(id: ChainAttackSkill, data: ChainAttackSkill): ChainAttackSkill = {
    data.update(_.skill.skill.id := id.id())
  }
}
