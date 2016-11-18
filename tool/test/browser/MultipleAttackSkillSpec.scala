package browser

import controllers.ReverseMultipleAttackController
import domain.service.Crud
import model.skill.MultipleAttackSkill
import play.api.mvc.Call

class MultipleAttackSkillSpec extends CrudSpec[MultipleAttackSkill, ReverseMultipleAttackController] {
  override protected[this] def list: Call = reverseController.list()

  override protected[this] def get(id: String): Call = reverseController.get(id)

  override protected[this] def fill(a: MultipleAttackSkill, hasIdField: Boolean): Unit = {
    val as = a.getSkill
    val s = as.getSkill

    if (hasIdField) {
      textField("id").value = s.getId.id
    }

    textField("name").value = s.name
    multiSel("skills").values = s.prerequisites.map(_.id)

    multiSel("types").values = as.types.map(_.value.toString)
    multiSel("elements").values = as.elements.map(_.value.toString)
    textLevel("base-power", "increasing-power", as.getPower)
    textLevel("base-tp", "increasing-tp", as.getTpCost)

    textLevel("base-hit", "increasing-hit", a.getHit)
    singleSel("range").value = a.range.value.toString
    singleSel("target").value = a.target.value.toString
  }

  override protected[this] def id(a: MultipleAttackSkill): String = a.id().id

  override protected[this] def create(): MultipleAttackSkill = {
    val prerequisite = arbChainAttackSkill.arbitrary(Nil)
    service.chainAttackSkills().create(prerequisite)
    arbMultipleAttackSkill.arbitrary(prerequisite.id() :: Nil)
  }

  override protected[this] def crud: Crud[MultipleAttackSkill] = service.multipleAttackSkills()

  override protected[this] def update(id: MultipleAttackSkill, data: MultipleAttackSkill): MultipleAttackSkill = {
    data.update(_.skill.skill.id := id.id())
  }

  override protected[this] def reverseController: ReverseMultipleAttackController = controllers.routes.MultipleAttackController
}
