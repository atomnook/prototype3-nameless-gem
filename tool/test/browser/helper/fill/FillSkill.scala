package browser.helper.fill

import model.skill.Skill
import org.scalatestplus.play.{AllBrowsersPerSuite, OneServerPerSuite, PlaySpec}

trait FillSkill extends PlaySpec with OneServerPerSuite with AllBrowsersPerSuite {
  protected[this] def fill(skill: Skill, hasIdField: Boolean): Unit = {
    if (hasIdField) {
      textField("id").value = skill.getId.id
    }

    textField("name").value = skill.name
    multiSel("skills").values = skill.prerequisites.map(_.id)
  }
}
