package browser

import org.scalatestplus.play.BrowserInfo

class ClassSkillsSpec extends BrowserSpec {
  def sharedTests(browser: BrowserInfo) = {
    "/classes/:id/skills.json" must {
      "update skill tree " + browser.name in {
        val prerequisite = newMultipleAttack(Nil)
        val last = newClass

        service.multipleAttackSkills().create(prerequisite)
        service.classes().create(last)

        val expected = last.update(_.skillTree := prerequisite.getSkill.getSkill.getId :: Nil)

        go to s"http://localhost:$port/classes/${expected.getId.id}"

        multiSel("skill-tree").values = expected.skillTree.map(_.id)

        click on id("update-skills")

        eventually {
          assert(service.classes().read() === Set(expected))
        }
      }
    }
  }
}
