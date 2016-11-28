package browser.helper.fill

import browser.helper.field.AttackLevelField
import model.skill.AttackSkill

trait FillAttackSkill extends FillSkill with AttackLevelField {
  protected[this] def fill(skill: AttackSkill, hasIdField: Boolean): Unit = {
    fill(skill.getSkill, hasIdField)

    multiSel("types").values = skill.types.map(_.value.toString)
    multiSel("elements").values = skill.elements.map(_.value.toString)
    attackLevelField("base-power", "increasing-power") := skill.getPower
    attackLevelField("base-tp", "increasing-tp") := skill.getTpCost
    singleSel("part").value = skill.usedPart.value.toString
  }
}
